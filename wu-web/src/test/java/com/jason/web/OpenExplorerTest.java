package com.jason.web;

import org.junit.Test;

public class OpenExplorerTest {

    @Test
    public void openSystemDefaultExplore(){
        try {
            // 创建一个URI实例
            java.net.URI uri = java.net.URI.create("http://localhost:8082/user/token/eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTYzMjMyNjIsInVzZXJJZCI6MX0.0CwL9xMJrCW74k1PlJy8cherPzH4RkNuy2Z3VULsUxc/");
            // 获取当前系统桌面扩展
            java.awt.Desktop dp = java.awt.Desktop.getDesktop();
            // 判断系统桌面是否支持要执行的功能
            if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
                // 获取系统默认浏览器打开链接
                dp.browse(uri);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
