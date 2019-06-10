package com.jason.client.config;

public class APIConfig {

    private static String urlPrefix = "http://localhost:8082/";

    public static String loginUrl = urlPrefix + "user/login?userName=%s&password=%s";

    public static String orderListUrl = urlPrefix + "client/task/orderList";

    public static String updateOrderUrl = urlPrefix + "client/task/handle/%s/%s/";

    public static String userInfoUrl = urlPrefix + "user/info/%s/%s/client";

}
