package com.jason.client.netty;

import com.jason.client.protocol.WUProto;
import com.jason.client.util.ByteObjConverter;
import com.jason.client.util.Constants;
import com.jason.client.util.NettyUtil;
import com.sun.image.codec.jpeg.ImageFormatException;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@ChannelHandler.Sharable
public class CIMClientHandle extends SimpleChannelInboundHandler<WUProto.WUProtocol> {

    private ThreadPoolExecutor threadPoolExecutor ;

    private ScheduledExecutorService scheduledExecutorService ;

    Robot robot;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt ;

            //LOGGER.info("定时检测服务端是否存活");
            if (idleStateEvent.state() == IdleState.WRITER_IDLE){
                NioSocketChannel nioSocketChannel = (NioSocketChannel) ctx.channel();
                //NettyUtil.sendGoogleProtocolMsg(Constants.CommandType.PING, 1, 0, null, null, nioSocketChannel);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //客户端和服务端建立连接时调用
        //LOGGER.info("cim netty connect success!");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,  WUProto.WUProtocol msg) throws Exception {
        //心跳更新时间
        /*if (msg.getMsgType() == Constants.PONG){
            //LOGGER.info("收到服务端心跳！！！");
            //NettyUtil.updateReaderTime(ctx.channel(), System.currentTimeMillis());
        }*/
        if(robot == null){
            robot = new Robot();
        }
        System.out.println("收到服务端消息:" +  msg.toString());
        if (msg.getMsgType() == Constants.MSG_CONTROL) {
            NettyUtil.sendGoogleProtocolMsg(Constants.MSG_IMG, msg.getReceiveUserId(), msg.getSendUserId(), getImgBytes(), null, null,(NioSocketChannel)ctx.channel());
        }

        if (msg.getMsgType() == Constants.MSG_EVENT) {
            //回调消息
            //callBackMsg(msg.getResMsg());
            //LOGGER.info("收到服务端消息[{}]", msg.toString());
            try {
                Robot robot = new Robot();

                InputEvent event = (InputEvent)ByteObjConverter.byteToObject(msg.getUserEvent().toByteArray());
                handleEvents(robot, event);// 处理事件
                NettyUtil.sendGoogleProtocolMsg(Constants.MSG_IMG, msg.getReceiveUserId(), msg.getSendUserId(), getImgBytes(), null, null,(NioSocketChannel)ctx.channel());
            } catch (AWTException e) {
                e.printStackTrace();
            } catch (ImageFormatException e) {
                e.printStackTrace();
            }
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
    public static void handleEvents(Robot action, InputEvent event) {
        MouseEvent mevent = null; // 鼠标事件
        MouseWheelEvent mwevent = null;// 鼠标滚动事件
        KeyEvent kevent = null; // 键盘事件
        int mousebuttonmask = -100; // 鼠标按键

        switch (event.getID()) {
            case MouseEvent.MOUSE_MOVED: // 鼠标移动
                mevent = (MouseEvent) event;
                action.mouseMove(mevent.getX(), mevent.getY());
                break;
            case MouseEvent.MOUSE_PRESSED: // 鼠标键按下
                mevent = (MouseEvent) event;
                action.mouseMove(mevent.getX(), mevent.getY());
                mousebuttonmask = getMouseClick(mevent.getButton());
                if (mousebuttonmask != -100)
                    action.mousePress(mousebuttonmask);
                break;
            case MouseEvent.MOUSE_RELEASED: // 鼠标键松开
                mevent = (MouseEvent) event;
                action.mouseMove(mevent.getX(), mevent.getY());
                mousebuttonmask = getMouseClick(mevent.getButton());// 取得鼠标按键
                if (mousebuttonmask != -100)
                    action.mouseRelease(mousebuttonmask);
                break;
            case MouseEvent.MOUSE_WHEEL: // 鼠标滚动
                mwevent = (MouseWheelEvent) event;
                action.mouseWheel(mwevent.getWheelRotation());
                break;
            case MouseEvent.MOUSE_DRAGGED: // 鼠标拖拽
                mevent = (MouseEvent) event;
                action.mouseMove(mevent.getX(), mevent.getY());
                break;
            case KeyEvent.KEY_PRESSED: // 按键
                kevent = (KeyEvent) event;
                action.keyPress(kevent.getKeyCode());
                break;
            case KeyEvent.KEY_RELEASED: // 松键
                kevent = (KeyEvent) event;
                action.keyRelease(kevent.getKeyCode());
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

    /**
     * 回调消息
     * @param msg
     */
    private void callBackMsg(String msg) {
        /*threadPoolExecutor = SpringBeanFactory.getBean("callBackThreadPool",ThreadPoolExecutor.class) ;
        threadPoolExecutor.execute(() -> {
            caller = SpringBeanFactory.getBean(MsgHandleCaller.class) ;
            caller.getMsgHandleListener().handle(msg);
        });*/

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //异常时断开连接
        cause.printStackTrace() ;
        ctx.close() ;
    }



}
