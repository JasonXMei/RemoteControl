package com.jason.common.util;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class JsonUtil {

    public static <T> List<T> json2JavaBeanList(String jsonStr, Class<T> cla) {
        List<T> list = JSON.parseArray(jsonStr, cla);
        return list;
    }
}
