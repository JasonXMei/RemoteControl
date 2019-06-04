package com.jason.use.config;

public class APIConfig {

    private static String urlPrefix = "http://localhost:8082/";

    public static String loginUrl = urlPrefix + "user/login?userName=%s&password=%s";

    public static String subUserListUrl = urlPrefix + "/client/subUser/list";

    public static String tagListUrl = urlPrefix + "/tag/list/client";

    public static String userInfoUrl = urlPrefix + "user/info/%s/client";

    public static String submitOrderUrl = urlPrefix + "client/task/saveReplaceOrder";

    public static String submitTagUrl = urlPrefix + "client/task/saveTag";

}
