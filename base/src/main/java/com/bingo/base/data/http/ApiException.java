package com.bingo.base.data.http;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import timber.log.Timber;

/**
 * Created by znjfuser on 2017/4/14.
 */

public class ApiException extends Throwable {
    @Type
    private int type;
    private RequestError requestError;

    public ApiException(@Type int type, RequestError requestError, String message) {
        super(message);
        this.type = type;
        this.requestError = requestError;
        Timber.e("ApiException: " + requestError.getErrorCode() + "_" + requestError.getErrorMsg());
    }

    public RequestError getRequestError() {
        return requestError;
    }

    @Type
    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "{type=" + type +
                ", requestError=" + requestError +
                '}';
    }

    // 网络异常
    public final static int TYPE_HTTP = 1;
    // 服务端数据异常
    public final static int TYPE_SERVER_DATA = 2;
    // onNext里面的异常
    public final static int TYPE_ON_NEXT = 3;

    @IntDef({TYPE_HTTP, TYPE_SERVER_DATA,TYPE_ON_NEXT})
    @Retention(RetentionPolicy.SOURCE)
    @interface Type {
    }
}
