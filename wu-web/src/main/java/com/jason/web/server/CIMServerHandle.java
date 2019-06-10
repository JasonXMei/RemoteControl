package com.jason.web.server;

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
            log.info("子用户[{}]下线", userId);
            SubUser subUser = new SubUser();
            subUser.setId(userId);
            subUser.setConnectStatus(ConnectStatusEnum.DisConnected);
            subUserMapper.updateById(subUser);
        }else{
            userId = SocketHandler.removeUse(nioSocketChannel);
            if(userId != 0){
                log.info("用户[{}]下线", userId);
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

        if (msg.getMsgType() == Constants.LOGIN) {
            //保存客户端与 Channel 之间的关系
            SocketHandler.putUse(msg.getSendUserId(), (NioSocketChannel) ctx.channel());
            //SocketHandler.saveSession(msg.getRequestId(), msg.getReqMsg());
            log.info("客户端[{}]上线成功", msg.toString());

            NettyUtil.sendGoogleProtocolMsg(Constants.MSG_CONTROL, msg.getReceiveUserId(), msg.getSendUserId(), null, null);
        }

        //心跳更新时间
        if (msg.getMsgType() == Constants.PING){
            //NettyUtil.updateReaderTime(ctx.channel(),System.currentTimeMillis());
            //向客户端响应 pong 消息
            NettyUtil.sendGoogleProtocolMsg(Constants.PONG, 0, 1, null, null);
        }

        if (msg.getMsgType() == Constants.MSG_IMG){
            //NettyUtil.sendGoogleProtocolMsg(Constants.PONG, 0, 1, null, null, ctx);
            //controlWindow.repainImage(msg.getScreenImg().toByteArray());
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
