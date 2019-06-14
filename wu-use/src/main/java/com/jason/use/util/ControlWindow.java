package com.jason.use.util;

import com.jason.use.netty.CIMClient;
import io.netty.channel.socket.nio.NioSocketChannel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by abo on 2019/5/29.
 */
public class ControlWindow extends JFrame implements ActionListener{
    private static final long serialVersionUID = -374177591619018452L;
    private JLabel label;

    Toolkit toolkit = Toolkit.getDefaultToolkit();

    // 设置窗体默认大小,使其适应屏幕大小
    private int DEFAULE_WIDTH = (int) (toolkit.getScreenSize().getWidth() * 0.4);
    private int DEFAULE_HEIGH = (int) (toolkit.getScreenSize().getHeight() * 0.5);

    // 设置窗体位置,使其在屏幕居中
    private int Location_x = (int) (toolkit.getScreenSize().getWidth() - DEFAULE_WIDTH) / 2;
    private int Location_y = (int) (toolkit.getScreenSize().getHeight() - DEFAULE_HEIGH) / 2;
    int mouseAtX = 0;
    int mouseAtY = 0;

    private JButton maximizeBtn;

    //重写背景图片方法
    public void repainImage(byte [] imageBytes){
        label.setIcon(new ImageIcon(imageBytes));
        this.repaint();
    }

    public ControlWindow(){
        setUndecorated(true);//设置窗体的标题栏不可见
        /*
         * 设置窗体可拖动
         */
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                /*
                 * 获取点击鼠标时的坐标
                 */
                mouseAtX = e.getPoint().x;
                mouseAtY = e.getPoint().y;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent e){
                setLocation((e.getXOnScreen()-mouseAtX),(e.getYOnScreen()-mouseAtY));//设置拖拽后，窗口的位置
            }
        });


        /*this.addKeyListener(new KeyListener() {
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
        });*/

        /*
         * 实例化简单组件
         */
        JButton minimumBtn = new JButton("最小化");
        maximizeBtn = new JButton("最大化");
        JButton closeBtn = new JButton("关闭");

        minimumBtn.addActionListener(this);
        maximizeBtn.addActionListener(this);
        closeBtn.addActionListener(this);

        /*closeBtn.setBounds(DEFAULE_WIDTH-90,0,90,45);
        maximizeBtn.setBounds(DEFAULE_WIDTH-185,0,90,45);
        b1.setBounds(DEFAULE_WIDTH-280,0,90,45);*/

        closeBtn.setBounds(200,0,90,45);
        maximizeBtn.setBounds(100,0,90,45);
        minimumBtn.setBounds(0,0,90,45);

        /*JLabel label = new JLabel("无边框窗体的演示",JLabel.CENTER);
        label.setBounds(280,280,450,45);
        label.setFont(new Font("华文新魏", Font.BOLD,40));

        JPanel contentPane = new JPanel();
        contentPane.setBackground(Color.GRAY);
        contentPane.setLayout(null);

        contentPane.add(b1);
        contentPane.add(maximizeBtn);
        contentPane.add(closeBtn);
        contentPane.add(label);
        setContentPane(contentPane);//设置ContentPane*/

        label = new JLabel();
        JPanel p = new JPanel();
        p.add(label);
        JScrollPane scroll = new JScrollPane(p);//给p面板添加滚动条
        scroll.add(minimumBtn);
        scroll.add(maximizeBtn);
        scroll.add(closeBtn);
        this.add(scroll);

        setSize(DEFAULE_WIDTH,DEFAULE_HEIGH);// 设置窗体默认大小,使其适应屏幕大小
        setLocation(Location_x, Location_y);//设置窗体在屏幕中的位置
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); //设置窗体可见
    }

    public void sendEvent(InputEvent event){
        NettyUtil.sendGoogleProtocolMsg(Constants.MSG_EVENT, 0, 1, null,
                ByteObjConverter.objectToByte(event), null, (NioSocketChannel) CIMClient.channel);
    }

    public static void main(String[] args) {
        new ControlWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "最大化") {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            maximizeBtn.setText("正常化");
        }

        if (e.getActionCommand() == "正常化") {
            setExtendedState(JFrame.NORMAL);
            maximizeBtn.setText("最大化");

        }

        if (e.getActionCommand() == "最小化") {
            setExtendedState(JFrame.ICONIFIED);
        }

        if (e.getActionCommand() == "关闭") {
            /*System.exit(0);*/
            this.setVisible(false);
        }
    }
}
