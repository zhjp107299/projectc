package com.seven.utils;



/**
 * 封装了一个响应体
 */
public class AssembleResponseMsg {
    /**
     * 成功返回内容
     */
    public <T>ResponseBody success(T data){
        ResponseBody<T> response = new ResponseBody<T>();
        response.setData(data);
        InfoMsg info = new InfoMsg();
        response.setInfo(info);//默认code0和msg操作成功
        return response;
    }

    /**
     * 失败或者异常返回内容
     */
    public <T>ResponseBody failure(int errorCode,String msg){
        ResponseBody<T> response = new ResponseBody<T>();
        InfoMsg info = new InfoMsg();
        info.setCode(errorCode);
        info.setMsg(msg);
        response.setInfo(info);
        return response;
    }
}
