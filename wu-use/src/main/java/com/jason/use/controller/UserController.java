package com.jason.use.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jason.use.JavafxApplication;
import com.jason.use.config.APIConfig;
import com.jason.use.enums.HttpStatus;
import com.jason.use.enums.SexEnum;
import com.jason.use.model.User;
import com.jason.use.util.HttpUtil;
import com.jason.use.util.StringUtil;
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

public class UserController implements Initializable {

    @FXML
    private JFXTreeTableView<User> userList;
    @FXML
    private JFXTreeTableColumn<User, String> id;
    @FXML
    private JFXTreeTableColumn<User, String> userName;
    @FXML
    private JFXTreeTableColumn<User, String> subUserName;
    @FXML
    private JFXTreeTableColumn<User, String> userSex;
    @FXML
    private JFXTreeTableColumn<User, String> replaceOrderTimes;
    @FXML
    private JFXTreeTableColumn<User, String> location;
    @FXML
    private JFXTreeTableColumn<User, String> connectionStatus;
    @FXML
    private JFXTreeTableColumn<User, String> desc;
    @FXML
    private JFXTreeTableColumn<User, Integer> subUserId;
    @FXML
    private JFXTreeTableColumn<User, Integer> age;
    @FXML
    public JFXComboBox<String> userSexCombo;
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

    @Override
    public void initialize(URL location1, ResourceBundle resources) {
        stackPane.setVisible(false);

        List<String> sexList = SexEnum.getAllStatus();
        ObservableList<String> sexItem = FXCollections.observableArrayList(sexList);
        userSexCombo.setItems(sexItem);
        userSexCombo.setValue(SexEnum.Unchoose.description);

        String data = HttpUtil.httpPost(new HashMap<>(), APIConfig.subUserListUrl, LoginController.jwt);
        showUserList(data);
    }

