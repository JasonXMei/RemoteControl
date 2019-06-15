package com.jason.use.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jason.use.JavafxApplication;
import com.jason.use.config.APIConfig;
import com.jason.use.enums.TagTypeEnum;
import com.jason.use.netty.CIMClient;
import com.jason.use.util.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.netty.channel.socket.nio.NioSocketChannel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.*;

public class TaskController implements Initializable {

    @FXML
    JFXComboBox<String> subUserListForOrder;
    @FXML
    JFXComboBox<String> shopListForOrder;
    @FXML
    JFXTextField orderId;
    @FXML
    JFXTextField orderAmount;
    @FXML
    JFXTextField orderCommission;

    @FXML
    JFXComboBox<String> subUserListForTag;
    @FXML
    JFXComboBox<String> shopListForTag;
    @FXML
    JFXComboBox<String> tagTypeList;
    @FXML
    JFXTextField tagDescription;

    public static ObservableList<String> subUserItem;
    public static ObservableList<String> shopItem;
    public static ObservableList<String> tagTypeItem;
    public static Map<String,Integer> subUserNameMap = new HashMap<>();
    public static Map<String,Integer> shopMap = new HashMap<>();

    public static String userId = "";
    public static String subUserId = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String data = HttpUtil.httpPost(new HashMap<>(), String.format(APIConfig.userInfoUrl, LoginController.userId, userId), LoginController.jwt);
        JSONObject jsonObject = JSONObject.parseObject(data);
        /*int status = jsonObject.getInteger("status");

        if (status != HttpStatus.OK.status){
            JavafxApplication.showAlert("温馨提示",jsonObject.getString("description"), null, null, "确定");
            return;
        }*/

        JSONArray jsonArray = jsonObject.getJSONArray("subUserList");
        List<String> subUserList = new ArrayList<>();
        if (jsonArray != null){
            for(int i=0;i<jsonArray.size();i++){
                JSONObject subUser = jsonArray.getJSONObject(i);
                String subUserName = subUser.getString("subUserName");
                Integer subUserId = subUser.getInteger("id");
                subUserNameMap.put(subUserName, subUserId);
                subUserList.add(subUserName);
            }
        }
        subUserItem = FXCollections.observableArrayList(subUserList);

        JSONArray shopListArr = jsonObject.getJSONArray("shopList");
        List<String> shopList = new ArrayList<>();
        if (shopListArr != null){
            for(int i=0;i<shopListArr.size();i++){
                JSONObject subUser = shopListArr.getJSONObject(i);
                String shopName = subUser.getString("shopName");
                Integer shopId = subUser.getInteger("id");
                shopMap.put(shopName, shopId);
                shopList.add(shopName);
            }
        }
        shopItem = FXCollections.observableArrayList(shopList);

        TagTypeEnum[] tagTypeEnums = TagTypeEnum.values();
        List<String> tagList = new ArrayList<>();
        for(TagTypeEnum tte : tagTypeEnums){
           tagList.add(tte.getDescription());
        }
        tagTypeItem = FXCollections.observableArrayList(tagList);

        subUserListForOrder.setItems(subUserItem);
        shopListForOrder.setItems(shopItem);
        subUserListForTag.setItems(subUserItem);
        shopListForTag.setItems(shopItem);
        tagTypeList.setItems(tagTypeItem);
    }

    public void submitTag(ActionEvent actionEvent) {
        String selectSubUser = subUserListForTag.getValue();
        if (StringUtil.isEmpty(selectSubUser)){
            JavafxApplication.showAlert("温馨提示", "请选择小号!", null, null, "确定");
            return;
        }
        String selectShop = shopListForTag.getValue();
        if (StringUtil.isEmpty(selectShop)){
            JavafxApplication.showAlert("温馨提示", "请选择店铺!", null, null, "确定");
            return;
        }

        Integer subUserId = subUserNameMap.get(selectSubUser);
        Integer shopId = shopMap.get(selectShop);
        String tagName = tagTypeList.getValue();
        if (StringUtil.isEmpty(tagName)){
            JavafxApplication.showAlert("温馨提示", "请选择标签类型!", null, null, "确定");
            return;
        }

        String desc = tagDescription.getText();
        if(desc == null || desc.trim().length() == 0){
            desc = " ";
        }

        HashMap<String, String> paramsMap =
                HttpUtil.generateParamMap("userId," + subUserId, "shopId," + shopId,
                "description," + desc, "tagTypeStr," + tagName);
        String response = HttpUtil.httpPost(paramsMap, APIConfig.submitTagUrl, LoginController.jwt);
        JSONObject resJSON = JSONObject.parseObject(response);
        JavafxApplication.showAlert("温馨提示", resJSON.getString("description"), null, null, "确定");
    }

    public void submitOrder(ActionEvent actionEvent) {
        String selectSubUser = subUserListForOrder.getValue();
        if (StringUtil.isEmpty(selectSubUser)){
            JavafxApplication.showAlert("温馨提示", "请选择小号!", null, null, "确定");
            return;
        }
        String selectShop = shopListForOrder.getValue();
        if (StringUtil.isEmpty(selectShop)){
            JavafxApplication.showAlert("温馨提示", "请选择店铺!", null, null, "确定");
            return;
        }

        Integer subUserId = subUserNameMap.get(selectSubUser);
        Integer shopId = shopMap.get(selectShop);
        String orderIdStr = orderId.getText();
        if(StringUtil.isEmpty(orderIdStr)){
            JavafxApplication.showAlert("温馨提示", "请输入订单号!", null, null, "确定");
            return;
        }
        String orderAmountStr = orderAmount.getText();
        if(StringUtil.isEmpty(orderAmountStr)){
            JavafxApplication.showAlert("温馨提示", "请输入订单金额!", null, null, "确定");
            return;
        }
        String orderCommissionStr = orderCommission.getText();
        if(StringUtil.isEmpty(orderCommissionStr)){
            JavafxApplication.showAlert("温馨提示", "请输入订单返现金额!", null, null, "确定");
            return;
        }

        HashMap<String, String> paramsMap =
                HttpUtil.generateParamMap("userId," + subUserId, "shopId," + shopId,
                        "orderId," + orderIdStr, "orderAmount," + orderAmountStr, "orderCommission," + orderCommissionStr);
        String response = HttpUtil.httpPost(paramsMap, APIConfig.submitOrderUrl, LoginController.jwt);
        JSONObject resJSON = JSONObject.parseObject(response);
        JavafxApplication.showAlert("温馨提示", resJSON.getString("description"), null, null, "确定");
    }

    public void connectClient(ActionEvent actionEvent) {
        NettyUtil.sendGoogleProtocolMsg(Constants.MSG_CONTROL, Integer.valueOf(LoginController.userId), Integer.valueOf(subUserId), null, null, null, (NioSocketChannel) CIMClient.channel);
    }
}

