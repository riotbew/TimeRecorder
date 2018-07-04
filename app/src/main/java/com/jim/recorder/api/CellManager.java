package com.jim.recorder.api;

import com.jim.green.gen.CellDao;
import com.jim.recorder.MyApplication;
import com.jim.recorder.model.Cell;
import com.jim.recorder.model.Constants;

import org.greenrobot.greendao.query.QueryBuilder;

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

    public List<Cell> getDay(long originTime) {
        return MyApplication.getDaoInstant().getCellDao().queryBuilder()
                .where(CellDao.Properties.Time.between(String.valueOf(originTime), String.valueOf(originTime+ Constants.one_day)))
                .orderAsc(CellDao.Properties.Time)
                .list();
    }

    public void save(List<Cell> data) {
        MyApplication.getDaoInstant().getCellDao().insertOrReplaceInTx(data);
    }

    public void delByKey(List<Long> data) {
        MyApplication.getDaoInstant().getCellDao().deleteByKeyInTx(data);
    }

    public void del(List<Cell> data) {
        MyApplication.getDaoInstant().getCellDao().deleteInTx(data);
    }

}
