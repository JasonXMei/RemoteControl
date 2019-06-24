package com.jason.web.netty;

import com.jason.common.enums.ConnectStatusEnum;
import com.jason.common.po.User;
import com.jason.web.handler.SocketHandler;
import com.jason.web.mapper.SubUserMapper;
import com.jason.web.mapper.UserMapper;
import com.jason.web.protocol.WUProto;
import com.jason.web.util.Constants;
import com.jason.web.util.NettyAttrUtil;
import com.jason.web.util.NettyUtil;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@ChannelHandler.Sharable
@Component
@Slf4j
public class CIMServerHandle extends SimpleChannelInboundHandler<WUProto.WUProtocol> {

    //ControlWindow controlWindow = new ControlWindow();
    @Autowired
    private SubUserMapper subUserMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     *
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        userOffLine(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {

                log.info("定时检测客户端是否存活");

                long heartBeatTime = 30 * 1000;
                Long lastReadTime = NettyAttrUtil.getReaderTime(ctx.channel());
                long now = System.currentTimeMillis();
                if (lastReadTime != null && now - lastReadTime > heartBeatTime){
                    log.info("心跳超时[{}]ms，需要关闭连接!", now - lastReadTime);
                    userOffLine(ctx);
                    ctx.channel().close();
                }
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    /**
     * 用户下线
     */
    private void userOffLine(ChannelHandlerContext ctx) throws IOException {
        NioSocketChannel nioSocketChannel = (NioSocketChannel) ctx.channel();
        int userId = SocketHandler.removeClient(nioSocketChannel);
        if(userId != 0){
            log.info("channelInactive客户端[{}]下线", userId);
            User user = new User();
            user.setId(userId);
            user.setConnectStatusClient(ConnectStatusEnum.DisConnected);
            userMapper.updateById(user);
        }else{
            userId = SocketHandler.removeUse(nioSocketChannel);
            if(userId != 0){
                log.info("channelInactive使用端[{}]下线", userId);
                User user = new User();
                user.setId(userId);
                user.setConnectStatusUse(ConnectStatusEnum.DisConnected);
                userMapper.updateById(user);
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WUProto.WUProtocol msg) throws Exception {
        log.info("收到msg={}", msg.getSendUserId() + ":" + msg.getReceiveUserId() + ":" + msg.getMsgType());

        //心跳更新时间
        if (msg.getMsgType() == Constants.PING){
            NettyAttrUtil.updateReaderTime(ctx.channel(), System.currentTimeMillis());
            //向客户端响应 pong 消息
            //NettyUtil.sendGoogleProtocolMsg(Constants.PONG, 0, msg.getSendUserId(), null, null, null, (NioSocketChannel) ctx);
            return;
        }

        if (msg.getMsgType() == Constants.LOGIN_USE) {
            NettyAttrUtil.updateReaderTime(ctx.channel(), System.currentTimeMillis());
            int sendUserId = msg.getSendUserId();
            User user = userMapper.selectById(sendUserId);
            if(user != null && user.getConnectStatusUse().getStatus() == ConnectStatusEnum.DisConnected.getStatus()){
                SocketHandler.putUse(sendUserId, (NioSocketChannel) ctx.channel());
                log.info("使用端[{}]上线", msg.toString());
                user.setConnectStatusUse(ConnectStatusEnum.ToBeConnect);
                userMapper.updateById(user);
            }
            return;
        }

        if (msg.getMsgType() == Constants.LOGIN_CLIENT) {
            NettyAttrUtil.updateReaderTime(ctx.channel(), System.currentTimeMillis());
            int sendUserId = msg.getSendUserId();
            User user = userMapper.selectById(sendUserId);
            if(user != null && user.getConnectStatusClient().getStatus() == ConnectStatusEnum.DisConnected.getStatus()){
                SocketHandler.putClient(sendUserId, (NioSocketChannel) ctx.channel());
                log.info("客户端[{}]上线", msg.toString());
                user.setConnectStatusClient(ConnectStatusEnum.ToBeConnect);
                userMapper.updateById(user);
            }
            return;
        }

        if (msg.getMsgType() == Constants.LOGOUT_USE) {
            int sendUserId = msg.getSendUserId();
            SocketHandler.removeUse(sendUserId);
            User user = new User();
            user.setId(sendUserId);
            user.setConnectStatusUse(ConnectStatusEnum.DisConnected);
            userMapper.updateById(user);
            log.info("使用端[{}]下线", sendUserId);
            return;
        }

        if (msg.getMsgType() == Constants.LOGOUT_CLIENT) {
            int sendUserId = msg.getSendUserId();
            SocketHandler.removeClient(sendUserId);
            User user = new User();
            user.setId(sendUserId);
            user.setConnectStatusClient(ConnectStatusEnum.DisConnected);
            userMapper.updateById(user);
            log.info("客户端[{}]下线", sendUserId);
            return;
        }

        if (msg.getMsgType() == Constants.MSG_CONTROL){
            int receiveUserId = msg.getReceiveUserId();
            User user = userMapper.selectById(receiveUserId);
            if(user.getConnectStatusClient().getStatus() == ConnectStatusEnum.DisConnected.getStatus()){
                NettyUtil.sendGoogleProtocolMsg(Constants.MSG_ERROR, 0, msg.getSendUserId(), null, null, "客户端已下线！", (NioSocketChannel) ctx.channel());
                return;
            }

            if(user.getConnectStatusClient().getStatus() == ConnectStatusEnum.ToBeConnect.getStatus()){
                if(SocketHandler.getClient(receiveUserId) != null){
                    user.setConnectStatusClient(ConnectStatusEnum.Connected);
                    NettyUtil.sendGoogleProtocolMsg(Constants.MSG_CONTROL, msg.getSendUserId(), msg.getReceiveUserId(), null, null, null);
                }else{
                    user.setConnectStatusClient(ConnectStatusEnum.DisConnected);
                    NettyUtil.sendGoogleProtocolMsg(Constants.MSG_ERROR, 0, msg.getSendUserId(), null, null, "客户端已下线！", (NioSocketChannel) ctx.channel());
                }
                userMapper.updateById(user);
            }else{
                NettyUtil.sendGoogleProtocolMsg(Constants.MSG_ERROR, 0, msg.getSendUserId(), null, null, "客户端已被连接,请稍候重试！", (NioSocketChannel) ctx.channel());
            }
            return;
        }

        if (msg.getMsgType() == Constants.MSG_DIS_CONTROL){
            int receiveUserId = msg.getReceiveUserId();
            User user = userMapper.selectById(receiveUserId);
            if(user != null){
                if(SocketHandler.getClient(receiveUserId) != null){
                    user.setConnectStatusClient(ConnectStatusEnum.ToBeConnect);
                    NettyUtil.sendGoogleProtocolMsg(Constants.MSG_DIS_CONTROL, msg.getSendUserId(), msg.getReceiveUserId(), null, null, null);
                }else{
                    user.setConnectStatusClient(ConnectStatusEnum.DisConnected);
                }
                userMapper.updateById(user);
            }
            return;
        }

        if (msg.getMsgType() == Constants.MSG_IMG){
            if(SocketHandler.getUse(msg.getReceiveUserId()) != null){
                NettyUtil.sendGoogleProtocolMsg(Constants.MSG_IMG, msg.getSendUserId(), msg.getReceiveUserId(), msg.getScreenImg().toByteArray(), null, null);
            }else{
                User receiveUser = new User();
                receiveUser.setId(msg.getReceiveUserId());
                receiveUser.setConnectStatusUse(ConnectStatusEnum.DisConnected);
                userMapper.updateById(receiveUser);

                User sendUser = new User();
                sendUser.setId(msg.getSendUserId());
                sendUser.setConnectStatusClient(ConnectStatusEnum.ToBeConnect);
                userMapper.updateById(sendUser);
                NettyUtil.sendGoogleProtocolMsg(Constants.MSG_DIS_CONTROL, msg.getReceiveUserId(), msg.getSendUserId(), null, null, null);
            }
            return;
        }

        if (msg.getMsgType() == Constants.MSG_EVENT){
            if(SocketHandler.getClient(msg.getReceiveUserId()) != null){
                NettyUtil.sendGoogleProtocolMsg(Constants.MSG_EVENT, msg.getSendUserId(), msg.getReceiveUserId(), null, msg.getUserEvent().toByteArray(), null);
            }
            return;
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        /*if (CIMException.isResetByPeer(cause.getMessage())) {
            return;
        }*/
        log.error(cause.getMessage(), cause);
    }

}
