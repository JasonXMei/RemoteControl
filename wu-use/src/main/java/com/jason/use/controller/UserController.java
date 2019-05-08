package com.jason.use.controller;

import com.jason.use.JavafxApplication;
import com.jason.use.model.User;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

public class UserController implements Initializable {

    @FXML
    private JFXTreeTableView<User> userList;
    @FXML
    private JFXTreeTableColumn<User, Integer> id;
    @FXML
    private JFXTreeTableColumn<User, String> userName;
    @FXML
    private JFXTreeTableColumn<User, String> subUserName;
    @FXML
    private JFXTreeTableColumn<User, String> replaceOrderTimes;
   @FXML
    private JFXTreeTableColumn<User, String> location;
    @FXML
    private JFXTreeTableColumn<User, String> connectionStatus;

    private final JFXDialogLayout DIALOG_LAYOUT = new JFXDialogLayout();

    private final TextFlow MESSAGE_FLOW = new TextFlow();

    private JFXDialog alertView;

    @FXML
    private StackPane stackPane;

    @Override
    public void initialize(URL location1, ResourceBundle resources) {
        stackPane.setVisible(false);

        setupCellValueFactory(id, p -> p.getId().asObject());
        setupCellValueFactory(userName, User::getUserName);
        setupCellValueFactory(subUserName, User::getSubUserName);
        setupCellValueFactory(replaceOrderTimes, User::getReplaceOrderTimes);
        setupCellValueFactory(location, User::getLocation);
        setupCellValueFactory(connectionStatus, User::getConnectionStatus);

        ObservableList<User> users = FXCollections.observableArrayList();
        users.add(new User(1,"jason", "j1", "1-10","四川 成都 移动", "可以连接"));
        users.add(new User(2,"chris", "c1", "3-3","cd", "可以连接"));
        users.add(new User(3,"lee", "l1", "1-2","hk", "被连接"));
        users.add(new User(4,"sara", "s1", "2-4","cd", "可以连接"));
        users.add(new User(5,"jason", "j1", "1-5","cd", "可以连接"));
        users.add(new User(6,"chris", "c1", "4-4","cd", "可以连接"));
        users.add(new User(7,"lee", "l1", "5-6","hk", "被连接"));
        users.add(new User(8,"sara", "s1", "2-5","cd", "可以连接"));
        users.add(new User(9,"jason", "j1", "1-4","cd", "可以连接"));
        users.add(new User(10,"chris", "c1", "6-6","cd", "未上线"));

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
                JavafxApplication.showConfirmed("操作提示", user.getUserName().getValue() + ":" + user.getSubUserName().getValue(),
                        stackPane, "testMethod", getClass());
                /*AdminAddUserViewController.selectedUser = user;
                AdminAddUserViewController.edit = true;
                try {
                    MainApp.switchView("/Views/Admin/AdminAddUserView.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            }
        });
    }

    public static void testMethod() {
        System.out.println("test");
    }
}
