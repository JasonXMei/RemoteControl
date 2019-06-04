package com.jason.use.config;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class SingleOKHttpClient {
    private static OkHttpClient okHttpClient = null;
    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }
    private SingleOKHttpClient(){
    }

    public static OkHttpClient getOkHttpClientInstance(){
        return okHttpClient;
    }
}
