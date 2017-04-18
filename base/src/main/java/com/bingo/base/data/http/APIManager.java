package com.bingo.base.data.http;

import com.bingo.base.BuildConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by znjfuser on 2017/4/14.
 * Description : api控制中心
 */

public class APIManager {
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String BASE_URL = BuildConfig.API_URL;
    private static OkHttpClient client;
    private static ConcurrentHashMap<String, Object> APICache = new ConcurrentHashMap<>(10);

    private volatile static APIManager apiManager;


    private String getBaseUrl() {
        return BASE_URL;
    }


    public static APIManager getInstance() {
        if (apiManager == null) {
            synchronized (APIManager.class) {
                if (apiManager == null) {
                    apiManager = new APIManager();
                }
            }
        }
        return apiManager;
    }

    /**
     * 获取对应API
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getAPI(Class<T> clazz) {
        if (null == client) {
            client = createOkHttpClient();
        }
        T api = (T) APICache.get(clazz.getName());
        if (api == null) {
            synchronized (APIManager.class) {
                if (api == null) {
                    api = new Retrofit.Builder().client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl(getBaseUrl())
                            .build()
                            .create(clazz);
                    APICache.put(clazz.getName(), api);
                }
            }
        }
        return api;
    }

    /**
     * 新建一般API http client
     *
     * @return
     */
    private OkHttpClient createOkHttpClient() {
        //retrofit2 配置 HttpLoggingInterceptor打印日志
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        if (DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(logging);
        }

        return httpClientBuilder
                .connectTimeout(HttpConfig.HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(HeaderInterceptor.getInstance())
                .build();
    }
}
