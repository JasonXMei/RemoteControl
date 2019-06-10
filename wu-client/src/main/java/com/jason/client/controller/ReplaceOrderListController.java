package com.jason.client.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jason.client.JavafxApplication;
import com.jason.client.config.APIConfig;
import com.jason.client.enums.HttpStatus;
import com.jason.client.enums.PaymentStatusEnum;
import com.jason.client.model.ReplaceOrder;
import com.jason.client.util.HttpUtil;
import com.jason.client.util.StringUtil;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

public class ReplaceOrderListController implements Initializable {

    @FXML
    public JFXComboBox<String> orderStatusSelection;
    @FXML
    private JFXTreeTableView<ReplaceOrder> replaceOrderList;
    @FXML
    private JFXTreeTableColumn<ReplaceOrder, String> orderId;
    @FXML
    private JFXTreeTableColumn<ReplaceOrder, String> shopName;
    @FXML
    private JFXTreeTableColumn<ReplaceOrder, String> orderAmount;
   @FXML
    private JFXTreeTableColumn<ReplaceOrder, String> orderComission;
    @FXML
    private JFXTreeTableColumn<ReplaceOrder, String> orderStatus;
    @FXML
    private JFXTreeTableColumn<ReplaceOrder, String> replaceOrderId;

    @FXML
    private Label pageRecord;
    @FXML
    private JFXTextField searchName;
    @FXML
    private JFXTextField searchPage;

    @FXML
    private StackPane stackPane;

    private int current = 1;
    private int pages;

    private static int choosedOrderId = 0;
    private static ReplaceOrder user;

    @Override
    public void initialize(URL location1, ResourceBundle resources) {
        stackPane.setVisible(false);

        List<String> paymentStatusList = PaymentStatusEnum.getAllStatus();
        ObservableList<String> subUserItem = FXCollections.observableArrayList(paymentStatusList);
        orderStatusSelection.setItems(subUserItem);

        HashMap<String, String> paramMap = new HashMap<>();
        updateParamMap(paramMap);
        String data = getReplaceOrderList(paramMap);
        showOrderList(data);
        orderStatusSelection.setValue(PaymentStatusEnum.Unchoose.description);
    }

