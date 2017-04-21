package com.bingo.base.data.http;

/**
 * Created by tuyx on 2017/4/20.
 * Description : 请求返回实体
 */

public class BaseResult<T> {
    /**
     * 响应码
     */
    int code;

    /**
     * 消息提示
     */
    String message;

    /**
     * 实体数据
     */
    T data;

    public BaseResult() {
    }

    public BaseResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
