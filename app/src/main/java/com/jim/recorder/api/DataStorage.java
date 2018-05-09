package com.jim.recorder.api;

import android.util.LongSparseArray;

import com.jim.recorder.model.DayCell;

public class DataStorage {
    // TODO 完成数据持久化和加载
    public static void saveRecordData(LongSparseArray<DayCell> data) {
        long key;
        DayCell cells;
        for (int i =0; i< data.size(); i++) {
            key = data.keyAt(i);
            cells = data.get(key);
            if (cells != null) {
                for (int j = 0; j < cells.size(); j++) {
//                    cell = cells.get(selects.get(j));
//                    if (cell != null) {
//                        cell.setSelected(false);
//                        if (type != -1) {
//                            cell.setType(type);
//                        }
//                    }

                }
            }
        }

    }

    public static LongSparseArray getRecordData() {
        return new LongSparseArray<>();
    }
}
