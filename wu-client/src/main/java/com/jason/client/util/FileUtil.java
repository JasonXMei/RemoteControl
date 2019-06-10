package com.jason.client.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileUtil {

    public static String separtor = ",";
    private static String filePath = FileUtil.class.getClassLoader().getResource("user_pass.txt").getPath();

    public static void setUserAndPass(String userName, String password, boolean flag) {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileOutputStream(filePath));
            if (flag) {
                outputStream.print(userName + separtor + password);
            }else{
                outputStream.println();
            }
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String[] getUserAndPass( ) {
        Scanner scan = null;
        try {
            scan = new Scanner(new FileInputStream(filePath));
            if(scan.hasNext()){
                return scan.nextLine().split(separtor);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

}


