package com.seven.utils;

//进制转换工具类
public class DigestUtil {

    public static String convertToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<bytes.length;i++){
            sb.append(Integer.toHexString(0xff & bytes[i]));
        }
        return sb.toString();
    }
}
