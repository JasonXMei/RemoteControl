package com.jason.client.netty;

import com.jason.client.controller.LoginController;
import com.jason.client.util.ByteObjConverter;
import com.jason.client.util.Constants;
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
            while (true){
                if(CIMClientHandle.controlUserId != 0){
                    BufferedImage image = robot.createScreenCapture(rec);;
                    byte imageBytes[] = ByteObjConverter.getImageBytes(image);
                    NettyUtil.sendGoogleProtocolMsg(Constants.MSG_IMG, Integer.valueOf(LoginController.userId), CIMClientHandle.controlUserId, imageBytes, null, null,(NioSocketChannel)CIMClient.channel);
                }
                Thread.sleep(50);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
