package com.jason.client.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jason.client.JavafxApplication;
import com.jason.client.config.APIConfig;
import com.jason.client.enums.HttpStatus;
import com.jason.client.model.Tag;
import com.jason.client.util.HttpUtil;
import com.jason.client.util.StringUtil;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.function.Function;

public class TagController implements Initializable {

    @FXML
    private JFXTreeTableView<Tag> tagList;
    @FXML
    private JFXTreeTableColumn<Tag, String> tagType;
    @FXML
    private JFXTreeTableColumn<Tag, String> subUserName;
    @FXML
    private JFXTreeTableColumn<Tag, String> shopName;
    @FXML
    private JFXTreeTableColumn<Tag, String> tagDescription;

    @FXML
    private Label pageRecord;
    @FXML
    private JFXTextField searchPage;
    private int current = 1;
    private int pages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String data = getTagList();
        showTagList(data);
    }

    private String getTagList(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("searchUserId", LoginController.userId);
        hashMap.put("searchCurrent", String.valueOf(current));
        String data = HttpUtil.httpPost(hashMap, APIConfig.tagListUrl, LoginController.jwt);
        return data;
    }

    private void showTagList(String data){
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

        setupCellValueFactory(tagType, Tag::tagTypeProperty);
        setupCellValueFactory(subUserName, Tag::subUserNameProperty);
        setupCellValueFactory(shopName, Tag::shopNameProperty);
        setupCellValueFactory(tagDescription, Tag::tagDescriptionProperty);

        JSONArray jsonArray = recordObj.getJSONArray("records");
        ObservableList<Tag> tags = FXCollections.observableArrayList();
        for (int i=0;i<jsonArray.size();i++){
            JSONObject tagJSON = jsonArray.getJSONObject(i);
            tags.add(
                    new Tag(tagJSON.getString("tagTypeStr"), tagJSON.getString("subUserName"),
                            tagJSON.getString("shopName"), tagJSON.getString("description")));
        }

        tagList.setRoot(new RecursiveTreeItem<>(tags, RecursiveTreeObject::getChildren));
        tagList.setShowRoot(false);
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<Tag, T> column, Function<Tag, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<Tag, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
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

        current = searchCurrent;
        String data = getTagList();
        showTagList(data);
    }

    public void previousPage(ActionEvent actionEvent) {
        if(current == 1){
            JavafxApplication.showAlert("温馨提示", "页码已达首页，翻不动啦！", null, null, "确定");
            return;
        }

        current -= 1;
        String data = getTagList();
        showTagList(data);
    }

    public void nextPage(ActionEvent actionEvent) {
        if(current == pages){
            JavafxApplication.showAlert("温馨提示", "页码已达尾页，翻不动啦！", null, null, "确定");
            return;
        }

        current += 1;
        String data = getTagList();
        showTagList(data);
    }
}
