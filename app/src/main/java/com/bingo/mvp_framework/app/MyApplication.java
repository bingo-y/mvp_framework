package com.bingo.mvp_framework.app;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bingo.mvp_framework.BuildConfig;
import com.bingo.mvp_framework.io.db.DBManager;

/**
 * Created by tuyx on 2017/4/18.
 * Description :
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        //初始化arouter
        ARouter.init(this);
        DBManager.getInstance(getApplicationContext());
    }
}
