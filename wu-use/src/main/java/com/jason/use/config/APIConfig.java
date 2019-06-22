package com.jason.use.config;

public class APIConfig {

    //public static String ipAddr = "www.zhlm.top";
    public static String ipAddr = "192.168.1.27";
    //public static String ipAddr = "192.168.100.142";

    private static String urlPrefix = "http://" + ipAddr + ":8082/";

    public static String loginUrl = urlPrefix + "user/login?userName=%s&password=%s";

    public static String subUserListUrl = urlPrefix + "/client/subUser/list";

    public static String tagListUrl = urlPrefix + "/tag/list/client";

    public static String userInfoUrl = urlPrefix + "user/info/%s/%s/client";

    public static String submitOrderUrl = urlPrefix + "client/task/saveReplaceOrder";

    public static String submitTagUrl = urlPrefix + "client/task/saveTag";

    public static String referrerUserLink = urlPrefix + "user/info/0?referrerUserInviteCode=";

    public static String backEndLink = urlPrefix + "user/token/%s/";

    public static String getUserStatusUrl = urlPrefix + "user/status/%s/%s/client";

    public static String orderCountUrl = urlPrefix + "client/task/orderCount/%s/%s";

    public static String userOrderTimesUrl = urlPrefix + "client/subUser/getUserOrderTimes/%s";

}
