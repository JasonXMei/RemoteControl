package com.jason.use.util;

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
    private int DEFAULE_WIDTH = (int) (toolkit.getScreenSize().getWidth() * 0.4);
    private int DEFAULE_HEIGH = (int) (toolkit.getScreenSize().getHeight() * 0.5);

    // 设置窗体位置,使其在屏幕居中
    private int Location_x = (int) (toolkit.getScreenSize().getWidth() - DEFAULE_WIDTH) / 2;
    private int Location_y = (int) (toolkit.getScreenSize().getHeight() - DEFAULE_HEIGH) / 2;
    int mouseAtX = 0;
    int mouseAtY = 0;

    //重写背景图片方法
    public void repainImage(byte [] imageBytes){
        label.setText("");
        label.setIcon(new ImageIcon(imageBytes));
        this.repaint();
    }

    public JavaFXWindow(){
        //设定标志，让MainFrame自己接收窗口事件
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        this.setTitle("远程控制程序");
        //setUndecorated(true);//设置窗体的标题栏不可见
        try {
            this.setIconImage(ImageIO.read(getClass().getResourceAsStream("/img/logo.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        label = new JLabel("正在连接远程桌面,请稍候...", JLabel.CENTER);
        JPanel p = new JPanel(new BorderLayout());
        p.add(label, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(p);//给p面板添加滚动条
        this.add(scroll);

        setSize(DEFAULE_WIDTH,DEFAULE_HEIGH);// 设置窗体默认大小,使其适应屏幕大小
        setLocation(Location_x, Location_y);//设置窗体在屏幕中的位置

        this.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                sendEvent(e);
            }

            public void keyPressed(KeyEvent e) {
                sendEvent(e);
            }
        });

        label.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
                sendEvent(e);
            }

            public void mousePressed(MouseEvent e) {
                sendEvent(e);
            }

            public void mouseClicked(MouseEvent e) {
                sendEvent(e);
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });

        setVisible(true); //设置窗体可见
    }

    public void sendEvent(InputEvent event){
        NettyUtil.sendGoogleProtocolMsg(Constants.MSG_EVENT, Integer.parseInt(LoginController.userId), Integer.parseInt(TaskController.subUserId), null,
                ByteObjConverter.objectToByte(event), null, (NioSocketChannel) CIMClient.channel);
    }

    protected void processWindowEvent(final WindowEvent pEvent) {
        if (pEvent.getID() == WindowEvent.WINDOW_CLOSING) {
            //处理JFrame关闭事件……
            this.setVisible(false);
        }else{
            //忽略其他事件，交给JFrame处理
            super.processWindowEvent(pEvent);
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
