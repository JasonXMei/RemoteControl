package com.jason.use.netty;

import com.jason.use.JavafxApplication;
import com.jason.use.protocol.WUProto;
import com.jason.use.util.Constants;
import com.jason.use.util.JavaFXWindow;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.Modality;

import java.awt.*;
import java.awt.Paint;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@ChannelHandler.Sharable
public class CIMClientHandle extends SimpleChannelInboundHandler<WUProto.WUProtocol> {

    private ThreadPoolExecutor threadPoolExecutor ;

    private ScheduledExecutorService scheduledExecutorService ;

    public static JavaFXWindow javaFXWindow;

    public static String errorMsg = null;

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
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,  WUProto.WUProtocol msg) throws Exception {
        System.out.println("收到服务端消息:" +  msg.getReceiveUserId() + "," + msg.getSendUserId() + "," + msg.getMsgType());
        if (msg.getMsgType() == Constants.MSG_IMG && JavaFXWindow.isConnected){
            if(javaFXWindow == null){
                javaFXWindow = new JavaFXWindow();
            }
            //NettyUtil.sendGoogleProtocolMsg(Constants.CommandType.PONG, 0, 1, null, null, ctx);
            if(!javaFXWindow.isVisible()){
                javaFXWindow.setVisible(true);
            }
            javaFXWindow.repainImage(msg.getScreenImg().toByteArray());
        }

        if (msg.getMsgType() == Constants.MSG_ERROR) {
            /*if(javaFXWindow == null){
                javaFXWindow = new JavaFXWindow();
            }
            if(!javaFXWindow.isVisible()){
                javaFXWindow.setVisible(true);
            }
            //JavafxApplication.showAlert("温馨提示", msg.getMessage(), null, null, "确定");
            javaFXWindow.showError(msg.getMessage());*/
            //showAlert("温馨提示", msg.getMessage(),"确定");
            errorMsg = msg.getMessage();
        }
    }

    public static void showAlert(String headMsg, String bodyMsg, String btnName){
        JFXAlert jfxAlert = new JFXAlert<>();
        JFXDialogLayout dialogLayout = new JFXDialogLayout();

        Text header = new Text(headMsg);
        header.setFont(new javafx.scene.text.Font("System", 18));
        header.setFill(javafx.scene.paint.Paint.valueOf("#495057"));

        Text body = new Text(bodyMsg);

        dialogLayout.setHeading(header);
        dialogLayout.setBody(body);
        JFXButton closeButton = new JFXButton(btnName);

        closeButton.setStyle("-fx-background-color: #039BE5;-fx-font-size: 18px");
        closeButton.setTextFill(javafx.scene.paint.Paint.valueOf("#FFFFFF"));
        closeButton.setRipplerFill(javafx.scene.paint.Paint.valueOf("#FFFFFF"));
        closeButton.setButtonType(JFXButton.ButtonType.RAISED);

        closeButton.setOnAction(event -> {
            jfxAlert.hideWithAnimation();
        });
        dialogLayout.setActions(closeButton);
        jfxAlert.setContent(dialogLayout);

        jfxAlert.setOverlayClose(true);
        jfxAlert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        jfxAlert.initModality(Modality.APPLICATION_MODAL);
        jfxAlert.showAndWait();
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
