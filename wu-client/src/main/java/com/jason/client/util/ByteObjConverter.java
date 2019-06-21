package com.jason.client.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import net.coobird.thumbnailator.Thumbnails;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ByteObjConverter {

    public static Object byteToObject(byte[] bytes) {
        Object obj = null;
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = null;
        try {
            oi = new ObjectInputStream(bi);
            obj = oi.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bi.close();
                oi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public static byte[] objectToByte(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = null;
        try {
            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bo.close();
                oo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    public static byte[] getImageBytes(BufferedImage image){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 压缩器压缩，先拿到存放到byte输出流中
        JPEGImageEncoder jpegd = JPEGCodec.createJPEGEncoder(baos);
        try {
            // 将iamge压缩
            jpegd.encode(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 转换成byte数组
        return baos.toByteArray();
    }
    public static byte[] compressedImageAndGetByteArray(BufferedImage image, float quality) {

        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Thumbnails.of(image).scale(1f).outputFormat("jpg").outputQuality(quality).toOutputStream(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    /*public static void main(String[] args) throws AWTException {
        //获取屏幕分辨率
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize ();
        Robot robot = new Robot();
        //以屏幕的尺寸创建个矩形
        Rectangle screenRect = new Rectangle(d);
        //截图（截取整个屏幕图片）
        BufferedImage bufferedImage = robot.createScreenCapture(screenRect);

        //System.out.println(compressedImageAndGetByteArray(bufferedImage, 50/100.0f).length);
        System.out.println(getImageBytes(bufferedImage).length);
    }*/

}
