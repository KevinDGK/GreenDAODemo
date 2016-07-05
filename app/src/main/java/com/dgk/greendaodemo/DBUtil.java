package com.dgk.greendaodemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import de.greenrobot.dao.DaoMaster;
import de.greenrobot.dao.DaoSession;

/**
 * Created by Kevin on 2016/7/4.
 * 数据库帮助类
 */
public class DBUtil {

    private static SQLiteDatabase db;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    private static String dbName = Environment.getExternalStorageDirectory().getPath() + File.separator + "com.dgk.greendaodemo"
            + File.separator + "db"
            + File.separator + "demo.db";

    /**
     * 初始化数据库
     *  - SQLiteDatabase：数据库
     *  - DaoMaster：GreenDAO框架的管家(主要的对象)
     *  - DaoSession：提供获取DAO组件的方法, 提供便利的支持的方法, 并且提供会话缓存服务？
     */
    public static void init(Context ctx){

        Log.i("【数据库位置】",dbName);

        if (daoSession == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(ctx, dbName, null);
            db = helper.getWritableDatabase();
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
        }
    }

    public static SQLiteDatabase getDB() {
        return db;
    }


    public static DaoSession getDaoSession() {
        return daoSession;
    }

}
