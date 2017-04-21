package com.bingo.mvp_framework.io.app.initialize;

import android.content.Context;
import android.os.Looper;

import com.bingo.base.data.prefernce.PreferencesManager;
import com.bingo.mvp_framework.io.app.setting.AppSettings;
import com.bingo.mvp_framework.io.db.DBManager;
import com.bingo.mvp_framework.support.util.AppUtil;

import timber.log.Timber;

/**
 * Created by tuyx on 2017/4/20.
 * Description : 初始化模块
 */

public class InitializeModule {
    Context mContext;
    //初始化标志
    volatile boolean initializedCoreState;

    public InitializeModule(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    /**
     * 初始化系统模块,且<strong>必须在主线程进行</strong>
     */
    public synchronized void tryInitializeCore() {
        //如果不在主线程，或者不在主进程,则忽略
        if (!mContext.getPackageName().equals(AppUtil.getProcessName(mContext))
                && Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("tryInitializeCore should in Main");
        }
        Timber.d("Call App#tryInitializeCore()");
        //初始化
        tryInitializeCoreStage();
    }

    /**
     * 初始化系统模块
     */
    void tryInitializeCoreStage() {
        //已经初始化，则忽略
        if (initializedCoreState) {
            return;
        }
        //初始化
        {
            // share preferences
            PreferencesManager.init(mContext);
            // db
            DBManager.getInstance(mContext);
            //设置全局异常处理
            {
                //重启
//                Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//                    @Override
//                    public void uncaughtException(Thread thread, Throwable ex) {
//                        Log.e(thread.getName(), ex.getMessage(), ex);
//                    }
//                });
            }
            //版本控制
            {
                //默认版本号
                int appVersion = AppUtil.getVersionCodeInApk(mContext);
                //更新
                int oldVer = AppSettings.getAppVersionCode();
                if (oldVer < appVersion) {
                    //只有之前安装过App的，才能进行升级
                    //没有安装过App的versionCode为0
                    if (oldVer > 0) {
                        onUpgrade(oldVer, appVersion);
                    }
                    //更新数据库中的版本信息
                    AppSettings.setAppVersionCode(appVersion);
                }
            }
            //初始化后台任务
            {
                //检测升级

            }

        }
        //标记初始化成功
        initializedCoreState = true;
    }

    /**
     * App 版本升级
     *
     * @param oldVer     之前存储在数据库中的版本号
     * @param appVersion 现在APP的版本号
     */
    private void onUpgrade(int oldVer, int appVersion) {
        for (int ver = oldVer + 1; ver <= appVersion; ++ver) {
            switch (ver) {

            }
        }
    }
}
