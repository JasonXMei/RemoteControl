package com.jason.server.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(8080);
		System.out.println("服务器已经正常启动");
		Socket socket = server.accept();// 等待接收请求,阻塞方法
		System.out.println("有客户端连接");
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		// 将客户端与服务器端链接的输出流交个ImageThread处理
		ImageThread imageThread = new ImageThread(dos);
		new Thread(imageThread).start();
		new Thread(new EventThread(new ObjectInputStream(socket.getInputStream()))).start();
	}
}
