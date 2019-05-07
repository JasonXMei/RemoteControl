package com.jason.use.controller;

import com.jason.use.JavafxApplication;
import com.jason.use.util.FileUtil;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private JavafxApplication myApp;
    private Stage primaryStage;
    JFXAlert<Void> alert;

    @FXML
    private JFXCheckBox rememberInfo;

    @FXML
    private JFXButton btn_start;

    @FXML
    private JFXTextField tf_user;

    @FXML
    private JFXPasswordField tf_passWord;

    @FXML
    private JFXDialog dialog;
    @FXML
    private JFXButton acceptButton;

    public void setApp(JavafxApplication myApp, Stage primaryStage) {
        this.myApp = myApp;
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rememberInfo.setSelected(true);

        String[] userInfo = FileUtil.getUserAndPass();
        if (userInfo.length == 2) {
            tf_user.setText(userInfo[0]);
            tf_passWord.setText(userInfo[1]);
        }
    }

    /**
     * 登录按钮点击事件
     */
    @FXML
    public void onStart() {
        String userName = tf_user.getText().trim();
        String password = tf_passWord.getText().trim();
        doCheckUser(userName, password);
        if (rememberInfo.isSelected()) {
            FileUtil.setUserAndPass(userName, password, true);
        }else{
            FileUtil.setUserAndPass(userName, password, false);
        }
    }

    /**
     * 检查并登录
     */
    private void doCheckUser(String userName, String password) {
        showAlert("操作提示", "登陆成功", "确定", "alertAction");
    }

   private void showAlert(String heading, String body, String btnName, String methodName){
       alert = new JFXAlert<>(primaryStage);
       JFXDialogLayout layout = new JFXDialogLayout();
       layout.setHeading(new Label(heading));
       layout.setBody(new Label(body));
       JFXButton closeButton = new JFXButton(btnName);
       closeButton.getStyleClass().add("closeBtn");
       closeButton.setOnAction(event -> {
           try {
               getClass().getMethod(methodName).invoke(this);
           } catch (Exception e) {
               e.printStackTrace();
           }
       });
       layout.setActions(closeButton);
       alert.setContent(layout);

       alert.setOverlayClose(true);
       alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
       alert.initModality(Modality.APPLICATION_MODAL);
       alert.showAndWait();
   }

   public void alertAction(){
       alert.hideWithAnimation();
       myApp.gotoReaderUi(tf_user.getText());
   }
}

