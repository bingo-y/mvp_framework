package com.bingo.mvp_framework.app;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bingo.mvp_framework.BuildConfig;
import com.bingo.mvp_framework.io.app.ApplicationIo;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import timber.log.Timber;

/**
 * Created by tuyx on 2017/4/18.
 * Description :
 */

public class MyApplication extends Application {
    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化leak canary
        installLeakCanary();
        if (BuildConfig.DEBUG) {
            //开启日志
            Timber.plant(new Timber.DebugTree());
            ARouter.openLog();
            ARouter.openDebug();
        }
        //初始化路由
        ARouter.init(this);
        //系统初始化
        ApplicationIo.getInstance(getApplicationContext()).tryInitializeCore();
    }

    private void installLeakCanary() {
        refWatcher = BuildConfig.DEBUG ? LeakCanary.install(this) : RefWatcher.DISABLED;
    }
}
