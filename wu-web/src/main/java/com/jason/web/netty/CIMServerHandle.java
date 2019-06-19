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
        log.info("收到msg={}", msg.getSendUserId() + ":" + msg.getReceiveUserId() + ":" + msg.getMsgType());

        if (msg.getMsgType() == Constants.LOGIN_USE) {
            int sendUserId = msg.getSendUserId();
            User user = userMapper.selectById(sendUserId);
            if(user != null && user.getConnectStatus().getStatus() == ConnectStatusEnum.DisConnected.getStatus()){
                SocketHandler.putUse(sendUserId, (NioSocketChannel) ctx.channel());
                log.info("使用端[{}]上线", msg.toString());
                user.setConnectStatus(ConnectStatusEnum.ToBeConnect);
                userMapper.updateById(user);
            }
            return;
        }

        if (msg.getMsgType() == Constants.LOGIN_CLIENT) {
            int sendUserId = msg.getSendUserId();
            SubUser subUser = subUserMapper.selectById(sendUserId);
            if(subUser != null && subUser.getConnectStatus().getStatus() == ConnectStatusEnum.DisConnected.getStatus()){
                SocketHandler.putClient(sendUserId, (NioSocketChannel) ctx.channel());
                log.info("客户端[{}]上线", msg.toString());
                subUser.setConnectStatus(ConnectStatusEnum.ToBeConnect);
                subUserMapper.updateById(subUser);
            }
            return;
        }

        //心跳更新时间
        /*if (msg.getMsgType() == Constants.PING){
            //NettyUtil.updateReaderTime(ctx.channel(),System.currentTimeMillis());
            //向客户端响应 pong 消息
            NettyUtil.sendGoogleProtocolMsg(Constants.PONG, 0, 1, null, null);
        }*/

        if (msg.getMsgType() == Constants.MSG_CONTROL){
            int receiveUserId = msg.getReceiveUserId();
            SubUser subUser = subUserMapper.selectById(receiveUserId);
            if(subUser.getConnectStatus().getStatus() == ConnectStatusEnum.DisConnected.getStatus()){
                NettyUtil.sendGoogleProtocolMsg(Constants.MSG_ERROR, 0, msg.getSendUserId(), null, null, "客户端已下线！", (NioSocketChannel) ctx.channel());
                return;
            }

            if(subUser.getConnectStatus().getStatus() == ConnectStatusEnum.ToBeConnect.getStatus()){
                if(SocketHandler.getClient(receiveUserId) != null){
                    subUser.setConnectStatus(ConnectStatusEnum.Connected);
                    NettyUtil.sendGoogleProtocolMsg(Constants.MSG_CONTROL, msg.getSendUserId(), msg.getReceiveUserId(), null, null, null);
                }else{
                    subUser.setConnectStatus(ConnectStatusEnum.DisConnected);
                    NettyUtil.sendGoogleProtocolMsg(Constants.MSG_ERROR, 0, msg.getSendUserId(), null, null, "客户端已下线！", (NioSocketChannel) ctx.channel());
                }
                subUserMapper.updateById(subUser);
            }else{
                NettyUtil.sendGoogleProtocolMsg(Constants.MSG_ERROR, 0, msg.getSendUserId(), null, null, "客户端已被连接,请稍候重试！", (NioSocketChannel) ctx.channel());
            }
            return;
        }

        if (msg.getMsgType() == Constants.MSG_DIS_CONTROL){
            int receiveUserId = msg.getReceiveUserId();
            SubUser subUser = subUserMapper.selectById(receiveUserId);
            if(subUser != null){
                if(SocketHandler.getClient(receiveUserId) != null){
                    subUser.setConnectStatus(ConnectStatusEnum.ToBeConnect);
                    NettyUtil.sendGoogleProtocolMsg(Constants.MSG_DIS_CONTROL, msg.getSendUserId(), msg.getReceiveUserId(), null, null, null);
                }else{
                    subUser.setConnectStatus(ConnectStatusEnum.DisConnected);
                }
                subUserMapper.updateById(subUser);
            }
            return;
        }

        if (msg.getMsgType() == Constants.MSG_IMG){
            if(SocketHandler.getUse(msg.getReceiveUserId()) != null){
                NettyUtil.sendGoogleProtocolMsg(Constants.MSG_IMG, msg.getSendUserId(), msg.getReceiveUserId(), msg.getScreenImg().toByteArray(), null, null);
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
