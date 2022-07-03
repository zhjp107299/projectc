package com.seven.utils;

import java.io.Serializable;

/**
 * 封装了一个用于响应体的数据结构
 */
public class ResponseBody<T> implements Serializable {

    //接口返回的数据
    private T data;
    //消息内容
    private InfoMsg info;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public InfoMsg getInfo() {
        return info;
    }

    public void setInfo(InfoMsg info) {
        this.info = info;
    }
}