    private void showUserList(String data){
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

        setupCellValueFactory(id, User::getId);
        setupCellValueFactory(age, p -> p.getAge().asObject());
        setupCellValueFactory(userName, User::getUserName);
        setupCellValueFactory(subUserName, User::getSubUserName);
        setupCellValueFactory(userSex, User::getSex);
        setupCellValueFactory(replaceOrderTimes, User::getReplaceOrderTimes);
        setupCellValueFactory(location, User::getLocation);
        setupCellValueFactory(connectionStatus, User::getConnectionStatus);
        setupCellValueFactory(desc, User::getDesc);
        setupCellValueFactory(subUserId, p -> p.getSubUserId().asObject());

        JSONArray jsonArray = recordObj.getJSONArray("records");
        ObservableList<User> users = FXCollections.observableArrayList();
        for (int i=0;i<jsonArray.size();i++){
            JSONObject subUserJSON = jsonArray.getJSONObject(i);
            Integer userId = subUserJSON.getInteger("userId");
            String subUserName = subUserJSON.getString("subUserName");
            users.add(
                    new User(String.format("%04d", userId), subUserJSON.getInteger("id"), subUserJSON.getInteger("age"),
                            subUserJSON.getString("userName"), subUserName ,
                            subUserJSON.getString("sexStr"), subUserJSON.getString("orderTimes"),
                            subUserJSON.getString("location"), subUserJSON.getString("connectStatusStr"), subUserJSON.getString("desc")));
        }

        mouseClickedOnRow();

        userList.setRoot(new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren));
        userList.setShowRoot(false);
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<User, T> column, Function<User, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<User, T> param) -> {
            if (column.validateValue(param)) {
                /*if (column.getText().equals("日-日限制次数")){
                    String[] tempArr = param.getValue().getValue().getReplaceOrderTimes().getValue().split("-");
                    if (tempArr.length == 2){
                        if(tempArr[0].equals(tempArr[1])){
                            column.setStyle("-fx-background-color:red;");
                        }else{
                            column.setStyle("-fx-background-color:green;");
                        }
                    }
                }*/
                if (column.getText().equals("最后使用/店/号")) {
                    column.setStyle("-fx-font-size:12;");
                }
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    public void mouseClickedOnRow() {
        userList.setOnMousePressed((MouseEvent event) -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                Node node = ((Node) event.getTarget()).getParent();
                JFXTreeTableRow row;
                if (node instanceof JFXTreeTableRow) {
                    row = (JFXTreeTableRow) node;
                } else {
                    // clicking on text part
                    row = (JFXTreeTableRow) node.getParent();
                }

                User user = (User) row.getItem();
                /*String replaceOrderTimes = user.getReplaceOrderTimes().getValue();
                String[] timesArr = replaceOrderTimes.split("-");
                if (timesArr[0].equals(timesArr[1])) {
                    JavafxApplication.showAlert("操作提示", "小号：{" + user.getSubUserName().getValue() + "},当日补单次数已满!" , null, null, "确定");
                    return;
                }*/
                String userId = user.getId().get();

                String connectionStatus = user.getConnectionStatus().getValue();
                if(("可以连接").equals(connectionStatus)){
                    TaskController.userId = userId;
                    //TaskController.subUserId = String.valueOf(user.getSubUserId().getValue());
                    String apiResponse = HttpUtil.httpPost(new HashMap<>(), String.format(APIConfig.userOrderTimesUrl, userId), LoginController.jwt);
                    /*try {
                        apiResponse = new String(apiResponse.getBytes("iso-8859-1"), "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }*/
                    JavafxApplication.showConfirmed("操作提示", apiResponse, stackPane, "taskView", getClass());
                }else{
                    JavafxApplication.showAlert("操作提示", "用户：{" + user.getUserName().getValue() + "}," + connectionStatus, null, null, "确定");
                }
            }
        });
    }

    public static void taskView() {
        JavafxApplication.switchView("/view/task.fxml");
    }

    public void searchUserList(ActionEvent actionEvent) {
        HashMap<String, String> paramMap = new HashMap<>();
        updateParamMap(paramMap);
        String data = HttpUtil.httpPost(paramMap, APIConfig.subUserListUrl, LoginController.jwt);
        showUserList(data);
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
        updateParamMap(paramMap);
        String data = HttpUtil.httpPost(paramMap, APIConfig.subUserListUrl, LoginController.jwt);
        showUserList(data);
    }

    public void refreshUserList(ActionEvent actionEvent) {
        searchName.setText("");
        searchPage.setText("");
        userSexCombo.setValue(SexEnum.Unchoose.description);
        String data = HttpUtil.httpPost(new HashMap<>(), APIConfig.subUserListUrl, LoginController.jwt);
        showUserList(data);
    }

    public void previousPage(ActionEvent actionEvent) {
        if(current == 1){
            JavafxApplication.showAlert("温馨提示", "页码已达首页，翻不动啦！", null, null, "确定");
            return;
        }

        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("searchCurrent", String.valueOf(current - 1));
        updateParamMap(paramMap);
        String data = HttpUtil.httpPost(paramMap, APIConfig.subUserListUrl, LoginController.jwt);
        showUserList(data);
    }

    public void nextPage(ActionEvent actionEvent) {
        if(current == pages){
            JavafxApplication.showAlert("温馨提示", "页码已达尾页，翻不动啦！", null, null, "确定");
            return;
        }

        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("searchCurrent", String.valueOf(current + 1));
        updateParamMap(paramMap);
        String data = HttpUtil.httpPost(paramMap, APIConfig.subUserListUrl, LoginController.jwt);
        showUserList(data);
    }

    public void updateParamMap(HashMap<String, String> paramMap){
        paramMap.put("searchName", searchName.getText());
        String sex = userSexCombo.getValue();
        int status = SexEnum.getPaymentStatus(sex);
        if(status != -1){
            paramMap.put("searchStatus", String.valueOf(status));
        }
    }
}
