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

    private volatile  boolean compareScreenFlag = false;
    //private int MAX_WIDTH = 1000;
    //private int MAX_HEIGH = 618;

    // 设置窗体位置,使其在屏幕居中
    private int Location_x = 0;
    private int Location_y = 0;

    public static volatile boolean isConnected = false;
    private int screenWidth = (int) toolkit.getScreenSize().getWidth();
    private int screenHeight = (int) toolkit.getScreenSize().getHeight();
    public static volatile int count = 0;
    //public static int barWidth = 50;

    //重写背景图片方法
    public void repainImage(byte [] imageBytes){
        label.setText("");
        ImageIcon imageIcon = new ImageIcon(imageBytes);
        if(count == 0){
            initWindow(imageIcon.getIconWidth(), imageIcon.getIconHeight());
        }
        label.setIcon(imageIcon);
        this.repaint();
        count++;
    }

    public void initWindow(int imgWidth, int imgHeigh){
        if(imgWidth > screenWidth){
            DEFAULE_WIDTH = screenWidth;
            Location_x = 0;
            compareScreenFlag = true;
        }else{
            DEFAULE_WIDTH = imgWidth;
            Location_x = (screenWidth - DEFAULE_WIDTH) / 2;
            compareScreenFlag = false;
        }

        if(imgHeigh > screenHeight){
            DEFAULE_HEIGH = screenHeight;
            Location_y = 0;
        }else{
            DEFAULE_HEIGH = imgHeigh;
            Location_y = (screenHeight - DEFAULE_HEIGH) / 2;
        }

        if(compareScreenFlag){
            setSize(DEFAULE_WIDTH, DEFAULE_HEIGH);// 设置窗体默认大小,使其适应屏幕大小
        }else{
            setSize(DEFAULE_WIDTH + 25, DEFAULE_HEIGH + 50);// 设置窗体默认大小,使其适应屏幕大小
        }
        setLocation(Location_x, Location_y);//设置窗体在屏幕中的位置
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
        //scroll.setPreferredSize(new Dimension(barWidth, barWidth));
        scroll.setWheelScrollingEnabled(false);
        scroll.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                sendMouseEvent(e);
            }
        });
        this.add(scroll);

        //initWindow();

        this.addKeyListener(new KeyAdapter() {//窗体增加键盘监听事件
            @Override
            public void keyTyped(KeyEvent e) {
                //JOptionPane.showMessageDialog(null, KeyEvent.getKeyText(e.getKeyCode()));
                //sendKeyEvent(e);
            }

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

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                sendMouseEvent(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //if(e.getButton() == MouseEvent.BUTTON2)
                sendMouseEvent(e);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(compareScreenFlag){
                    sendMouseEvent(e);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //sendMouseEvent(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //sendMouseEvent(e);
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e){
                //System.out.println("mouse wheel move");
            }

            @Override
            public void mouseDragged(MouseEvent e){
                System.out.println("mouseDragger");
                //e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                sendMouseEvent(e);
            }

            @Override
            public void mouseMoved(MouseEvent e){
                System.out.println("mouseMove");
                sendMouseEvent(e);
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
        if(MouseEvent.MOUSE_WHEEL == event.getID()) {
            eventJSON.put("wheelRotation", ((MouseWheelEvent) event).getWheelRotation());
        }
        //System.out.println(eventJSON);
        NettyUtil.sendGoogleProtocolMsg(Constants.MSG_EVENT, Integer.parseInt(LoginController.userId), Integer.parseInt(TaskController.userId), null,
                ByteObjConverter.objectToByte(eventJSON), null, (NioSocketChannel) CIMClient.channel);
    }

    public void sendKeyEvent(KeyEvent event){
        JSONObject eventJSON = new JSONObject();
        eventJSON.put("eventId", event.getID());
        eventJSON.put("keyCode", event.getKeyCode());
        //KeyEvent event1 = new KeyEvent(label, event.getID(), event.getWhen(), event.getModifiers(), ((KeyEvent) event).getKeyCode(), ((KeyEvent) event).getKeyChar());
        NettyUtil.sendGoogleProtocolMsg(Constants.MSG_EVENT, Integer.parseInt(LoginController.userId), Integer.parseInt(TaskController.userId), null,
                ByteObjConverter.objectToByte(eventJSON), null, (NioSocketChannel) CIMClient.channel);
    }

    protected void processWindowEvent(final WindowEvent pEvent) {
        if (pEvent.getID() == WindowEvent.WINDOW_CLOSING){
            //处理JFrame关闭事件……
            this.setVisible(false);
            isConnected = false;
            NettyUtil.sendGoogleProtocolMsg(Constants.MSG_DIS_CONTROL, Integer.parseInt(LoginController.userId), Integer.parseInt(TaskController.userId), null,
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

    public static void main(String[] args) throws AWTException{
        JavaFXWindow controlWindow = new JavaFXWindow();
        Robot robot = new Robot();

        // 截取整个屏幕
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle rec = new Rectangle(dimension);
        BufferedImage image = robot.createScreenCapture(rec);
        byte imageBytes[] = ByteObjConverter.getImageBytes(image);
        controlWindow.repainImage(imageBytes);
    }
}
