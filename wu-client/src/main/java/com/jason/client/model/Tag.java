package com.jason.client.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tag extends RecursiveTreeObject<Tag> {

    private StringProperty tagType;
    private StringProperty subUserName;
    private StringProperty shopName;
    private StringProperty tagDescription;

    public Tag(String tagType, String subUserName, String shopName, String tagDescription) {
        this.tagType = new SimpleStringProperty(tagType);
        this.subUserName = new SimpleStringProperty(subUserName);
        this.shopName = new SimpleStringProperty(shopName);
        this.tagDescription = new SimpleStringProperty(tagDescription);
    }

    public String getTagType() {
        return tagType.get();
    }

    public StringProperty tagTypeProperty() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType.set(tagType);
    }

    public String getSubUserName() {
        return subUserName.get();
    }

    public StringProperty subUserNameProperty() {
        return subUserName;
    }

    public void setSubUserName(String subUserName) {
        this.subUserName.set(subUserName);
    }

    public String getShopName() {
        return shopName.get();
    }

    public StringProperty shopNameProperty() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName.set(shopName);
    }

    public String getTagDescription() {
        return tagDescription.get();
    }

    public StringProperty tagDescriptionProperty() {
        return tagDescription;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription.set(tagDescription);
    }
}
