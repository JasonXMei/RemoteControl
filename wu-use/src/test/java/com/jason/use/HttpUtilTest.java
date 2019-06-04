package com.jason.use;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jason.use.config.APIConfig;
import com.jason.use.enums.HttpStatus;
import com.jason.use.util.HttpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class HttpUtilTest {

    @Test
    public void httpGet() {
        HttpUtil.httpGet("http://localhost:8082/user/login?userName=admin&password=123456", null);
    }

    @Test
    public void httpPost() {
        //把参数传进Map中
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userId", "1");
        paramsMap.put("shopId", "1");
        paramsMap.put("description", "test http call");
        paramsMap.put("tagTypeStr", "加购");
        HttpUtil.httpPost(paramsMap, "http://localhost:8082/client/task/saveTag", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTgyMjgyMjUsInVzZXJJZCI6MX0.VPgW3gRHjnbmGl9pPKpWneAyMQZK-BUXzv5GgEUzFL0");
    }

    @Test
    public void subUserList(){
        String response = HttpUtil.httpGet(APIConfig.subUserListUrl, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTg2MTQyNjEsInVzZXJJZCI6MX0.Yj7e7kp98uJl0U-Lclw-O7U-nEBHPBMBCDpAzn5_H5o");
        JSONObject jsonObject = JSONObject.parseObject(response);
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(status, HttpStatus.OK.status);
        JSONObject recordObj = jsonObject.getJSONObject("obj");
        int total = recordObj.getInteger("total");
        int pages = recordObj.getInteger("pages");
        int current = recordObj.getInteger("current");
        String text = "第" + current + "/" + pages + "页，共" + total + "条记录";
        System.out.println(text);

        JSONArray jsonArray = recordObj.getJSONArray("records");
        for (int i=0;i<jsonArray.size();i++){
            JSONObject subUserJSON = jsonArray.getJSONObject(i);
            System.out.println(subUserJSON.getString("subUserName"));
        }
    }
}
