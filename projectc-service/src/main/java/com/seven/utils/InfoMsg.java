package com.seven.utils;

/**
 * 封装了一个用于响应体内的消息体
 */
public class InfoMsg {

    private int code;//状态码
    private String msg;//消息体

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
