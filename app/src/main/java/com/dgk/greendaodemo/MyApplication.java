package com.dgk.greendaodemo;

import android.app.Application;

/**
 * Created by Kevin on 2016/7/4.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DBUtil.init(getApplicationContext());   // 初始化数据库
    }
}
