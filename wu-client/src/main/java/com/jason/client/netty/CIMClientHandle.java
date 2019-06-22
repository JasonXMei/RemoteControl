package com.jason.client.netty;

import com.alibaba.fastjson.JSONObject;
import com.jason.client.controller.LoginController;
import com.jason.client.protocol.WUProto;
import com.jason.client.util.ByteObjConverter;
import com.jason.client.util.Constants;
import com.jason.client.util.NettyUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

@ChannelHandler.Sharable
public class CIMClientHandle extends SimpleChannelInboundHandler<WUProto.WUProtocol> {

    Robot robot;

    public static volatile int controlUserId = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        /*if (evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt ;

            //LOGGER.info("定时检测服务端是否存活");
            if (idleStateEvent.state() == IdleState.WRITER_IDLE){
                NioSocketChannel nioSocketChannel = (NioSocketChannel) ctx.channel();
                //NettyUtil.sendGoogleProtocolMsg(Constants.CommandType.PING, 1, 0, null, null, nioSocketChannel);
            }
        }
        super.userEventTriggered(ctx, evt);*/
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //客户端和服务端建立连接时调用
        //LOGGER.info("cim netty connect success!");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //NettyUtil.sendGoogleProtocolMsg(Constants.LOGOUT_CLIENT, Integer.valueOf(LoginController.userId), 0, null, null, null, (NioSocketChannel) ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,  WUProto.WUProtocol msg) throws Exception {
        if(robot == null){
            robot = new Robot();
        }
        System.out.println("收到服务端消息:" +  msg.toString());
        if (msg.getMsgType() == Constants.MSG_CONTROL) {
            controlUserId = msg.getSendUserId();
            NettyUtil.sendGoogleProtocolMsg(Constants.MSG_IMG, msg.getReceiveUserId(), msg.getSendUserId(), getImgBytes(), null, null,(NioSocketChannel)ctx.channel());
            return;
        }

        if (msg.getMsgType() == Constants.MSG_DIS_CONTROL) {
            controlUserId = 0;
            return;
        }

        if (msg.getMsgType() == Constants.UPDATE_JWT) {
            LoginController.jwt = msg.getMessage();
            return;
        }

        if (msg.getMsgType() == Constants.MSG_EVENT) {
            JSONObject jsonObject = (JSONObject) ByteObjConverter.byteToObject(msg.getUserEvent().toByteArray());
            handleEvents(robot, jsonObject);// 处理事件
            Thread.sleep(200);
            NettyUtil.sendGoogleProtocolMsg(Constants.MSG_IMG, msg.getReceiveUserId(), msg.getSendUserId(), getImgBytes(), null, null,(NioSocketChannel)ctx.channel());
            return;
        }
    }

    private byte[] getImgBytes(){
        // 截取整个屏幕
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle rec = new Rectangle(dimension);
        BufferedImage image = robot.createScreenCapture(rec);;
        byte imageBytes[] = ByteObjConverter.getImageBytes(image);

        return imageBytes;
    }

    /**
     * 事件处理，用来判断事件类型，并用robot类执行
     */
    public static void  handleEvents(Robot action, JSONObject event) {
        int mousebuttonmask = -100; // 鼠标按键

        switch (event.getInteger("eventId")) {
            case MouseEvent.MOUSE_MOVED: // 鼠标移动
            case MouseEvent.MOUSE_DRAGGED: // 鼠标拖拽
                action.mouseMove(event.getInteger("moveX"), event.getInteger("moveY"));
                break;
            case MouseEvent.MOUSE_PRESSED: // 鼠标键按下
                action.mouseMove(event.getInteger("moveX"), event.getInteger("moveY"));
                mousebuttonmask = getMouseClick(event.getInteger("btn"));
                if (mousebuttonmask != -100)
                    action.mousePress(mousebuttonmask);
                break;
            case MouseEvent.MOUSE_RELEASED: // 鼠标键松开
                action.mouseMove(event.getInteger("moveX"), event.getInteger("moveY"));
                mousebuttonmask = getMouseClick(event.getInteger("btn"));// 取得鼠标按键
                if (mousebuttonmask != -100)
                    action.mouseRelease(mousebuttonmask);
                break;
            case MouseEvent.MOUSE_WHEEL: // 鼠标滚动
                action.mouseWheel(event.getInteger("wheelRotation"));
                break;
            case KeyEvent.KEY_PRESSED: // 按键
                action.keyPress(event.getInteger("keyCode"));
                break;
            case KeyEvent.KEY_RELEASED: // 松键
                action.keyRelease(event.getInteger("keyCode"));
                break;
            default:
                break;
        }
    }

    private static int getMouseClick(int button) { // 取得鼠标按键
        if (button == MouseEvent.BUTTON1) // 左键 ,中间键为BUTTON2
            return InputEvent.BUTTON1_MASK;
        if (button == MouseEvent.BUTTON3) // 右键
            return InputEvent.BUTTON3_MASK;
        return -100;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //异常时断开连接
        cause.printStackTrace() ;
        ctx.close() ;
    }

}
