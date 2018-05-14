package com.jim.recorder.api;

import android.content.Context;
import android.content.res.Resources;
import android.util.LongSparseArray;

import com.jim.green.gen.CategoryTypeDao;
import com.jim.recorder.MyApplication;
import com.jim.recorder.R;
import com.jim.recorder.model.CategoryType;
import com.jim.recorder.model.DayCell;
import com.jim.recorder.model.EventType;

import java.util.ArrayList;
import java.util.List;

public class DataStorage {

    public synchronized static void saveRecordData(LongSparseArray<DayCell> data) {
        DayCell cells;
        ArrayList<DayCell> dataList = new ArrayList<>();
        for (int i =0; i< data.size(); i++) {
            cells = data.valueAt(i);
            dataList.add(cells);
        }
        MyApplication.getDaoInstant().getDayCellDao().insertOrReplaceInTx(dataList);
    }

    public synchronized static void saveRecordData(LongSparseArray<DayCell> data, ArrayList<Long> flag) {
        ArrayList<DayCell> inputContent = new ArrayList<>();
        for (int i=0; i<flag.size(); i++) {
            inputContent.add(data.get(flag.get(i)));
        }
        MyApplication.getDaoInstant().getDayCellDao().insertOrReplaceInTx(inputContent);
    }

    public synchronized static LongSparseArray<DayCell> getRecordData() {
        List<DayCell> dayCells = MyApplication.getDaoInstant().getDayCellDao().loadAll();
        LongSparseArray<DayCell> result = new LongSparseArray<>();
        for (DayCell item : dayCells) {
            result.put(item.getTime(), item);
        }

        return result;
    }

    public synchronized static void saveEventList(ArrayList<EventType> eventTypes) {
        MyApplication.getDaoInstant().getEventTypeDao().insertOrReplaceInTx(eventTypes);
    }

    public synchronized static List<EventType> getEventList() {
        List<EventType> result;
        try {
            result = MyApplication.getDaoInstant().getEventTypeDao().loadAll();
        } catch (Exception e) {
            result = new ArrayList<>();
        }
        if (result.size() == 0) {
            result.add(new EventType("上班", 0));
            result.add(new EventType("学习",3));
            result.add(new EventType("阅读",5));
            result.add(new EventType("购物",7));
            result.add(new EventType("运动",4));
            result.add(new EventType("聚会", 13));
            result.add(new EventType("睡觉",21));
            result.add(new EventType("洗漱",8));
            result.add(new EventType("写作",18));
            result.add(new EventType("休息", 10));
            result.add(new EventType("闲聊",9));
            result.add(new EventType("吃饭",2));
        }
        return result;
    }

    public synchronized static List<CategoryType> getCategoryTypes(Context context) {
        List<CategoryType> result;
        try {
            result = MyApplication.getDaoInstant().getCategoryTypeDao().loadAll();
        } catch (Exception e) {
            result = new ArrayList<>();
        }
        if (result.size() == 0) {
            Resources res = context.getResources();
            String[] categories =res.getStringArray(R.array.categories);
            String[] tmp;
            for (int i=0; i<categories.length; i++) {
                tmp = categories[i].split(",");
                result.add(new CategoryType(i, tmp[0], tmp[1]));
            }
        }
        return result;
    }
}
