package com.jason.client.service;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String args[]) throws UnknownHostException, IOException{
        Socket s = new Socket("127.0.0.1",80);
        DataInputStream dis = new DataInputStream(s.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        ClientWindow cw = new ClientWindow(oos);
        byte[] imageBytes;
        while(true){
            imageBytes = new byte[dis.readInt()];   //先拿到传过来的数组长度
            dis.readFully(imageBytes);  //所有的数据存放到byte中
            cw.repainImage(imageBytes);
        }
    }
}
