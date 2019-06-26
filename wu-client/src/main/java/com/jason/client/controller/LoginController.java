package com.jason.client.controller;

import com.alibaba.fastjson.JSONObject;
import com.jason.client.JavafxApplication;
import com.jason.client.config.APIConfig;
import com.jason.client.enums.ConnectStatusEnum;
import com.jason.client.enums.HttpStatus;
import com.jason.client.netty.CIMClient;
import com.jason.client.util.Constants;
import com.jason.client.util.HttpUtil;
import com.jason.client.util.NettyUtil;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import io.netty.channel.socket.nio.NioSocketChannel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private JFXTextField userName;
    @FXML
    private JFXPasswordField password;

    public static String jwt;
    public static String userId;

    /*
    @FXML
    private JFXCheckBox rememberInfo;
    @FXML
    private StackPane stackPane;
    public static String userNameStr;
    public static String passwordStr;
    public static boolean rememberFlag = false;
    public static Map<String, Integer> subUserMap = new HashMap<>();
    static JFXComboBox<String> subUserListComboBox = new JFXComboBox<>();
    public static ObservableList<String> subUserItem;
    */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*stackPane.setVisible(false);
        rememberInfo.setSelected(true);

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

            String connectStatusClientStr = userJSON.getString("connectStatusClientStr");

            if(connectStatusClientStr.equals(ConnectStatusEnum.DisConnected.description)){
                boolean connectRemote = CIMClient.start();
                if(connectRemote){
                    NettyUtil.sendGoogleProtocolMsg(Constants.LOGIN_CLIENT, Integer.valueOf(userId), 0, null, null, null, (NioSocketChannel) CIMClient.channel);
                    JavafxApplication.showAlert("操作提示", "{登录}成功!", "listView", getClass(), "确定");
                }else{
                    JavafxApplication.showAlert("操作提示", "连接服务器失败!", null, null, "确定");
                }
            }else{
                JavafxApplication.showAlert("操作提示", "账号已在别处登录，请先退出再尝试登录!", null, null, "确定");
            }
            /*if (rememberInfo.isSelected()) {
                rememberFlag = true;
                userNameStr = userName;
                passwordStr = password;
            }*/

            //getSubUserList();
            //JavafxApplication.showSelectConfirmed("操作提示", subUserListComboBox, stackPane, "chooseAndCheckSubUser", getClass());
        }else{
            JavafxApplication.showAlert("操作提示", jsonObject.getString("description"), null, null, "确定");
        }
    }

    /*public void getSubUserList(){
        *//*String data = HttpUtil.httpPost(new HashMap<>(), String.format(APIConfig.userInfoUrl, userId, userId), LoginController.jwt);
        JSONObject jsonObject = JSONObject.parseObject(data);

        JSONArray jsonArray = jsonObject.getJSONArray("subUserList");

        List<String> subUserList = new ArrayList<>();
        if (jsonArray != null){
            for(int i=0;i<jsonArray.size();i++){
                JSONObject subUser = jsonArray.getJSONObject(i);
                String subUserName = subUser.getString("subUserName");
                Integer subUserId = subUser.getInteger("id");
                subUserMap.put(subUserName, subUserId);
                subUserList.add(subUserName);
            }
        }
        subUserItem = FXCollections.observableArrayList(subUserList);
        subUserListComboBox.setPromptText("请选择登陆小号");
        subUserListComboBox.setItems(subUserItem);*//*
    }

   public static void chooseAndCheckSubUser(){
       *//*if(rememberFlag){
           FileUtil.setUserAndPass(userNameStr, passwordStr, true);
       }*//*

       *//*String selectSubUser = subUserListComboBox.getValue();
       if (StringUtil.isEmpty(selectSubUser)){
           JavafxApplication.showAlert("温馨提示", "请选择小号!", null, null, "确定");
           return;
       }
       userId = String.valueOf(subUserMap.get(selectSubUser));*//*
   }*/

   public static void listView(){
       JavafxApplication.switchView("/view/replaceOrderList.fxml");
   }
}

