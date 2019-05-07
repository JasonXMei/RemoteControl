package com.jason.use.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User extends RecursiveTreeObject<User> {
    private SimpleIntegerProperty id;
    private StringProperty userName;
    private StringProperty subUserName;
    private StringProperty replaceOrderTimes;
    private StringProperty location;
    private StringProperty connectionStatus;

    public User(int id, String userName, String subUserName, String replaceOrderTimes, String location, String connectionStatus) {
        this.id = new SimpleIntegerProperty(id);
        this.userName = new SimpleStringProperty(userName);
        this.subUserName = new SimpleStringProperty(subUserName);
        this.replaceOrderTimes = new SimpleStringProperty(replaceOrderTimes);
        this.location = new SimpleStringProperty(location);
        this.connectionStatus = new SimpleStringProperty(connectionStatus);
    }

    public SimpleIntegerProperty getId() {
        return id;
    }

    public void setId(SimpleIntegerProperty id) {
        this.id = id;
    }

    public StringProperty getUserName() {
        return userName;
    }

    public void setUserName(StringProperty userName) {
        this.userName = userName;
    }

    public StringProperty getSubUserName() {
        return subUserName;
    }

    public void setSubUserName(StringProperty subUserName) {
        this.subUserName = subUserName;
    }

    public StringProperty getReplaceOrderTimes() {
        return replaceOrderTimes;
    }

    public void setReplaceOrderTimes(StringProperty replaceOrderTimes) {
        this.replaceOrderTimes = replaceOrderTimes;
    }

    public StringProperty getLocation() {
        return location;
    }

    public void setLocation(StringProperty location) {
        this.location = location;
    }

    public StringProperty getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(StringProperty connectionStatus) {
        this.connectionStatus = connectionStatus;
    }
}
