package com.jim.recorder.ui.pressenter;

import android.content.Context;
import android.util.LongSparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.jim.recorder.api.DataStorage;
import com.jim.recorder.api.EventTypeManager;
import com.jim.recorder.ui.model.ViewCell;
import com.jim.recorder.model.Constants;
import com.jim.recorder.ui.model.MainViewData;
import com.jim.recorder.model.DayCell;
import com.jim.recorder.model.EventType;
import com.jim.recorder.ui.callback.MainView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.jim.recorder.model.Constants.timezone;
import static com.jim.recorder.utils.CalendarUtil.getCalendarDayStart;

public class MainPressenter extends MvpBasePresenter<MainView> {

    private ArrayList<MainViewData> mViewDatas = new ArrayList<>();

    private LongSparseArray<DayCell> mStorage = new LongSparseArray<>();
    private LongSparseArray<ArrayList<Integer>> mSelects= new LongSparseArray<>();
    private int mSelectCount = 0;
    private Calendar now;

    public void setTimeZone() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        timezone = calendar.get(Calendar.HOUR_OF_DAY) * Constants.one_hour + calendar.get(Calendar.MINUTE) * Constants.one_min;
        if (calendar.get(Calendar.YEAR) != 1970) {
            timezone = Constants.one_day - timezone;
        }
    }

    public ArrayList<MainViewData> getViewData() {
        return mViewDatas;
    }

    public EventType getEventType(int pos) {
        return EventTypeManager.getInstance().getEventList().get(pos);
    }

    @SuppressWarnings("all")
    public void initViewData(Context context) {
        Calendar start = Calendar.getInstance();
        start.set(start.get(Calendar.YEAR)-2, 0, 1);
        start = getCalendarDayStart(start);

        Calendar end = Calendar.getInstance();
        end.set(end.get(Calendar.YEAR)+1, 0, 1);
        end = getCalendarDayStart(end);

        for (long i=start.getTimeInMillis();i < end.getTimeInMillis(); i+=Constants.one_day) {
            mViewDatas.add(new MainViewData(i));
        }
        long now_time = getCalendarDayStart(now).getTimeInMillis();
        getView().todayPosition(Long.valueOf((now_time - start.getTimeInMillis()+timezone)/Constants.one_day).intValue());

        refreshRecordData();
    }

    public List<EventType> getLabelData() {
        return EventTypeManager.getInstance().getEventList();
    }

    public void updateTime() {
        now = Calendar.getInstance();
    }

    @SuppressWarnings("all")
    public void handleLeftClick(View v, MainViewData item, Object position) {
        if (position == null) {
            return;
        }
        int pos = (Integer) position;
        long timeStamp = item.getTime();
        DayCell cells = mStorage.get(timeStamp);
        ViewCell viewCell = null;
        if (cells == null) {
            cells = new DayCell(timeStamp);
            viewCell = new ViewCell(true, pos);
            cells.put(pos, viewCell);
            mStorage.put(timeStamp, cells);
        } else {
            viewCell = cells.get(pos);
            if (viewCell == null) {
                viewCell = new ViewCell(true, pos);
                cells.put(pos, viewCell);
            } else {
                viewCell.setSelected(!viewCell.isSelected());
            }
        }
        //储存已经选择
        ArrayList<Integer> selects = mSelects.get(timeStamp);
        if (viewCell.isSelected()) {
            if (selects == null) {
                selects = new ArrayList<>(5);
            }
            selects.add(pos);
            mSelects.append(timeStamp, selects);
            mSelectCount += 1;
        } else {
            if (selects != null) {
                if (selects.indexOf(pos) != -1){
                    selects.remove(selects.indexOf(pos));
                    mSelectCount -= 1;
                }
            }
        }
        getView().updateCellAfterClick(v, viewCell);
        getView().updateSelectIndicator(mSelectCount);
    }

    @SuppressWarnings("all")
    public void handleDayCellRender(ViewGroup content, final MainViewData item, int position) {
        if (mStorage.get(item.getTime()) == null) {
            getView().resetLeftDayCellView(content, mViewDatas.get(position));
        } else {
            if (content.getChildCount() == 0) {
                getView().resetLeftDayCellView(content, mViewDatas.get(position));
            }
            getView().updateLeftDayCellView(content, mViewDatas.get(position), mStorage.get(item.getTime()));
        }
    }

    /**
     * 取消所有选择
     */
    public void cancelSelected() {
        fixSelected(null);
    }

    public void wipeData(boolean showWarning) {
        if (judgeStatus() && showWarning) {
            getView().labelWipeWarning();
            return;
        }
        cancelSelected();
    }

    /**
     * 锚定颜色
     */
    @SuppressWarnings("all")
    public void fixSelected(EventType type) {
        ArrayList<Integer> selects;
        DayCell cells;
        ViewCell viewCell;
        long key;
        LongSparseArray<DayCell> needUpdates = new LongSparseArray<>();

        for (int i =0; i< mSelects.size(); i++) {
            key = mSelects.keyAt(i);
            selects = mSelects.get(key);
            cells = mStorage.get(key);
            if (cells != null) {
                for (int j = 0; j < selects.size(); j++) {
                    viewCell = cells.get(selects.get(j));
                    if (viewCell != null) {
                        viewCell.setSelected(false);
                        if (type != null) {
                            viewCell.setType(type.getType());
                            viewCell.setEventId(type.get_id());
                            needUpdates.put(key, cells);
                        }
                    }

                }
            }
        }
        mSelects.clear();
        mSelectCount =  0;
        getView().refreshLeft();
        DataStorage.saveRecordData(needUpdates);
    }

    public String toFormatTime(int pCount) {
        int count = pCount*15;
        int hour = count/60;
        int min = count%60;
        StringBuilder content = new StringBuilder();
        content.append("选择了");
        if (hour != 0) {
            content.append(hour).append("小时");
        }
        if (min != 0) {
            content.append(min).append("分钟");
        }
        return content.toString();
    }

    /**
     * 判断是否会覆盖原有数据
     * @return YES:有覆盖;NO:没有
     */
    public boolean judgeStatus() {
        if (mSelects.size() ==0 ) {
            return false;
        }
        ArrayList<Integer> selects;
        DayCell cells;
        ViewCell viewCell;
        long key;
        for (int i =0; i< mSelects.size(); i++) {
            key = mSelects.keyAt(i);
            selects = mSelects.get(key);
            cells = mStorage.get(key);
            if (cells != null) {
                for (int j = 0; j < selects.size(); j++) {
                    viewCell = cells.get(selects.get(j));
                    if (viewCell != null) {
                        viewCell.setSelected(false);
                        if (viewCell.getType() != -1) {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    public void judgeStatus(EventType type) {
        if (judgeStatus()) {
            getView().labelCoverWarning(type);
        } else {
            fixSelected(type);
        }
    }

    public void refreshRecordData() {
        mStorage = DataStorage.getRecordData();
        getView().refreshLeft();
    }

    public ViewCell cellAvailJudge(DayCell cells, int pos) {
        ViewCell viewCell = cells.get(pos);
        if (viewCell == null || EventTypeManager.getInstance().getEventType(viewCell.getEventId()) == null)
            return null;
        return viewCell;
    }
}
