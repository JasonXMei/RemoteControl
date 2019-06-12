package com.jason.web.netty;

import com.jason.common.enums.ConnectStatusEnum;
import com.jason.common.po.SubUser;
import com.jason.common.po.User;
import com.jason.web.handler.SocketHandler;
import com.jason.web.mapper.SubUserMapper;
import com.jason.web.mapper.UserMapper;
import com.jason.web.protocol.WUProto;
import com.jason.web.util.Constants;
import com.jason.web.util.NettyUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
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
    }

    /**
     * 用户下线
     */
    private void userOffLine(ChannelHandlerContext ctx) throws IOException {
        NioSocketChannel nioSocketChannel = (NioSocketChannel) ctx.channel();
        int userId = SocketHandler.removeClient(nioSocketChannel);
        if(userId != 0){
            log.info("客户端[{}]下线", userId);
            SubUser subUser = new SubUser();
            subUser.setId(userId);
            subUser.setConnectStatus(ConnectStatusEnum.DisConnected);
            subUserMapper.updateById(subUser);
        }else{
            userId = SocketHandler.removeUse(nioSocketChannel);
            if(userId != 0){
                log.info("使用端[{}]下线", userId);
                User user = new User();
                user.setId(userId);
                user.setConnectStatus(ConnectStatusEnum.DisConnected);
                userMapper.updateById(user);
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WUProto.WUProtocol msg) throws Exception {
        log.info("收到msg={}", msg.toString());

        if (msg.getMsgType() == Constants.LOGIN_USE) {
            int sendUserId = msg.getSendUserId();
            User user = userMapper.selectById(sendUserId);
            if(user != null && user.getConnectStatus().getStatus() == ConnectStatusEnum.DisConnected.getStatus()){
                SocketHandler.putUse(sendUserId, (NioSocketChannel) ctx.channel());
                log.info("使用端[{}]上线", msg.toString());
                user.setConnectStatus(ConnectStatusEnum.ToBeConnect);
                userMapper.updateById(user);
            }else{
                NettyUtil.sendGoogleProtocolMsg(Constants.MSG_ERROR, 0, sendUserId, null, null, "该用户已登陆！", (NioSocketChannel) ctx.channel());
            }
        }

        if (msg.getMsgType() == Constants.LOGIN_CLIENT) {
            int sendUserId = msg.getSendUserId();
            SubUser subUser = subUserMapper.selectById(sendUserId);
            if(subUser != null && subUser.getConnectStatus().getStatus() == ConnectStatusEnum.DisConnected.getStatus()){
                SocketHandler.putClient(sendUserId, (NioSocketChannel) ctx.channel());
                log.info("客户端[{}]上线", msg.toString());
                subUser.setConnectStatus(ConnectStatusEnum.ToBeConnect);
                subUserMapper.updateById(subUser);
            }else{
                NettyUtil.sendGoogleProtocolMsg(Constants.MSG_ERROR, 0, sendUserId, null, null, "该用户已登陆！", (NioSocketChannel) ctx.channel());
            }
        }

        //心跳更新时间
        /*if (msg.getMsgType() == Constants.PING){
            //NettyUtil.updateReaderTime(ctx.channel(),System.currentTimeMillis());
            //向客户端响应 pong 消息
            NettyUtil.sendGoogleProtocolMsg(Constants.PONG, 0, 1, null, null);
        }*/

        if (msg.getMsgType() == Constants.MSG_CONTROL){
            //NettyUtil.sendGoogleProtocolMsg(Constants.PONG, 0, 1, null, null, ctx);
            //controlWindow.repainImage(msg.getScreenImg().toByteArray());
            NettyUtil.sendGoogleProtocolMsg(Constants.MSG_IMG, msg.getSendUserId(), msg.getReceiveUserId(), null, null, null);
        }

        if (msg.getMsgType() == Constants.MSG_IMG){
            //NettyUtil.sendGoogleProtocolMsg(Constants.PONG, 0, 1, null, null, ctx);
            //controlWindow.repainImage(msg.getScreenImg().toByteArray());
            NettyUtil.sendGoogleProtocolMsg(Constants.MSG_IMG, msg.getSendUserId(), msg.getReceiveUserId(), msg.getScreenImg().toByteArray(), null, null);
        }

        if (msg.getMsgType() == Constants.MSG_EVENT){
            //NettyUtil.sendGoogleProtocolMsg(Constants.PONG, 0, 1, null, null, ctx);
            //controlWindow.repainImage(msg.getScreenImg().toByteArray());
            NettyUtil.sendGoogleProtocolMsg(Constants.MSG_EVENT, msg.getSendUserId(), msg.getReceiveUserId(), null, msg.getUserEvent().toByteArray(), null);
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
