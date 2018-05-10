package com.jim.recorder.api;

import android.util.LongSparseArray;

import com.jim.recorder.MyApplication;
import com.jim.recorder.model.DayCell;

import java.util.ArrayList;
import java.util.List;

public class DataStorage {

    public static void saveRecordData(LongSparseArray<DayCell> data) {
        long key;
        DayCell cells;
        ArrayList<DayCell> dataList = new ArrayList<>();
        for (int i =0; i< data.size(); i++) {
            cells = data.valueAt(i);
            dataList.add(cells);
        }
        MyApplication.getDaoInstant().getDayCellDao().insertOrReplaceInTx(dataList);
    }

    public static void saveRecordData(LongSparseArray<DayCell> data, ArrayList<Long> flag) {
        ArrayList<DayCell> inputContent = new ArrayList<>();
        for (int i=0; i<flag.size(); i++) {
            inputContent.add(data.get(flag.get(i)));
        }
        MyApplication.getDaoInstant().getDayCellDao().insertOrReplaceInTx(inputContent);
    }

    public static LongSparseArray<DayCell> getRecordData() {
        List<DayCell> dayCells = MyApplication.getDaoInstant().getDayCellDao().loadAll();
        LongSparseArray<DayCell> result = new LongSparseArray<>();
        for (DayCell item : dayCells) {
            result.put(item.getTime(), item);
        }

        return result;
    }
}
