package com.jason.client.util;

import com.google.protobuf.ByteString;
import com.jason.client.netty.CIMClient;
import com.jason.client.protocol.WUProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class NettyUtil {

    /**
     * 发送 Google Protocol 编解码字符串
     */
    public static void sendGoogleProtocolMsg(int msgType, int sendUserId, int receiveUserId, byte[] screenImg, byte[] userEvent, String message, NioSocketChannel nioSocketChannel) {
        WUProto.WUProtocol protocol = null;
        switch (msgType){
            case Constants.PING:
            case Constants.LOGIN_CLIENT:
            case Constants.LOGOUT_CLIENT:
                protocol = WUProto.WUProtocol.newBuilder()
                        .setMsgType(msgType)
                        .setSendUserId(sendUserId)
                        .build();
                break;
            case Constants.MSG_ERROR:
                protocol = WUProto.WUProtocol.newBuilder()
                        .setMsgType(msgType)
                        .setSendUserId(sendUserId)
                        .setMessage(message)
                        .build();
                break;
            case Constants.MSG_IMG:
                protocol = WUProto.WUProtocol.newBuilder()
                        .setMsgType(msgType)
                        .setSendUserId(sendUserId)
                        .setReceiveUserId(receiveUserId)
                        .setScreenImg(ByteString.copyFrom(screenImg))
                        .build();
                //System.out.println("send msg:" + sendUserId + ":" + receiveUserId + ":" + msgType + ",data size:" + screenImg.length);
        }

        nioSocketChannel.writeAndFlush(protocol);
        /*nioSocketChannel.writeAndFlush(protocol).addListeners((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                System.out.println("IO error,close Channel");
                future.channel().close();
            }else{
                System.out.println("发送 Google Protocol Msg成功!");
            }
        });*/
    }
}
