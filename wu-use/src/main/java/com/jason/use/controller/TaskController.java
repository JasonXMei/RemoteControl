package com.jason.use.controller;

import com.jason.use.enums.TagTypeEnum;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TaskController implements Initializable {

    @FXML
    JFXComboBox subUserListForOrder;

    @FXML
    JFXComboBox shopListForOrder;

    @FXML
    JFXComboBox subUserListForTag;

    @FXML
    JFXComboBox shopListForTag;

    @FXML
    JFXComboBox tagTypeList;

    public static ObservableList<String> subUserItem;
    public static ObservableList<String> shopItem;
    public static ObservableList<String> tagTypeItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        subUserItem = FXCollections.observableArrayList(
                "Jason",
                "ABo",
                "Chris"
        );

        shopItem = FXCollections.observableArrayList(
                "shop1",
                "shop2"
        );

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
}

