package com.jason.use.util;

import com.jason.use.config.SingleOKHttpClient;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;

public class HttpUtil {

    private static OkHttpClient okHttpClient = SingleOKHttpClient.getOkHttpClientInstance();

    public static String httpGet(String url, String jwt) {
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + jwt)
                .get()//默认就是GET请求，可以不写
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String httpPost(HashMap<String, String> paramsMap, String url, String jwt) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            //追加表单信息
            builder.add(key, paramsMap.get(key));
        }
        RequestBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + jwt)
                .post(formBody)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static HashMap<String, String> generateParamMap(String... params){
        HashMap<String, String> paramsMap = new HashMap<>();
        if (params != null){
            for(String param: params){
                String[] paramArr = param.split(",");
                paramsMap.put(paramArr[0], paramArr[1]);
            }
        }
        return paramsMap;
    }
}
