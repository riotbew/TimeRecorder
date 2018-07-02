package com.jim.recorder.ui.pressenter;

import android.graphics.Color;
import android.util.SparseArray;
import android.view.View;

import com.codbking.calendar.CalendarBean;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.jim.recorder.api.DataStorage;
import com.jim.recorder.api.DayCellManager;
import com.jim.recorder.api.EventTypeManager;
import com.jim.recorder.ui.model.ViewCell;
import com.jim.recorder.model.DayCell;
import com.jim.recorder.model.EventType;
import com.jim.recorder.ui.model.SingleModel;
import com.jim.recorder.ui.callback.DayFixView;
import com.jim.recorder.utils.CalendarUtil;
import com.jim.recorder.utils.TemplateColor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Tauren on 2018/5/19.
 */

public class DayFixPressenter extends MvpBasePresenter<DayFixView> {

    private long mSelectDay = -1;
    private Calendar mNowCalendar;
    private List<SingleModel> mViewData;

    public List<EventType> getEventTypes() {
        return EventTypeManager.getInstance().getEventList();
    }

    public void updateTimeCalendar() {
        mNowCalendar = CalendarUtil.getCalendarDayStart(Calendar.getInstance());
        mSelectDay = mNowCalendar.getTimeInMillis();
    }

    public void updateTitle() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mSelectDay);
        String title = String.valueOf(calendar.get(Calendar.YEAR)) + "/" +
                (calendar.get(Calendar.MONTH) + 1) + "/"+(calendar.get(Calendar.DATE));
        getView().updateTitle(title);
    }

    public void updateSelectedIndicator(Set<Integer> selected) {
        int size = 0;
        for (Integer index : selected) {
            if (index%5 !=0 )
                size++;
        }
        getView().updateSelectedIndicator(size);
    }

    public void selectTime(CalendarBean bean) {
        Calendar calendar = Calendar.getInstance();
        calendar = CalendarUtil.getCalendarDayStart(calendar);
        calendar.set(bean.year, bean.moth-1, bean.day);
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

    public void refreshContent() {
        if (mViewData == null) {
            mViewData = new ArrayList<>();
        }
        mViewData.clear();
        for (int i=0; i< 120; i++) {
            if (i%5 == 0) {
                mViewData.add(new SingleModel(true));
            } else {
                mViewData.add(new SingleModel(Color.parseColor("#ebebeb"),false));
            }
        }
        DayCell dayCell = DayCellManager.getInstance().getData(mSelectDay);
        if (dayCell != null) {
            SparseArray<ViewCell> cells = dayCell.getDatas();
            int key;
            ViewCell item;
            SingleModel change;
            for (int i=0; i< cells.size(); i++) {
                key = cells.keyAt(i);
                item = cells.get(key);
                if (item == null)
                    continue;
                int position = (int) item.getPosition()-1;
                int index = (position/4)*5+position%4+1;
                change = mViewData.get(index);
                EventType eventType = EventTypeManager.getInstance().getEventType(item.getEventId());
                if (eventType == null) {
                    change.setColor(Color.parseColor("#ebebeb"));
                    change.setEventId(-1);
                    change.setName("");
                } else {
                    change.setColor(TemplateColor.getColor(eventType.getType()));
                    change.setEventId(item.getEventId());
                    change.setName(eventType.getName());
                }

            }

        }
        getView().updateContent(mViewData);
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
        DayCell dataToSave = new DayCell(mSelectDay);
        SparseArray<ViewCell> cells = new SparseArray<>();
        SingleModel item;
        for (int i=0; i<mViewData.size(); i++) {
            if (i%5 == 0)
                continue;
            item = mViewData.get(i);
            if (item.getEventId() == -1)
                continue;
            int position = 4*(i/5) + i%5;
            cells.put(position, new ViewCell(item.getEventId(), position));
        }
        if (cells.size() != 0) {
            dataToSave.setDatas(cells);
            DataStorage.saveDayCell(dataToSave);
        } else {
            DataStorage.delDayCell(dataToSave.getTime());
        }
    }

    public void wipeData(Set<Integer> selected) {
        if (selected.size() == 0)
            return;
        SingleModel change;
        for(Integer index : selected) {
            change = mViewData.get(index);
            change.setEventId(-1);
            change.setColor(Color.parseColor("#ebebeb"));
            change.setName("");
        }
        getView().updateAfterFix();
        convertToDayCell();
    }
}
