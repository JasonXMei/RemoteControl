package com.jason.server.kit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jason.server.config.AppConfiguration;
import com.jason.server.pojo.CIMUserInfo;
import com.jason.server.util.NettyAttrUtil;
import com.jason.server.util.SessionSocketHolder;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 */
@Service
public class ServerHeartBeatHandlerImpl implements HeartBeatHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerHeartBeatHandlerImpl.class);

    @Autowired
    private AppConfiguration appConfiguration ;

    @Override
    public void process(ChannelHandlerContext ctx) throws Exception {

        long heartBeatTime = appConfiguration.getHeartBeatTime() * 1000;

        Channel channel = ctx.channel(); 
        Long lastReadTime = NettyAttrUtil.getReaderTime(channel);
        long now = System.currentTimeMillis();
        if (lastReadTime != null && now - lastReadTime > heartBeatTime){
            CIMUserInfo userInfo = SessionSocketHolder.getUserId((NioSocketChannel)channel);
            if (userInfo != null){
                LOGGER.warn("客户端[{}]心跳超时[{}]ms，需要关闭连接!",userInfo.getUserName(),now - lastReadTime);
                userOffLine(userInfo, (NioSocketChannel) channel);
            }
            channel.close();
        }
    }
    
    private void userOffLine(CIMUserInfo userInfo, NioSocketChannel channel) {
        LOGGER.info("用户[{}]下线", userInfo.getUserName());
        SessionSocketHolder.removeSession(userInfo.getUserId());
        SessionSocketHolder.remove(channel);
    }
}
