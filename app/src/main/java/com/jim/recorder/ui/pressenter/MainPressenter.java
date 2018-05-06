package com.jim.recorder.ui.pressenter;

import android.util.LongSparseArray;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.jim.recorder.R;
import com.jim.recorder.abslistview.ViewHolder;
import com.jim.recorder.model.Cell;
import com.jim.recorder.model.Data;
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
    private LongSparseArray<SparseArray<Cell>> mStorage = new LongSparseArray<>();
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

        data2.add(new EventType("吃", 1, R.color.blue));
        data2.add(new EventType("喝",2, R.color.orange_color));
        data2.add(new EventType("玩",3,R.color.colorPrimaryDark));
        data2.add(new EventType("乐",4, R.color.colorPrimary));
        data2.add(new EventType("睡觉",5, R.color.colorAccent));
        data2.add(new EventType("吃", 1, R.color.blue));
        data2.add(new EventType("喝",2, R.color.orange_color));
        data2.add(new EventType("玩",3,R.color.colorPrimaryDark));
        data2.add(new EventType("乐",4, R.color.colorPrimary));
        data2.add(new EventType("睡觉",5, R.color.colorAccent));
        data2.add(new EventType("吃", 1, R.color.blue));
        data2.add(new EventType("喝",2, R.color.orange_color));
        data2.add(new EventType("玩",3,R.color.colorPrimaryDark));
        data2.add(new EventType("乐",4, R.color.colorPrimary));
        data2.add(new EventType("睡觉",5, R.color.colorAccent));
        data2.add(new EventType("吃", 1, R.color.blue));
        data2.add(new EventType("喝",2, R.color.orange_color));
        data2.add(new EventType("玩",3,R.color.colorPrimaryDark));
        data2.add(new EventType("乐",4, R.color.colorPrimary));
        data2.add(new EventType("睡觉",5, R.color.colorAccent));
        data2.add(new EventType("吃", 1, R.color.blue));
        data2.add(new EventType("喝",2, R.color.orange_color));
        data2.add(new EventType("玩",3,R.color.colorPrimaryDark));
        data2.add(new EventType("乐",4, R.color.colorPrimary));
        data2.add(new EventType("睡觉",5, R.color.colorAccent));
        data2.add(new EventType("吃", 1, R.color.blue));
        data2.add(new EventType("喝",2, R.color.orange_color));
        data2.add(new EventType("玩",3,R.color.colorPrimaryDark));
        data2.add(new EventType("乐",4, R.color.colorPrimary));
        data2.add(new EventType("睡觉",5, R.color.colorAccent));
    }

    public ArrayList<EventType> getLabelData() {
        return data2;
    }

    public void updateTime() {
        now = Calendar.getInstance();
    }

    public void handleLeftClick(View v, Data item, Object position) {
        if (position == null) {
            return;
        }
        int pos = (Integer) position;
        long timeStamp = item.getTime();
        SparseArray<Cell> cells = mStorage.get(timeStamp);
        Cell cell = null;
        if (cells == null) {
            cells = new SparseArray<>();
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
        getView().updateCellAfterClick(v,cell);
    }

    public void handleDayCellRender(ViewHolder viewHolder, final Data item, int position) {
        ViewGroup container = (ViewGroup)viewHolder.getConvertView();
        ViewGroup content = container.findViewById(R.id.day_content);

        if (mStorage.get(item.getTime()) == null) {
            getView().resetLeftDayCellView(content, data1.get(position));
        } else {
            if (content.getChildCount() == 0) {
                getView().resetLeftDayCellView(content, data1.get(position));
            }
            getView().updateLeftDayCellView(content, data1.get(position),mStorage.get(item.getTime()));
        }
    }

}
