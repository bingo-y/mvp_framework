package com.bingo.mvp_framework.ui.splash;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bingo.base.ui.BaseActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * Created by tuyx on 2017/4/25.
 * Description : 启动页
 */

public class SplashActivity extends BaseActivity implements SplashContract.View {
    SplashPresenter splashPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashPresenter = new SplashPresenter(this, new RxPermissions(this));
        registerPresenter(splashPresenter);

        splashPresenter.requestPermission();
    }


    @Override
    public void permissionsGranted() {

    }

    @Override
    public void shouldShowRequestPermissions() {

    }

    @Override
    public void openPermissionsInSetting() {
        //跳转到权限设置页面
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(localIntent);
    }
}
