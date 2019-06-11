package com.jason.use.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

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
}
