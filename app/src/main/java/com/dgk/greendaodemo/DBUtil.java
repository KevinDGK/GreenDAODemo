package com.dgk.greendaodemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import de.greenrobot.dao.DaoMaster;
import de.greenrobot.dao.DaoSession;
import de.greenrobot.dao.StudentDao;

/**
 * Created by Kevin on 2016/7/4.
 * 数据库帮助类，该类是为了将生成的DAOs进一步封装，方便在使用数据库的时候进行初始化操作，
 * 以及获得各种已经初始化完成后的帮助类的实例。
 */
public class DBUtil {

    private static SQLiteDatabase db;       // 数据库对象
    private static DaoMaster daoMaster;     // 数据库的总的管理类，用于获得各种DAOs的实例
    private static DaoSession daoSession;   // 数据库交互的会话类，管理每次对数据库的CRUD操作

    /*
        一般情况下，为了安全，数据库创建在/data/data/包名/databases目录下即可，但是demo中为了随时将
        数据库从手机中取出，查看数据库的数据，所以暂时将数据库建立在sd卡中。
     */
    private static String dbName = Environment.getExternalStorageDirectory().getPath()
            + File.separator + "com.dgk.greendaodemo"
            + File.separator + "db"
            + File.separator + "demo.db";

    /**
     * 初始化数据库
     *  一般在Application层进行数据库的初始化，当然，如果在应用使用数据库的地方较少，
     *  也可以在第一次使用之前进行数据库的初始化。
     *  - SQLiteDatabase：数据库
     *  - DaoMaster：GreenDAO框架的管理类
     *  - DaoSession：提供获取DAO组件的方法, 提供便利的支持的方法, 并且提供数据库的会话缓存功能
     *                (即相当于数据库的一级缓存？)。
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

    /** 获得数据库 */
    public static SQLiteDatabase getDB() {
        return db;
    }

    /** 获得数据库的Session类 */
    public static DaoSession getDaoSession() {
        return daoSession;
    }

    /** 获得数据库的Session类 */
    public static StudentDao getStudentDao() {
        return daoSession.getStudentDao();
    }
}
