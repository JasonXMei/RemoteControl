package com.jason.server.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jason.server.constant.Constants;
import com.jason.server.exception.CIMException;
import com.jason.server.kit.HeartBeatHandler;
import com.jason.server.kit.ServerHeartBeatHandlerImpl;
import com.jason.server.pojo.CIMUserInfo;
import com.jason.server.protocal.CIMRequestProto;
import com.jason.server.util.NettyAttrUtil;
import com.jason.server.util.SessionSocketHolder;
import com.jason.server.util.SpringBeanFactory;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 */
@ChannelHandler.Sharable
public class CIMServerHandle extends SimpleChannelInboundHandler<CIMRequestProto.CIMReqProtocol> {

    private final static Logger LOGGER = LoggerFactory.getLogger(CIMServerHandle.class);

    /**
     * 取消绑定
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //可能出现业务判断离线后再次触发 channelInactive
        CIMUserInfo userInfo = SessionSocketHolder.getUserId((NioSocketChannel) ctx.channel());
        if (userInfo != null){
            LOGGER.warn("[{}]触发 channelInactive 掉线!",userInfo.getUserName());
            userOffLine(userInfo, (NioSocketChannel) ctx.channel());
            ctx.channel().close();
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {

                LOGGER.info("定时检测客户端端是否存活");

                HeartBeatHandler heartBeatHandler = SpringBeanFactory.getBean(ServerHeartBeatHandlerImpl.class) ;
                heartBeatHandler.process(ctx) ;
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    /**
     * 用户下线
     * @param userInfo
     * @param channel
     * @throws IOException
     */
    private void userOffLine(CIMUserInfo userInfo, NioSocketChannel channel) throws IOException {
        LOGGER.info("用户[{}]下线", userInfo.getUserName());
        SessionSocketHolder.remove(channel);
        SessionSocketHolder.removeSession(userInfo.getUserId());
    }

    @SuppressWarnings("unchecked")
	@Override
    protected void channelRead0(ChannelHandlerContext ctx, CIMRequestProto.CIMReqProtocol msg) throws Exception {
        LOGGER.info("收到msg={}", msg.toString());

        if (msg.getType() == Constants.CommandType.LOGIN) {
            //保存客户端与 Channel 之间的关系
            SessionSocketHolder.put(msg.getRequestId(), (NioSocketChannel) ctx.channel());
            SessionSocketHolder.saveSession(msg.getRequestId(), msg.getReqMsg());
            LOGGER.info("客户端[{}]上线成功", msg.getReqMsg());
        }

        //心跳更新时间
        if (msg.getType() == Constants.CommandType.PING){
            NettyAttrUtil.updateReaderTime(ctx.channel(), System.currentTimeMillis());
            
            //向客户端响应 pong 消息
            CIMRequestProto.CIMReqProtocol heartBeat = SpringBeanFactory.getBean("heartBeat",
                    CIMRequestProto.CIMReqProtocol.class);
            ctx.writeAndFlush(heartBeat).addListeners((ChannelFutureListener) future -> {
                if (!future.isSuccess()) {
                    LOGGER.error("IO error,close Channel");
                    future.channel().close();
                }
            }) ;
        }
        
        if (msg.getType() == Constants.CommandType.MSG) {
        	NioSocketChannel socketChannel = SessionSocketHolder.get(msg.getRequestId());

            if (null == socketChannel) {
                throw new NullPointerException("客户端[" + msg.getRequestId() + "]不在线！");
            }

            ChannelFuture future = socketChannel.writeAndFlush(msg);
            future.addListener((ChannelFutureListener) channelFuture ->
                    LOGGER.info("服务端手动发送 Google Protocol 成功={}", msg.toString()));
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (CIMException.isResetByPeer(cause.getMessage())) {
            return;
        }

        LOGGER.error(cause.getMessage(), cause);

    }

}
