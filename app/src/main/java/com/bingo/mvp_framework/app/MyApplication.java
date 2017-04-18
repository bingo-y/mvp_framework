package com.bingo.mvp_framework.app;

import android.app.Application;

import com.bingo.mvp_framework.io.db.DBManager;

/**
 * Created by tuyx on 2017/4/18.
 * Description :
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.getInstance(getApplicationContext());
    }
}
