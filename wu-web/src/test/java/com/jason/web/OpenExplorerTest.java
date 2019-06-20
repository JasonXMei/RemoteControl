package com.jason.web;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

public class OpenExplorerTest {

    @Test
    public void openSystemDefaultExplore(){
        try {
            // 创建一个URI实例
            java.net.URI uri = java.net.URI.create("http://localhost:8082/user/token/eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjExNDc2NjEsInVzZXJJZCI6MX0.E74XGnzovKNose-57eG-vynfG2Orj0usttBPk-9I5co/");
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

    @Test
    public void testTimstamp() throws ParseException {
        String currentTime = DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(new Date());
        System.out.println(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.parse(currentTime).getTime());
    }
}
