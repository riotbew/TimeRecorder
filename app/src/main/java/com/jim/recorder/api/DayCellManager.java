package com.jim.recorder.api;

import com.jim.recorder.model.DayCell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tauren on 2018/5/20.
 */

public class DayCellManager {

    private static List<DayCell> mDatas = new ArrayList<>();
    private static DayCellManager mInstance;

    public static synchronized DayCellManager getInstance() {
        if (mInstance == null) {
            mInstance = new DayCellManager();
        }
        return mInstance;
    }

    public void refreshData() {
        mDatas = DataStorage.getRecordDataList();
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
    }

    public void putData(DayCell data) {
        DataStorage.saveDayCell(data);
    }

    public DayCell getData(Long key) {
        return DataStorage.getDayCell(key);
    }


}
