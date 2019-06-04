package com.jason.use.controller;

import com.jason.use.JavafxApplication;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by abo on 2019/5/7.
 */
public class WrapperController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void openLinkWindow(){
        JavafxApplication.showAlert("操作提示", "www.baidu.com",
                 "copyClipboard", getClass(), "点击复制链接");
    }

    public static void copyClipboard(){
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 封装文本内容
        Transferable trans = new StringSelection("www.baidu.com");
        // 把文本内容设置到系统剪贴板
        clipboard.setContents(trans, null);
    }

    public void userList(ActionEvent actionEvent) {
        JavafxApplication.switchView("/view/userList.fxml");
    }

    public void tagList(ActionEvent actionEvent) {
        JavafxApplication.switchView("/view/tagList.fxml");
    }
}
