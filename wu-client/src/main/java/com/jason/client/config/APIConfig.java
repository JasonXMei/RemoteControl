package com.jason.client.config;

public class APIConfig {

    public static String ipAddr = "www.zhlm.top";
    //public static String ipAddr = "192.168.1.27";
    //public static String ipAddr = "192.168.100.142";

    private static String urlPrefix = "http://" + ipAddr + ":8082/";

    public static String loginUrl = urlPrefix + "user/login?userName=%s&password=%s";

    public static String orderListUrl = urlPrefix + "client/task/orderList";

    public static String updateOrderUrl = urlPrefix + "client/task/handle/%s/%s/";

    public static String getUserStatusUrl = urlPrefix + "user/status/%s/%s/client";

}
