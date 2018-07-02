package com.jim.recorder.api;

import android.app.Application;

import com.jim.green.gen.CellDao;
import com.jim.recorder.MyApplication;
import com.jim.recorder.model.Cell;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

public class CellManager {

    private static List<Cell> mDatas = new ArrayList<>();

    private static class CellManagerHolder {
        static final CellManager sInstance = new CellManager();
    }

    public static CellManager getInstance() {
        return CellManagerHolder.sInstance;
    }

    public void getDay(long originTime) {
        QueryBuilder<Cell> queryBuilder = MyApplication.getDaoInstant().getCellDao().queryBuilder();
        MyApplication.getDaoInstant().getCellDao().queryBuilder().where(CellDao.Properties.MTime.between(String.valueOf(originTime),String.valueOf(originTime)));
//        WhereCondition

    }



}
