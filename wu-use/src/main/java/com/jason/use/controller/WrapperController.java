package com.jason.use.controller;

import com.alibaba.fastjson.JSONObject;
import com.jason.use.JavafxApplication;
import com.jason.use.config.APIConfig;
import com.jason.use.util.HttpUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by abo on 2019/5/7.
 */
public class WrapperController implements Initializable {

    private static String refferUserLink = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void openLinkWindow(){
        String data = HttpUtil.httpPost(new HashMap<>(), String.format(APIConfig.userInfoUrl, LoginController.userId, LoginController.userId), LoginController.jwt);
        JSONObject jsonObject = JSONObject.parseObject(data);

        JSONObject userVO = jsonObject.getJSONObject("userVO");

        if(userVO != null && userVO.getString("inviteCode") != null){
            refferUserLink = APIConfig.referrerUserLink + userVO.getString("inviteCode");
            JavafxApplication.showAlert("操作提示", refferUserLink,"copyClipboard", getClass(), "点击复制链接");
        }else{
            JavafxApplication.showAlert("温馨提示", "获取用户邀请码出错啦，请稍后重试!", null, null, "确定");
        }
    }

    public static void copyClipboard(){
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 封装文本内容
        Transferable trans = new StringSelection(refferUserLink);
        // 把文本内容设置到系统剪贴板
        clipboard.setContents(trans, null);
    }

    public void userList(ActionEvent actionEvent) {
        JavafxApplication.switchView("/view/userList.fxml");
    }

    public void tagList(ActionEvent actionEvent) {
        JavafxApplication.switchView("/view/tagList.fxml");
    }

    public void openWebBackend(ActionEvent actionEvent) {
        try {
            // 创建一个URI实例
            String backEndUrl = String.format(APIConfig.backEndLink, LoginController.jwt);
            java.net.URI uri = java.net.URI.create(backEndUrl);
            // 获取当前系统桌面扩展
            java.awt.Desktop dp = java.awt.Desktop.getDesktop();
            // 判断系统桌面是否支持要执行的功能
            if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
                // 获取系统默认浏览器打开链接
                dp.browse(uri);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeClient(ActionEvent actionEvent) {
        JavafxApplication.showAlertWithCancelBtn("操作提示", "确认退出使用端吗?", "closeClient", JavafxApplication.class, "确定");
    }
}
