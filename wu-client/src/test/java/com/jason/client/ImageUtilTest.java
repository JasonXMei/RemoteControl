package com.jason.client;

import com.jason.client.util.ImageUtils;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtilTest {

    @Test
    public void testSameImage() throws AWTException, InterruptedException {
        Robot robot = new Robot();
        // 截取整个屏幕
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle rec = new Rectangle(dimension);
        BufferedImage image = robot.createScreenCapture(rec);
        byte before[] = ImageUtils.compressedImageAndGetByteArray(image,50/100.0f);

        //Thread.sleep(3000);
        BufferedImage image1 = robot.createScreenCapture(rec);
        byte now[] = ImageUtils.compressedImageAndGetByteArray(image1,50/100.0f);
        System.out.println(ImageUtils.isDifferent(now, before));
    }
}
