package com.jason.client.controller;

import com.jason.client.JavafxApplication;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by abo on 2019/5/7.
 */
public class WrapperController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void replaceOrderList(ActionEvent actionEvent) {
        JavafxApplication.switchView("/view/replaceOrderList.fxml");
    }

    public void stopClient(ActionEvent actionEvent) {
        JavafxApplication.showAlertWithCancelBtn("操作提示", "确认退出客户端吗?", "closeClient", JavafxApplication.class, "确定");
    }
}
