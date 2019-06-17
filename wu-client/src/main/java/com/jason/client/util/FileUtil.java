package com.jason.client.util;

import com.jason.client.JavafxApplication;

import java.io.*;
import java.util.Scanner;

public class FileUtil {

    public static String separtor = ",";
    //private static String filePath = FileUtil.class.getClassLoader().getResource("user_pass.txt").getPath();
    private static String filePath = FileUtil.class.getClassLoader().getResource("config/user_pass.txt").getPath();

    public static void setUserAndPass(String userName, String password, boolean flag) {
        PrintWriter outputStream = null;
        try {
            String jarpath = JavafxApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            jarpath = jarpath + "/config/user_pass.txt";
            outputStream = new PrintWriter(new FileOutputStream(jarpath));
            if (flag) {
                outputStream.print(userName + separtor + password);
            }else{
                outputStream.println();
            }
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null){
                outputStream.close();
            }
        }
    }

    public static String[] getUserAndPass( ) {
        Scanner scan = null;
        FileInputStream in = null;
        try {
            String jarpath = JavafxApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            jarpath = jarpath + "/config/user_pass.txt";
            in = new FileInputStream(jarpath);
            scan = new Scanner(in);
            if(scan.hasNext()){
                return scan.nextLine().split(separtor);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(scan != null){
                scan.close();
            }
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new String[0];
    }

}


