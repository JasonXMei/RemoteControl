package com.jason.common.util;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class StringUtil {
    public static String generateInviteCode(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String encodeStr(String str) {
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "服务器内部异常!";
        }
    }
}
