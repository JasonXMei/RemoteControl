package com.jason.client.netty;

import com.jason.client.controller.LoginController;
import com.jason.client.util.Constants;
import com.jason.client.util.ImageUtils;
import com.jason.client.util.NettyUtil;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageThread implements Runnable{

    @Override
    public void run() {
        try {
            Robot robot = new Robot();
            // 截取整个屏幕
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle rec = new Rectangle(dimension);
            byte[] priviousImgBytes = null;
            int count = 0;
            while (true){
                if(CIMClientHandle.controlUserId != 0){
                    BufferedImage image = robot.createScreenCapture(rec);
                    byte nowImageBytes[] = ImageUtils.compressedImageAndGetByteArray(image,35/100.0f);
                    if(ImageUtils.isDifferent(nowImageBytes, priviousImgBytes) || count >= 3){
                        priviousImgBytes = nowImageBytes;
                        NettyUtil.sendGoogleProtocolMsg(Constants.MSG_IMG, Integer.valueOf(LoginController.userId), CIMClientHandle.controlUserId, nowImageBytes, null, null,(NioSocketChannel)CIMClient.channel);
                        count = 0;
                    }else{
                        count++;
                    }
                }else{
                    priviousImgBytes = null;
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
