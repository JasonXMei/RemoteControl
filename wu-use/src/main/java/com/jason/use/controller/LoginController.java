package com.jason.use.controller;

import com.alibaba.fastjson.JSONObject;
import com.jason.use.JavafxApplication;
import com.jason.use.config.APIConfig;
import com.jason.use.enums.ConnectStatusEnum;
import com.jason.use.enums.HttpStatus;
import com.jason.use.enums.NeedClientLoginEnum;
import com.jason.use.netty.CIMClient;
import com.jason.use.util.Constants;
import com.jason.use.util.HttpUtil;
import com.jason.use.util.NettyUtil;
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
    public static boolean needClientLogin = false;

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
            JSONObject userJSON = jsonObject.getJSONObject("obj");

            userId = userJSON.getString("id");
            jwt = userJSON.getString("jwt");

            String needClientLoginStr = userJSON.getString("needClientLoginStr");
            String connectStatusClientStr = userJSON.getString("connectStatusClientStr");

            /*if (rememberInfo.isSelected()) {
                FileUtil.setUserAndPass(userName, password, true);
            }else{
                FileUtil.setUserAndPass(userName, password, false);
            }*/

            if(needClientLoginStr.equals(NeedClientLoginEnum.Need.description)){
                needClientLogin = true;
            }
            if(connectStatusClientStr.equals(ConnectStatusEnum.DisConnected.description) && needClientLogin){
                JavafxApplication.showAlert("操作提示", "请先登录智慧联盟客户端!", null, null, "确定");
            }else{
                String connectStatusUseStr = userJSON.getString("connectStatusUseStr");
                if(connectStatusUseStr.equals(ConnectStatusEnum.DisConnected.description)){
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
            }
        }else{
            JavafxApplication.showAlert("操作提示", jsonObject.getString("description"), null, null, "确定");
        }
    }

    public static void listView(){
       JavafxApplication.switchView("/view/userList.fxml");
   }
}

