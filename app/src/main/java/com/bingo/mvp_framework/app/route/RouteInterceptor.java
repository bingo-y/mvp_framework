package com.bingo.mvp_framework.app.route;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * Created by tuyx on 2017/4/19.
 * Description : 路由拦截器
 */
@Interceptor(priority = 5)
public class RouteInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {

    }

    @Override
    public void init(Context context) {

    }
}
