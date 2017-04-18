package com.bingo.base.data.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by znjfuser on 2017/4/14.
 */

public class HeaderInterceptor implements Interceptor {

    private static HeaderInterceptor instance;

    public static HeaderInterceptor getInstance() {
        if (instance == null) {
            instance = new HeaderInterceptor();
        }
        return instance;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        //add some common parameters
        Request originalRequest = chain.request();
        HttpUrl.Builder requestBuilder = originalRequest.url().newBuilder();
        if (HttpCommonParams.getInstance() != null) {
            Map<String, String> params = HttpCommonParams.getInstance().getParams();
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                requestBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }

        Request.Builder builder = chain.request().newBuilder()
                .addHeader("Accept-Charset", "utf-8");
        builder.url(requestBuilder.build());
        return chain.proceed(builder.build());
    }
}
