package com.jason.client.service;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

public class ClientWindow  extends JFrame{
	private static final long serialVersionUID = -374177591619018452L;
	private ObjectOutputStream oos;
    private JLabel label;
    //重写背景图片方法
    public void repainImage(byte [] imageBytes){
        label.setIcon(new ImageIcon(imageBytes));
        this.repaint();
    }
    public ClientWindow(ObjectOutputStream oos){
        this.oos = oos;
        this.setTitle("远程控制程序");
        label = new JLabel();
        JPanel p = new JPanel();
        p.add(label);
        JScrollPane scroll = new JScrollPane(p);//给p面板添加滚动条
        this.add(scroll);
        this.setSize(1024,768);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        try {
            oos.writeObject(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
