package com.jason.client;

import org.junit.Test;

import java.awt.*;

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
}
