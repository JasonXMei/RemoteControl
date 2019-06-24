package com.jason.client;

import org.junit.Test;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ImageUtilTest {

    @Test
    public void testSameImage() throws AWTException, InterruptedException {
        Robot robot = new Robot();
        Thread.sleep(65000);
        robot.mouseMove(1908,190);
        robot.mousePress(16);
        robot.mouseRelease(16);
        // 截取整个屏幕
       /* Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle rec = new Rectangle(dimension);
        BufferedImage image = robot.createScreenCapture(rec);
        byte before[] = ImageUtils.compressedImageAndGetByteArray(image,50/100.0f);

        //Thread.sleep(3000);
        BufferedImage image1 = robot.createScreenCapture(rec);
        byte now[] = ImageUtils.compressedImageAndGetByteArray(image1,50/100.0f);
        System.out.println(ImageUtils.isDifferent(now, before));*/
    }

    @Test
    public void testComand() throws  Exception{
        //上传之后的视频文件名为test.tmpmedia
        String command = "powercfg -h off";

        Process p = Runtime.getRuntime().exec(command);

        //读取命令的输出信息
        InputStream is = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        p.waitFor();
        if (p.exitValue() != 0) {
            //说明命令执行失败
            //可以进入到错误处理步骤中
            System.out.println("failed");
        }

        //打印输出信息
        String s = null;
        while ((s = reader.readLine()) != null) {
            System.out.println(s);
        }
    }

}
