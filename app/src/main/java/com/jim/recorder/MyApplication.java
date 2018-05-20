package com.jim.recorder;

import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDexApplication;

import com.jim.green.gen.DaoMaster;
import com.jim.green.gen.DaoSession;
import com.jim.recorder.api.EventTypeManager;
import com.jim.recorder.utils.CalendarUtil;

public class MyApplication extends MultiDexApplication {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        //配置数据库
        setupDatabase();
        EventTypeManager.getInstance().refreshEvent();
        CalendarUtil.setTimeZone();
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库timeRecorder.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "timeRecorder.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
