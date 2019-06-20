package com.jason.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jason.common.enums.SexEnum;
import com.jason.common.po.SubUser;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    public static <T> List<T> json2JavaBeanList(String jsonStr, Class<T> cla) {
        List<T> list = JSON.parseArray(jsonStr, cla);
        return list;
    }

    public static List<SubUser> json2SubUserList(String jsonStr) {
        JSONArray jsonArray = JSON.parseArray(jsonStr);
        List<SubUser> list = new ArrayList<>();
        for (int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            SubUser subUser = new SubUser();
            subUser.setId(jsonObject.getInteger("id"));
            subUser.setSex(SexEnum.getSexEnumByType(jsonObject.getInteger("sex")));
            if(StringUtil.isNumeric(jsonObject.getString("age"))){
                subUser.setAge(jsonObject.getInteger("age"));
            }
            subUser.setSubUserName(jsonObject.getString("subUserName"));
            list.add(subUser);
        }
        return list;
    }
}