    private void showOrderList(String data){
        JSONObject jsonObject = JSONObject.parseObject(data);
        int status = jsonObject.getInteger("status");

        if (status != HttpStatus.OK.status){
            JavafxApplication.showAlert("温馨提示",jsonObject.getString("description"), null, null, "确定");
            return;
        }

        JSONObject recordObj = jsonObject.getJSONObject("obj");
        int total = recordObj.getInteger("total");
        pages = recordObj.getInteger("pages");
        current = recordObj.getInteger("current");
        String text = "第" + current + "/" + pages + "页，共" + total + "条记录";
        pageRecord.setText(text);

        setupCellValueFactory(orderId, ReplaceOrder::getOrderId);
        setupCellValueFactory(shopName, ReplaceOrder::getShopName);
        setupCellValueFactory(orderAmount, ReplaceOrder::getOrderAmount);
        setupCellValueFactory(orderComission, ReplaceOrder::getOrderComission);
        setupCellValueFactory(orderStatus, ReplaceOrder::getOrderStatus);
        setupCellValueFactory(replaceOrderId, ReplaceOrder::getReplaceOrderId);

        JSONArray jsonArray = recordObj.getJSONArray("records");
        ObservableList<ReplaceOrder> users = FXCollections.observableArrayList();
        for (int i=0;i<jsonArray.size();i++){
            JSONObject subUserJSON = jsonArray.getJSONObject(i);
            users.add(
                    new ReplaceOrder(subUserJSON.getString("orderId"),
                            subUserJSON.getString("shopName"), subUserJSON.getString("orderAmount"),
                            subUserJSON.getString("orderCommission"), subUserJSON.getString("paymentStatusStr"),
                            subUserJSON.getString("id")));
        }

        mouseClickedOnRow();

        replaceOrderList.setRoot(new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren));
        replaceOrderList.setShowRoot(false);
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<ReplaceOrder, T> column, Function<ReplaceOrder, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<ReplaceOrder, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    public void mouseClickedOnRow() {
        replaceOrderList.setOnMousePressed((MouseEvent event) -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                Node node = ((Node) event.getTarget()).getParent();
                JFXTreeTableRow row;
                if (node instanceof JFXTreeTableRow) {
                    row = (JFXTreeTableRow) node;
                } else {
                    // clicking on text part
                    row = (JFXTreeTableRow) node.getParent();
                }

                user = (ReplaceOrder) row.getItem();
                String orderStatus = user.getOrderStatus().get();
                if (PaymentStatusEnum.Unpaid.description.equals(orderStatus)) {
                    choosedOrderId = Integer.valueOf(user.getReplaceOrderId().get());
                    JavafxApplication.showConfirmed("操作提示", "确认已垫付该条补单记录吗？",
                            stackPane, "updateOrderStatus", getClass());
                }else{
                    JavafxApplication.showAlert("操作提示", "该条补单记录状态为：" + orderStatus + ",双击操作无效!", null, null, "确定");
                }
            }
        });
    }

    public static void updateOrderStatus() {
        String url = String.format(APIConfig.updateOrderUrl, choosedOrderId, 1);
        String data = HttpUtil.httpPost(new HashMap<>(), url, LoginController.jwt);

        JSONObject jsonObject = JSONObject.parseObject(data);

        JavafxApplication.showAlert("温馨提示",jsonObject.getString("description"), null, null, "确定");
        user.setOrderStatus(PaymentStatusEnum.Paid.description);
        //int status = jsonObject.getInteger("status");
        /*if (status != HttpStatus.OK.status){
        }else{
            JavafxApplication.showAlert("温馨提示",jsonObject.getString("description"), null, null, "确定");
        }*/
    }

    public void searchOrderList(ActionEvent actionEvent) {
        String text = searchName.getText();
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("searchName", text);
        updateParamMap(paramMap);
        String data = getReplaceOrderList(paramMap);
        showOrderList(data);
    }

    public void searchPage(ActionEvent actionEvent) {
        String text = searchPage.getText().trim();

        if (text.length() == 0){
            JavafxApplication.showAlert("温馨提示", "请先输入页码!", null, null, "确定");
            return;
        }

        if (!StringUtil.isNumeric(text)){
            JavafxApplication.showAlert("温馨提示", "页码只能为整数哦!", null, null, "确定");
            return;
        }

        int searchCurrent = Integer.parseInt(text);
        if(searchCurrent < 0 || searchCurrent > pages){
            JavafxApplication.showAlert("温馨提示", "页码范围为：1~" + pages, null, null, "确定");
            return;
        }

        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("searchCurrent", text);
        paramMap.put("searchName", searchName.getText());
        updateParamMap(paramMap);
        String data = getReplaceOrderList(paramMap);
        showOrderList(data);
    }

    public void refreshUserList(ActionEvent actionEvent) {
        searchName.setText("");
        searchPage.setText("");
        orderStatusSelection.setValue(PaymentStatusEnum.Unchoose.description);

        HashMap<String, String> paramMap = new HashMap<>();
        updateParamMap(paramMap);
        String data = getReplaceOrderList(paramMap);
        showOrderList(data);
    }

    public void previousPage(ActionEvent actionEvent) {
        if(current == 1){
            JavafxApplication.showAlert("温馨提示", "页码已达首页，翻不动啦！", null, null, "确定");
            return;
        }

        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("searchCurrent", String.valueOf(current - 1));
        paramMap.put("searchName", searchName.getText());
        updateParamMap(paramMap);
        String data = getReplaceOrderList(paramMap);
        showOrderList(data);
    }

    public void nextPage(ActionEvent actionEvent) {
        if(current == pages){
            JavafxApplication.showAlert("温馨提示", "页码已达尾页，翻不动啦！", null, null, "确定");
            return;
        }

        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("searchCurrent", String.valueOf(current + 1));
        paramMap.put("searchName", searchName.getText());
        updateParamMap(paramMap);
        String data = getReplaceOrderList(paramMap);
        showOrderList(data);
    }

    public String getReplaceOrderList(HashMap<String, String> paramMap){
        return HttpUtil.httpPost(paramMap, APIConfig.orderListUrl, LoginController.jwt);
    }

    public void updateParamMap(HashMap<String, String> paramMap){
        paramMap.put("searchUserId", LoginController.userId);

        String paymentStatus = orderStatusSelection.getValue();
        int status = PaymentStatusEnum.getPaymentStatus(paymentStatus);
        if(status != -1){
            paramMap.put("searchStatus", String.valueOf(status));
        }
    }
}
