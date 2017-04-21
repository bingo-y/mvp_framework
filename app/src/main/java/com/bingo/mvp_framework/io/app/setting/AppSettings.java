package com.bingo.mvp_framework.io.app.setting;

import com.bingo.base.constant.GlobalConfig;
import com.bingo.base.data.prefernce.PreferencesManager;

/**
 * Created by tuyx on 2017/4/20.
 * Description :
 */

public class AppSettings {

    /**
     * 获取app版本号
     * @return
     */
    public static int getAppVersionCode() {
        return PreferencesManager.getInstance().getInt(GlobalConfig.SP_KEY_VERSION_CODE, 0);
    }

    /**
     * 存储app版本号
     * @param versionCode
     */
    public static void setAppVersionCode(int versionCode) {
        PreferencesManager.getInstance().putInt(GlobalConfig.SP_KEY_VERSION_CODE, versionCode);
    }

}
