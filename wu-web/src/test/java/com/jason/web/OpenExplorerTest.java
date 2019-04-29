package com.jason.web;

import org.junit.Test;

public class OpenExplorerTest {

    @Test
    public void openSystemDefaultExplore(){
        try {
            // 创建一个URI实例
            java.net.URI uri = java.net.URI.create("http://localhost:8082/user/token/eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTY1ODE3MjYsInVzZXJJZCI6MX0.utjqWUR4VeBRqJ7xhyYRDKs4PC12hg5YhR3qwp3uCho/");
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
