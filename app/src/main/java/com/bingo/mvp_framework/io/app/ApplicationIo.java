package com.bingo.mvp_framework.io.app;

import android.content.Context;

import com.bingo.mvp_framework.io.app.initialize.InitializeModule;

/**
 * Created by tuyx on 2017/4/21.
 * Description : 系统核心模块
 */

public class ApplicationIo {
    //单例
    static volatile ApplicationIo instance;
    //上下文
    Context mContext;
    //初始化模块
    volatile InitializeModule initializeModule;

    public ApplicationIo(Context context) {
        this.mContext = context.getApplicationContext();
        this.initializeModule = new InitializeModule(this.mContext);
    }

    /**
     * 获取实例
     */
    public static ApplicationIo getInstance(Context context) {
        if (instance == null) {
            synchronized (ApplicationIo.class) {
                if (instance == null) {
                    instance = new ApplicationIo(context);
                }
            }
        }
        return instance;
    }

    /**
     * 初始化系统模块
     */
    public void tryInitializeCore() {
        initializeModule.tryInitializeCore();
    }

    public InitializeModule getInitializeModule() {
        return initializeModule;
    }
}
