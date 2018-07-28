package com.jim.recorder.ui.pressenter;

import android.graphics.Color;
import android.util.SparseArray;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.jim.recorder.api.CellManager;
import com.jim.recorder.api.DataStorage;
import com.jim.recorder.api.DayCellManager;
import com.jim.recorder.api.EventTypeManager;
import com.jim.recorder.model.Cell;
import com.jim.recorder.model.Constants;
import com.jim.recorder.model.DayCell;
import com.jim.recorder.model.EventType;
import com.jim.recorder.ui.callback.DayFixView;
import com.jim.recorder.ui.model.ViewCell;
import com.jim.recorder.ui.model.SingleModel;
import com.jim.recorder.utils.CalendarUtil;
import com.jim.recorder.utils.TemplateColor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tauren on 2018/5/19.
 */

public class DayFixNewPressenter extends MvpBasePresenter<DayFixView> {

    private long mSelectDay = -1;
    private Calendar mNowCalendar;
    private List<SingleModel> mViewData;
    private final int MAX_SIZE = 120;

    public List<EventType> getEventTypes() {
        return EventTypeManager.getInstance().getEventList();
    }

    public void updateTimeCalendar() {
        mNowCalendar = CalendarUtil.getCalendarDayStart(Calendar.getInstance());
        mSelectDay = mNowCalendar.getTimeInMillis();
    }

    /**
     * 更新主页面状态
     */
    public void updateTitle() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mSelectDay);
        String title = String.valueOf(calendar.get(Calendar.YEAR)) + "/" +
                (calendar.get(Calendar.MONTH) + 1) + "/"+(calendar.get(Calendar.DATE));
        getView().updateTitle(title);
    }

    /**
     * 更新底部选择状态
     * @param selected
     */
    public void updateSelectedIndicator(Set<Integer> selected) {
        int size = 0;
        for (Integer index : selected) {
            if (index%5 !=0 )
                size++;
        }
        getView().updateSelectedIndicator(size);
    }

    public void selectTime(Calendar calendar) {
        mNowCalendar = calendar;
        mSelectDay = calendar.getTimeInMillis();
        updateTitle();
        refreshContent();
    }

    public void refreshData() {
        refreshEventList();
        refreshContent();
    }

    public void refreshEventList() {
        getView().updateEventList(getEventTypes());
    }

    /**
     * 更新左部选择内容
     */
    public void refreshContent() {
        if (mViewData == null) {
            mViewData = new ArrayList<>();
        }
        mViewData.clear();
        for (int i=0; i< 120; i++) {
            if (i%5 == 0)
                mViewData.add(new SingleModel(true));
            else
                mViewData.add(new SingleModel(Color.parseColor("#ebebeb"),false));
        }
        List<Cell> cellList = CellManager.getInstance().getDay(mSelectDay);
        Cell item;
        SingleModel change;
        for (int i = 0; i < cellList.size(); i++) {
            item = cellList.get(i);
            int position = (int) ((item.getTime() - mSelectDay)/(Constants.one_min * 15));
            int index = (position/4)*5+position%4+1;
            change = mViewData.get(index);
            EventType eventType = EventTypeManager.getInstance().getEventType(item.getTypeId());
            if (eventType == null) {
                change.setColor(Color.parseColor("#ebebeb"));
                change.setEventId(-1);
                change.setName("");
            } else {
                change.setColor(TemplateColor.getColor(eventType.getType()));
                change.setEventId(item.getTypeId());
                change.setName(eventType.getName());
            }
        }
        getView().updateContent(mViewData);
//        Observable.fromArray(cellList.toArray())
    }

    public void handleLeftClick(View view, int position) {
        SingleModel singleModel = mViewData.get(position);
        getView().updateLeftCell(view, singleModel, position);
    }

    public void judgeStatus(HashSet<Integer> selected, EventType eventType) {
        for (Integer index : selected) {
            SingleModel singleModel = mViewData.get(index);
            if (singleModel.getEventId() != -1) {
                getView().labelCoverWarning(eventType);
                return;
            }
        }
        fixSelected(selected, eventType);

    }

    public void fixSelected(HashSet<Integer> selected, EventType eventType) {
        if (selected.size() == 0)
            return;
        SingleModel change;
        for(Integer index : selected) {
            change = mViewData.get(index);
            change.setColor(TemplateColor.getColor(eventType.getType()));
            change.setEventId(eventType.get_id());
            change.setName(eventType.getName());
        }
        getView().updateAfterFix();
        convertToDayCell();
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

    private void convertToDayCell() {
        List<Cell> storageData = new ArrayList<>();
        SingleModel item;
        long typeId = 0l;
        for (int i=0; i<mViewData.size(); i++) {
            if (i%5 == 0)
                continue;
            item = mViewData.get(i);
            typeId = item.getEventId();
            if (typeId != -1) {
                int position = 4*(i/5) + i%5;
                storageData.add(new Cell(mSelectDay + (position-1) * Constants.one_min * 15, typeId));
            }
        }
        CellManager.getInstance().save(storageData);
    }

    public void wipeData(Set<Integer> selected) {
        if (selected.size() == 0)
            return;
        SingleModel change;
        List<Long> delData = new ArrayList<>();
        for(Integer index : selected) {
            change = mViewData.get(index);
            // 删除数据库中的数据
            if (change.getEventId() != -1) {
                int position = 4*(index/5) + index%5;
                delData.add(mSelectDay + (position-1) * Constants.one_min * 15);
            }
            change.setEventId(-1);
            change.setColor(Color.parseColor("#ebebeb"));
            change.setName("");
        }
        CellManager.getInstance().delByKey(delData);
        getView().updateAfterFix();
    }
}
