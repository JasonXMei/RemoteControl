package com.jason.use.util;

import com.alibaba.fastjson.JSONObject;
import com.jason.use.controller.LoginController;
import com.jason.use.controller.TaskController;
import com.jason.use.netty.CIMClient;
import io.netty.channel.socket.nio.NioSocketChannel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by abo on 2019/5/29.
 */
public class JavaFXWindow extends JFrame{
    private static final long serialVersionUID = -374177591619018452L;
    private JLabel label;

    // 设置窗体默认大小,使其适应屏幕大小
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    private int DEFAULE_WIDTH = 1000;
    private int DEFAULE_HEIGH = 618;
    private int MAX_WIDTH = 1000;
    private int MAX_HEIGH = 618;

    // 设置窗体位置,使其在屏幕居中
    private int Location_x = 0;
    private int Location_y = 0;

    public static volatile boolean isConnected = false;

    //重写背景图片方法
    public void repainImage(byte [] imageBytes){
        label.setText("");
        ImageIcon imageIcon = new ImageIcon(imageBytes);
        MAX_WIDTH = imageIcon.getIconWidth();
        MAX_HEIGH = imageIcon.getIconHeight();
        label.setIcon(imageIcon);
        initWindow(false);
        this.repaint();
    }

    public void showError(String msg){
        label.setText(msg);
        initWindow(false);
    }

    private int screenWidth = (int) toolkit.getScreenSize().getWidth();
    private int screenHeight = (int) toolkit.getScreenSize().getHeight();
    public void initWindow(boolean flag){
        if(flag){
            if(screenWidth > MAX_WIDTH){
                Location_x = (screenWidth - MAX_WIDTH) / 2;
            }else{
                Location_x = 0;
            }
            if(screenHeight > MAX_HEIGH){
                Location_y = (screenHeight - MAX_HEIGH) / 2;
            }else{
                Location_y = 0;
            }
            setSize(DEFAULE_WIDTH,DEFAULE_HEIGH);// 设置窗体默认大小,使其适应屏幕大小
            setLocation(Location_x, Location_y);//设置窗体在屏幕中的位置
        }else{
            setSize(MAX_WIDTH, MAX_HEIGH);
        }
    }

    public JavaFXWindow(){
        //设定标志，让MainFrame自己接收窗口事件
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        this.setTitle("远程控制程序");
        this.setResizable(false);
        //setUndecorated(true);//设置窗体的标题栏不可见
        try {
            this.setIconImage(ImageIO.read(getClass().getResourceAsStream("/img/logo.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //label = new JLabel("正在连接远程桌面,请稍候...", JLabel.CENTER);
        //JPanel p = new JPanel(new BorderLayout());
        label = new JLabel();
        JPanel p = new JPanel();
        p.add(label);

        JScrollPane scroll = new JScrollPane(p);//给p面板添加滚动条
        this.add(scroll);

        initWindow(true);

        this.addKeyListener(new KeyAdapter() {//窗体增加键盘监听事件
            /*@Override
            public void keyTyped(KeyEvent e) {
                JOptionPane.showMessageDialog(null, KeyEvent.getKeyText(e.getKeyCode()));
                sendMouseEvent(e);
            }*/

            @Override
            public void keyReleased(KeyEvent e) {
                //JOptionPane.showMessageDialog(null, KeyEvent.getKeyText(e.getKeyCode()));
                sendKeyEvent(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //JOptionPane.showMessageDialog(null, KeyEvent.getKeyText(e.getKeyCode()));
                sendKeyEvent(e);
            }
        });
        /*this.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                sendMouseEvent(e);
            }

            public void keyReleased(KeyEvent e) {
                sendMouseEvent(e);
            }

            public void keyPressed(KeyEvent e) {
                JOptionPane.showMessageDialog(null, KeyEvent.getKeyText(e.getKeyCode()));
                sendMouseEvent(e);
            }
        });*/

        label.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
                sendMouseEvent(e);
            }

            public void mousePressed(MouseEvent e) {
                sendMouseEvent(e);
            }

            public void mouseClicked(MouseEvent e) {
                sendMouseEvent(e);
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });

        setVisible(true); //设置窗体可见
    }

    public void sendMouseEvent(MouseEvent event){
        JSONObject eventJSON = new JSONObject();
        eventJSON.put("eventId", event.getID());
        eventJSON.put("moveX", event.getX());
        eventJSON.put("moveY", event.getY());
        eventJSON.put("btn", event.getButton());
        if(MouseEvent.MOUSE_WHEEL == event.getID())
            eventJSON.put("wheelRotation", ((MouseWheelEvent) event).getWheelRotation());
        NettyUtil.sendGoogleProtocolMsg(Constants.MSG_EVENT, Integer.parseInt(LoginController.userId), Integer.parseInt(TaskController.subUserId), null,
                ByteObjConverter.objectToByte(eventJSON), null, (NioSocketChannel) CIMClient.channel);
    }

    public void sendKeyEvent(KeyEvent event){
        JSONObject eventJSON = new JSONObject();
        eventJSON.put("eventId", event.getID());
        eventJSON.put("keyCode", event.getKeyCode());
        //KeyEvent event1 = new KeyEvent(label, event.getID(), event.getWhen(), event.getModifiers(), ((KeyEvent) event).getKeyCode(), ((KeyEvent) event).getKeyChar());
        NettyUtil.sendGoogleProtocolMsg(Constants.MSG_EVENT, Integer.parseInt(LoginController.userId), Integer.parseInt(TaskController.subUserId), null,
                ByteObjConverter.objectToByte(eventJSON), null, (NioSocketChannel) CIMClient.channel);
    }

    protected void processWindowEvent(final WindowEvent pEvent) {
        if (pEvent.getID() == WindowEvent.WINDOW_CLOSING){
            //处理JFrame关闭事件……
            this.setVisible(false);
            isConnected = false;
            NettyUtil.sendGoogleProtocolMsg(Constants.MSG_DIS_CONTROL, Integer.parseInt(LoginController.userId), Integer.parseInt(TaskController.subUserId), null,
                    null, null, (NioSocketChannel) CIMClient.channel);
        }else{
            //忽略其他事件，交给JFrame处理
            super.processWindowEvent(pEvent);
            /*int newState = pEvent.getNewState();// 获得窗体现在的状态
            switch (newState) {// 判断窗台现在的状态
                case Frame.NORMAL:// 窗体处于正常化
                default:// 窗体处于最小化
                    break;
                case Frame.MAXIMIZED_BOTH:// 窗体处于最大化
                    initWindow(false);
            }*/
        }
    }

    public static void main(String[] args) throws AWTException {
        JavaFXWindow controlWindow = new JavaFXWindow();
        Robot robot = new Robot();

        // 截取整个屏幕
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle rec = new Rectangle(dimension);
        BufferedImage image = robot.createScreenCapture(rec);;
        byte imageBytes[] = ByteObjConverter.getImageBytes(image);
        controlWindow.repainImage(imageBytes);
    }
}
