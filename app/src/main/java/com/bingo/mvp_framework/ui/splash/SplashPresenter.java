package com.bingo.mvp_framework.ui.splash;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import com.bingo.base.ui.BasePresenter;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tuyx on 2017/4/25.
 * Description :
 */

public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {
    private RxPermissions rxPermissions;

    public SplashPresenter(SplashContract.View view, @NonNull RxPermissions rxPermissions) {
        super(view);
        this.rxPermissions = rxPermissions;
    }

    @Override
    public void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Disposable disposable = rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(@NonNull Permission permission) throws Exception {
                            if (permission.granted) {
                                // `permission.name` is granted !
                                getView().permissionsGranted();
                            } else if (permission.shouldShowRequestPermissionRationale) {
                                // Denied permission without ask never again
                                getView().shouldShowRequestPermissions();
                            } else {
                                // Denied permission with ask never again
                                // Need to go to the settings
                                getView().openPermissionsInSetting();
                            }
                        }
                    });
            subscribe(disposable);
        }
    }
}
