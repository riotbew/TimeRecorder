package com.jim.recorder.ui.pressenter;

import android.graphics.Color;
import android.util.SparseArray;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.jim.recorder.api.DataStorage;
import com.jim.recorder.api.DayCellManager;
import com.jim.recorder.api.EventTypeManager;
import com.jim.recorder.ui.model.Cell;
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
        String title = String.valueOf(calendar.get(Calendar.YEAR)) + "年" +
                (calendar.get(Calendar.MONTH) + 1) + "月 ";
        getView().updateTitle(title);
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
            SparseArray<Cell> cells = dayCell.getDatas();
            int key;
            Cell item;
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

    private void convertToDayCell() {
        DayCell dataToSave = new DayCell(mSelectDay);
        SparseArray<Cell> cells = new SparseArray<>();
        SingleModel item;
        for (int i=0; i<mViewData.size(); i++) {
            if (i%5 == 0)
                continue;
            item = mViewData.get(i);
            if (item.getEventId() == -1)
                continue;
            int position = 4*(i/5) + i%5;
            cells.put(position, new Cell(item.getEventId(), position));
        }
        dataToSave.setDatas(cells);
        DataStorage.saveDayCell(dataToSave);
    }
}
