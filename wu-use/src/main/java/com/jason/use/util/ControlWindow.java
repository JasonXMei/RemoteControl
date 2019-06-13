package com.jason.use.util;

import com.jason.use.controller.LoginController;
import com.jason.use.controller.TaskController;
import com.jason.use.netty.CIMClient;
import io.netty.channel.socket.nio.NioSocketChannel;

import javax.swing.*;
import java.awt.event.*;

public class ControlWindow extends JFrame {
    private static final long serialVersionUID = -374177591619018452L;
    private JLabel label;

    //重写背景图片方法
    public void repainImage(byte [] imageBytes){
        label.setIcon(new ImageIcon(imageBytes));
        this.repaint();
    }

    public ControlWindow(){
        this.setTitle("远程控制程序");
        label = new JLabel();
        JPanel p = new JPanel();
        p.add(label);
        //setUndecorated(true);
        JScrollPane scroll = new JScrollPane(p);//给p面板添加滚动条
        this.add(scroll);
        this.setSize(1024,768);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
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
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

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
    }
    public void sendEvent(InputEvent event){
        NettyUtil.sendGoogleProtocolMsg(Constants.MSG_EVENT, Integer.parseInt(LoginController.userId), Integer.parseInt(TaskController.subUserId), null,
                ByteObjConverter.objectToByte(event), null, (NioSocketChannel) CIMClient.channel);
    }
}
