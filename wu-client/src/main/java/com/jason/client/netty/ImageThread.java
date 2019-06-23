package com.jason.client.netty;

import com.jason.client.controller.LoginController;
import com.jason.client.util.Constants;
import com.jason.client.util.ImageUtils;
import com.jason.client.util.NettyUtil;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ImageThread implements Runnable{

    @Override
    public void run() {
        try {
            byte[] priviousImgBytes = null;
            while (true){
                if(CIMClientHandle.controlUserId != 0){
                    byte nowImageBytes[] = ImageUtils.compressedImageAndGetByteArray(30/100.0f);
                    if(ImageUtils.isDifferent(nowImageBytes, priviousImgBytes)){
                        priviousImgBytes = nowImageBytes;
                        NettyUtil.sendGoogleProtocolMsg(Constants.MSG_IMG, Integer.valueOf(LoginController.userId), CIMClientHandle.controlUserId, nowImageBytes, null, null,(NioSocketChannel)CIMClient.channel);
                    }
                }else{
                    priviousImgBytes = null;
                }
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
