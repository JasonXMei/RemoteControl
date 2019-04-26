package com.jason.common;

import com.jason.common.po.SubUser;
import com.jason.common.util.JsonUtil;
import org.junit.Test;

import java.util.List;

public class JsonUtilTest {

    @Test
    public void test(){
        String s = "[]";
        List<SubUser> list = JsonUtil.json2JavaBeanList(s, SubUser.class);
        for (SubUser user: list) {
            System.out.println(user.getSubUserName());
        }
    }
}
