package com.jason.web.util;

import com.google.protobuf.ByteString;
import com.jason.web.handler.SocketHandler;
import com.jason.web.protocol.WUProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyUtil {

    private static final AttributeKey<String> ATTR_KEY_READER_TIME = AttributeKey.valueOf("readerTime");
    private final static Logger LOGGER = LoggerFactory.getLogger(NettyUtil.class);

    public static void updateReaderTime(Channel channel, Long time) {
        channel.attr(ATTR_KEY_READER_TIME).set(time.toString());
    }

    public static Long getReaderTime(Channel channel) {
        String value = getAttribute(channel, ATTR_KEY_READER_TIME);

        if (value != null) {
            return Long.valueOf(value);
        }
        return null;
    }


    private static String getAttribute(Channel channel, AttributeKey<String> key) {
        Attribute<String> attr = channel.attr(key);
        return attr.get();
    }

    /**
     * 发送 Google Protocol 编解码字符串
     */
    public static void sendGoogleProtocolMsg(int msgType, int sendUserId, int receiveUserId, byte[] screenImg, byte[] userEvent, String message, NioSocketChannel nioSocketChannel) {
        WUProto.WUProtocol protocol = null;
        switch (msgType){
            /*case Constants.PING:
            case Constants.PONG:
            case Constants.LOGIN:
            default:*/
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
                break;
            case Constants.MSG_EVENT:
                protocol = WUProto.WUProtocol.newBuilder()
                        .setMsgType(msgType)
                        .setSendUserId(sendUserId)
                        .setReceiveUserId(receiveUserId)
                        .setScreenImg(ByteString.copyFrom(userEvent))
                        .build();
        }

        nioSocketChannel.writeAndFlush(protocol).addListeners((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                LOGGER.error("IO error,close Channel");
                future.channel().close();
            }else{
                LOGGER.info("发送 Google Protocol Msg成功!");
            }
        });
    }

    /**
     * 发送 Google Protocol 编解码字符串
     */
    public static void sendGoogleProtocolMsg(int msgType, int sendUserId, int receiveUserId, byte[] screenImg, byte[] userEvent, String message) {
        WUProto.WUProtocol protocol = null;
        NioSocketChannel nioSocketChannel = null;
        switch (msgType){
            case Constants.MSG_CONTROL:
                nioSocketChannel = SocketHandler.getClient(receiveUserId);
                if(nioSocketChannel == null){
                    nioSocketChannel = SocketHandler.getUse(sendUserId);
                    protocol = WUProto.WUProtocol.newBuilder()
                            .setMsgType(Constants.MSG_ERROR)
                            .setSendUserId(0)
                            .setReceiveUserId(sendUserId)
                            .setMessage("客户端已下线!")
                            .build();
                }else{
                    protocol = WUProto.WUProtocol.newBuilder()
                            .setMsgType(msgType)
                            .setSendUserId(sendUserId)
                            .setReceiveUserId(receiveUserId)
                            .build();
                }
                break;
            case Constants.MSG_IMG:
                nioSocketChannel = SocketHandler.getUse(receiveUserId);
                if(nioSocketChannel == null){
                   return;
                }else{
                    protocol = WUProto.WUProtocol.newBuilder()
                            .setMsgType(msgType)
                            .setSendUserId(sendUserId)
                            .setReceiveUserId(receiveUserId)
                            .setScreenImg(ByteString.copyFrom(screenImg))
                            .build();
                }
                break;
            case Constants.MSG_EVENT:
                nioSocketChannel = SocketHandler.getClient(receiveUserId);
                if(nioSocketChannel == null){
                    nioSocketChannel = SocketHandler.getUse(sendUserId);
                    protocol = WUProto.WUProtocol.newBuilder()
                            .setMsgType(Constants.MSG_ERROR)
                            .setSendUserId(0)
                            .setReceiveUserId(sendUserId)
                            .setMessage("客户端已下线!")
                            .build();
                }else{
                    protocol = WUProto.WUProtocol.newBuilder()
                            .setMsgType(msgType)
                            .setSendUserId(sendUserId)
                            .setReceiveUserId(receiveUserId)
                            .setUserEvent(ByteString.copyFrom(userEvent))
                            .build();
                }
        }

        nioSocketChannel.writeAndFlush(protocol).addListeners((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                LOGGER.error("IO error,close Channel");
                future.channel().close();
            }else{
                LOGGER.info("发送 Google Protocol Msg成功!");
            }
        });
    }

}
