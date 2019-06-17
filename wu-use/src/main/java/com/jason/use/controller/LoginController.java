package com.jason.use.controller;

import com.alibaba.fastjson.JSONObject;
import com.jason.use.JavafxApplication;
import com.jason.use.config.APIConfig;
import com.jason.use.enums.ConnectStatusEnum;
import com.jason.use.enums.HttpStatus;
import com.jason.use.enums.PaymentStatusEnum;
import com.jason.use.netty.CIMClient;
import com.jason.use.util.*;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import io.netty.channel.socket.nio.NioSocketChannel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private JFXCheckBox rememberInfo;

    @FXML
    private JFXTextField userName;

    @FXML
    private JFXPasswordField password;

    public static String jwt;
    public static String userId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

       /* rememberInfo.setSelected(true);

        String[] userInfo = FileUtil.getUserAndPass();
        if (userInfo.length == 2) {
            userName.setText(userInfo[0]);
            password.setText(userInfo[1]);
        }*/
    }

    /**
     * 登录按钮点击事件
     */
    @FXML
    public void loginAction() {
        String userName = this.userName.getText().trim();
        String password = this.password.getText().trim();
        if(userName.length() == 0 || password.length() == 0){
            JavafxApplication.showAlert("操作提示", "用户名和密码不能为空哦!", null, null, "确定");
            return;
        }
        String loginUrl = String.format(APIConfig.loginUrl, userName, password);
        String responseStr = HttpUtil.httpGet(loginUrl, null);
        JSONObject jsonObject = JSONObject.parseObject(responseStr);
        if (jsonObject.getInteger("status").equals(HttpStatus.OK.status)){
            jwt = jsonObject.getString("obj");
            userId = String.valueOf(JWTUtil.decodeToken(jwt));
            /*if (rememberInfo.isSelected()) {
                FileUtil.setUserAndPass(userName, password, true);
            }else{
                FileUtil.setUserAndPass(userName, password, false);
            }*/

            boolean checkOrderCount = checkOrderCount();

            if(checkOrderCount){
                String userStatusUrl = String.format(APIConfig.getUserStatusUrl, userId);
                responseStr = HttpUtil.httpGet(userStatusUrl, jwt);
                if(Integer.valueOf(responseStr) == ConnectStatusEnum.DisConnected.status){
                    boolean connectRemote = CIMClient.start();
                    if(connectRemote){
                        NettyUtil.sendGoogleProtocolMsg(Constants.LOGIN_USE, Integer.valueOf(userId), 0, null, null, null, (NioSocketChannel) CIMClient.channel);
                        JavafxApplication.showAlert("操作提示", jsonObject.getString("description"), "listView", getClass(), "确定");
                    }else{
                        JavafxApplication.showAlert("操作提示", "连接服务器失败!", null, null, "确定");
                    }
                }else{
                    JavafxApplication.showAlert("操作提示", "账号已在别处登录，请先退出再尝试登录!", null, null, "确定");
                }
            }else{
                JavafxApplication.showAlert("温馨提示", "您有补单尚未完成支付，请先完成补单才可登录使用端!", null, null, "确定");
            }
        }else{
            JavafxApplication.showAlert("操作提示", jsonObject.getString("description"), null, null, "确定");
        }
    }

    private boolean checkOrderCount() {
        String orderCountUrl = String.format(APIConfig.orderCountUrl, PaymentStatusEnum.Unpaid.status, LoginController.userId);
        String responseStr = HttpUtil.httpGet(orderCountUrl, LoginController.jwt);
        if("0".equals(responseStr)){
            return true;
        }else{
            return false;
        }
    }

    public static void listView(){
       JavafxApplication.switchView("/view/userList.fxml");
   }
}

