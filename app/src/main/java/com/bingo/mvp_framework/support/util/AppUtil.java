package com.bingo.mvp_framework.support.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import timber.log.Timber;

/**
 * 系统工具
 */
public class AppUtil {
    //前台运行
    public static final int RUNNING_FRONT = 1;
    //后台运行
    public static final int RUNNING_BACKGROUND = 2;
    //未运行
    public static final int RUNNING_NOT = 3;

    /**
     * 获取当前进程的名称
     */
    @Nullable
    static public String getProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (mActivityManager.getRunningAppProcesses() != null) {
            Iterator iterator = mActivityManager.getRunningAppProcesses().iterator();

            ActivityManager.RunningAppProcessInfo appProcess;
            do {
                if (!iterator.hasNext()) {
                    return null;
                }
                appProcess = (ActivityManager.RunningAppProcessInfo) iterator.next();
            } while (appProcess.pid != pid);

            return appProcess.processName;
        }
        return null;
    }

    /**
     * 重启App
     */
    static public void kill(Context context, boolean restart, Class<? extends Activity> launchClz) {
        //重启
        if (restart) {
            //关闭除了首页以外的页面
            Intent intent = new Intent(context, launchClz);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

        //停止后台 service
        try {
            //读取所有的SERVICE信息
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SERVICES);
            if (packageInfo != null && packageInfo.services != null) {
                for (ServiceInfo serviceInfo : packageInfo.services) {
                    try {
                        Class cls = Class.forName(serviceInfo.name);
                        context.stopService(new Intent(context, cls));
                    } catch (ClassNotFoundException e) {
                    }
                }
            }
        } catch (Exception e) {
            Timber.e(e.getMessage());
        }
        //杀死所有进程
        try {
            Set<String> processNameSet = new HashSet<String>();
            //读取 所有的Process
            {
                //读取Activity
                {
                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
                    if (packageInfo != null && packageInfo.activities != null) {
                        for (ActivityInfo activityInfo : packageInfo.activities) {
                            processNameSet.add(activityInfo.processName);
                        }
                    }
                }
                //读取Service
                {
                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SERVICES);
                    if (packageInfo != null && packageInfo.services != null) {
                        for (ServiceInfo serviceInfo : packageInfo.services) {
                            processNameSet.add(serviceInfo.processName);
                        }
                    }
                }
                //读取 RECEIVERS
                {
                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_RECEIVERS);
                    if (packageInfo != null && packageInfo.receivers != null) {
                        for (ActivityInfo activityInfo : packageInfo.receivers) {
                            processNameSet.add(activityInfo.processName);
                        }
                    }
                }
                //读取 PROVIDERS
                {
                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PROVIDERS);
                    if (packageInfo != null && packageInfo.providers != null) {
                        for (ProviderInfo providerInfo : packageInfo.providers) {
                            processNameSet.add(providerInfo.processName);
                        }
                    }
                }
            }
            //关闭所有进程
            {
                int myPid = android.os.Process.myPid();
                ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                Iterator iterator = mActivityManager.getRunningAppProcesses().iterator();
                while (iterator.hasNext()) {
                    ActivityManager.RunningAppProcessInfo appProcess = (ActivityManager.RunningAppProcessInfo) iterator.next();
                    if (processNameSet.contains(appProcess.processName)) {
                        //先KILL其他进程
                        if (appProcess.pid != myPid) {
                            android.os.Process.killProcess(appProcess.pid);
                        }
                    }
                }
                //杀死自己
                android.os.Process.killProcess(myPid);
            }
        } catch (Exception e) {
            Timber.e(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void simpleKill(Context context) {
        //杀死所有activity进程
        try {
            Set<String> processNameSet = new HashSet<String>();
            //读取Activity
            {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
                if (packageInfo != null && packageInfo.activities != null) {
                    for (ActivityInfo activityInfo : packageInfo.activities) {
                        processNameSet.add(activityInfo.processName);
                    }
                }
            }
            //关闭所有activity进程
            {
                int myPid = android.os.Process.myPid();
                ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                Iterator iterator = mActivityManager.getRunningAppProcesses().iterator();
                while (iterator.hasNext()) {
                    ActivityManager.RunningAppProcessInfo appProcess = (ActivityManager.RunningAppProcessInfo) iterator.next();
                    if (processNameSet.contains(appProcess.processName)) {
                        //先KILL其他进程
                        if (appProcess.pid != myPid) {
                            android.os.Process.killProcess(appProcess.pid);
                        }
                    }
                }
                //杀死自己
                android.os.Process.killProcess(myPid);
            }
        } catch (Exception e) {
            Timber.e(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取手机版本信息
     */
    static public String getPlatform() {
        StringBuilder sb = new StringBuilder("Android:");
        sb.append(android.os.Build.VERSION.RELEASE);
        sb.append(" MODEL:");
        sb.append(android.os.Build.MODEL);
        return sb.toString();
    }

    /**
     * 获取AndroidManifest.xml 中的versioncode, <strong> 如果出现任何的异常，则返回 0</strong>
     */
    static public int getVersionCodeInApk(Context context) {
        int versionCode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e.getMessage());
        }
        return versionCode;
    }

    /**
     * 获取AndroidManifest.xml 中的versionname, <strong>如果出现任何的异常，返回null</strong>
     */
    @Nullable
    static public String getVersionNameInApk(Context context) {
        String versionName = null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e.getMessage());
        }
        return versionName;
    }

    /**
     * 返回app运行状态
     * 1:程序在前台运行
     * 2:程序在后台运行
     * 3:程序未启动
     * 注意：需要配置权限<uses-permission android:name="android.permission.GET_TASKS" />
     */
    public static int getAppStatus(Context context, String pageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(20);

        //判断程序是否在栈顶
        if (list.get(0).topActivity.getPackageName().equals(pageName)) {
            //前台运行
            return RUNNING_FRONT;
        } else {
            //判断程序是否在栈里
            //后台运行
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals(pageName)) {
                    return RUNNING_BACKGROUND;
                }
            }
            //栈里找不到，返回3
            return RUNNING_NOT;
        }
    }

    /**
     * 判断activity是否运行在栈顶
     * @param context
     * @param activityName 目标activity名
     * @return
     */
    public static boolean isActivityOnTop(Context context, @NonNull String activityName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfoList = am.getRunningTasks(1);
        String runningActivityName = null;
        if (runningTaskInfoList != null && runningTaskInfoList.size() > 0) {
            runningActivityName = runningTaskInfoList.get(0).topActivity.toString();
            Timber.d(runningActivityName);
        }
        return activityName.equals(runningActivityName);
    }
}
