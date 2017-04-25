package com.bingo.mvp_framework.ui.splash;

import com.bingo.base.ui.IView;

/**
 * Created by tuyx on 2017/4/25.
 * Description :
 */

public interface SplashContract {
    interface View extends IView {

        /**
         * 权限已授予
         */
        void permissionsGranted();

        /**
         * 权限未授予，可以显示申请权限框
         */
        void shouldShowRequestPermissions();

        /**
         * 权限未授予，不在显示申请框，去设置打开
         */
        void openPermissionsInSetting();
    }

    interface Presenter {

        /**
         * 权限请求
         */
        void requestPermission();
    }
}
