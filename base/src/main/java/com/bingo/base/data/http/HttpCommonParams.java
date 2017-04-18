package com.bingo.base.data.http;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by znjfuser on 2017/4/14.
 * Description :
 */

public class HttpCommonParams {
    private static Map<String, String> PARAMS;

    private static HttpCommonParams instance;

    private HttpCommonParams() {
    }

    public static HttpCommonParams getInstance() {
        if (instance == null) {
            synchronized (HttpCommonParams.class) {
                if (instance == null) {
                    instance = new HttpCommonParams();
                }
            }
        }
        return instance;
    }


    /**
     * get params
     *
     * @return common params
     */
    public Map<String, String> getParams() {
        return PARAMS;
    }

    /**
     * addParam params to common params
     *
     * @param key   params key
     * @param value params value
     * @return common params
     */
    public Map<String, String> addParam(@NonNull String key, @NonNull String value) {
        if (null == PARAMS) {
            PARAMS = new HashMap<>();
        }
        PARAMS.put(key, value);
        return PARAMS;
    }

    /**
     * clearParam params
     */
    public void clearParams() {
        if (null != PARAMS) {
            PARAMS.clear();
        }
    }
}
