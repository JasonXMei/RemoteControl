package com.jason.use.netty;

import com.jason.use.config.APIConfig;
import com.jason.use.controller.LoginController;
import com.jason.use.util.Constants;
import com.jason.use.util.NettyUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

public class CIMClient {

    private static EventLoopGroup group = new NioEventLoopGroup(0, new DefaultThreadFactory("cim-work"));
    public static SocketChannel channel;

    /**
     * 重试次数
     */
    private static int maxRetryCount = 5;
    private static int errorCount;
    private static int port = 8989;

    /**
     * 启动客户端
     */
    public static boolean start() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new CIMClientHandleInitializer());

        ChannelFuture future = null;
        try {
            future = bootstrap.connect(APIConfig.ipAddr, port).sync();

            if (future.isSuccess()) {
                System.out.println("启动 cim client 成功");
            }
            channel = (SocketChannel) future.channel();
            return true;
        } catch (InterruptedException e) {
            errorCount++;

            if (errorCount >= maxRetryCount) {
                System.out.println("链接失败次数达到上限[" + errorCount + "]次");
            }
            System.out.println("连接失败");
            start();
        }

        return false;
    }

    public static boolean reconnect(){
        if (channel != null && channel.isActive()) {
            return true;
        }
        return start();
    }

    /**
     * 关闭
     */
    public static boolean close(){
        if (channel != null){
            NettyUtil.sendGoogleProtocolMsg(Constants.LOGOUT_USE, Integer.valueOf(LoginController.userId), 0, null, null, null, (NioSocketChannel) channel);
            channel.close();
            group.shutdownGracefully().syncUninterruptibly();
            return true;
        }
        return false;
    }
}
