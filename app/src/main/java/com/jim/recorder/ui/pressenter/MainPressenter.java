package com.jim.recorder.ui.pressenter;

import android.util.LongSparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.jim.recorder.api.DataStorage;
import com.jim.recorder.model.Cell;
import com.jim.recorder.model.Data;
import com.jim.recorder.model.DayCell;
import com.jim.recorder.model.EventType;
import com.jim.recorder.ui.callback.MainView;

import java.util.ArrayList;
import java.util.Calendar;

import static com.jim.recorder.model.Constants.one_day;
import static com.jim.recorder.model.Constants.one_hour;
import static com.jim.recorder.model.Constants.one_min;
import static com.jim.recorder.model.Constants.timezone;
import static com.jim.recorder.utils.CalendarUtil.getCalendarDayStart;

public class MainPressenter extends MvpBasePresenter<MainView> {

    private ArrayList<Data> data1 = new ArrayList<>();
    private ArrayList<EventType> data2 = new ArrayList<>();
    private LongSparseArray<DayCell> mStorage = new LongSparseArray<>();
    private LongSparseArray<ArrayList<Integer>> mSelects= new LongSparseArray<>();
    private Calendar now;

    public void setTimeZone() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        timezone = calendar.get(Calendar.HOUR_OF_DAY) * one_hour + calendar.get(Calendar.MINUTE) * one_min;
        if (calendar.get(Calendar.YEAR) != 1970) {
            timezone = one_day - timezone;
        }
    }

    public ArrayList<Data> getViewData() {
        return data1;
    }

    public EventType getEventType(int pos) {
        return data2.get(pos);
    }

    @SuppressWarnings("all")
    public void initViewData() {
        Calendar start = Calendar.getInstance();
        start.set(start.get(Calendar.YEAR)-2, 0, 1);
        start = getCalendarDayStart(start);

        Calendar end = Calendar.getInstance();
        end.set(end.get(Calendar.YEAR)+1, 0, 1);
        end = getCalendarDayStart(end);

        for (long i=start.getTimeInMillis();i < end.getTimeInMillis(); i+=one_day) {
            data1.add(new Data(i));
        }
        long now_time = getCalendarDayStart(now).getTimeInMillis();
        getView().todayPosition(Long.valueOf((now_time - start.getTimeInMillis()+timezone)/one_day).intValue());

        // 替换成对应初始化
        data2.add(new EventType("上班", 0));
        data2.add(new EventType("学习",12));
        data2.add(new EventType("阅读",6));
        data2.add(new EventType("购物",4));
        data2.add(new EventType("运动",7));
        data2.add(new EventType("聚会", 19));
        data2.add(new EventType("睡觉",26));
        data2.add(new EventType("洗漱",4));
        data2.add(new EventType("在路上",11));
        data2.add(new EventType("写作",18));
        data2.add(new EventType("休息", 10));
        data2.add(new EventType("闲聊",9));
        data2.add(new EventType("吃饭",2));
    }

    public ArrayList<EventType> getLabelData() {
        return data2;
    }

    public void updateTime() {
        now = Calendar.getInstance();
    }

    @SuppressWarnings("all")
    public void handleLeftClick(View v, Data item, Object position) {
        if (position == null) {
            return;
        }
        int pos = (Integer) position;
        long timeStamp = item.getTime();
        DayCell cells = mStorage.get(timeStamp);
        Cell cell = null;
        if (cells == null) {
            cells = new DayCell(timeStamp);
            cell = new Cell(true, pos);
            cells.put(pos, cell);
            mStorage.put(timeStamp, cells);
        } else {
            cell = cells.get(pos);
            if (cell == null) {
                cell = new Cell(true, pos);
                cells.put(pos, cell);
            } else {
                cell.setSelected(!cell.isSelected());
            }
        }
        //储存已经选择
        ArrayList<Integer> selects = mSelects.get(timeStamp);
        if (cell.isSelected()) {
            if (selects == null) {
                selects = new ArrayList<>(5);
            }
            selects.add(pos);
            mSelects.append(timeStamp, selects);
        } else {
            if (selects != null) {
                if (selects.indexOf(pos) != -1){
                    selects.remove(selects.indexOf(pos));
                }
            }
        }
        getView().updateCellAfterClick(v,cell);
    }

    @SuppressWarnings("all")
    public void handleDayCellRender(ViewGroup content, final Data item, int position) {
        if (mStorage.get(item.getTime()) == null) {
            getView().resetLeftDayCellView(content, data1.get(position));
        } else {
            if (content.getChildCount() == 0) {
                getView().resetLeftDayCellView(content, data1.get(position));
            }
            getView().updateLeftDayCellView(content, data1.get(position), mStorage.get(item.getTime()));
        }
    }

    /**
     * 取消所有选择
     */
    public void cancelSelected() {
        fixSelected(-1);
    }

    /**
     * 锚定颜色
     */
    @SuppressWarnings("all")
    public void fixSelected(int type) {
        ArrayList<Integer> selects;
        DayCell cells;
        Cell cell;
        long key;
        for (int i =0; i< mSelects.size(); i++) {
            key = mSelects.keyAt(i);
            selects = mSelects.get(key);
            cells = mStorage.get(key);
            if (cells != null) {
                for (int j = 0; j < selects.size(); j++) {
                    cell = cells.get(selects.get(j));
                    if (cell != null) {
                        cell.setSelected(false);
                        if (type != -1) {
                            cell.setType(type);
                        }
                    }

                }
            }
        }
        mSelects.clear();
        getView().refreshLeft();
        DataStorage.saveRecordData(mStorage);
    }
}
