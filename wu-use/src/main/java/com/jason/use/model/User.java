package com.jason.use.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User extends RecursiveTreeObject<User> {
    private StringProperty id;
    private SimpleIntegerProperty subUserId;
    private SimpleIntegerProperty age;
    private StringProperty userName;
    private StringProperty subUserName;
    private StringProperty sex;
    private StringProperty replaceOrderTimes;
    private StringProperty location;
    private StringProperty connectionStatus;

    public User(String id, int subUserId, int age, String userName, String subUserName, String sex, String replaceOrderTimes, String location, String connectionStatus) {
        this.id = new SimpleStringProperty(id);
        this.subUserId = new SimpleIntegerProperty(subUserId);
        this.age = new SimpleIntegerProperty(age);
        this.userName = new SimpleStringProperty(userName);
        this.subUserName = new SimpleStringProperty(subUserName);
        this.sex = new SimpleStringProperty(sex);
        this.replaceOrderTimes = new SimpleStringProperty(replaceOrderTimes);
        this.location = new SimpleStringProperty(location);
        this.connectionStatus = new SimpleStringProperty(connectionStatus);
    }

    public StringProperty getId() {
        return id;
    }

    public void setId(StringProperty id) {
        this.id = id;
    }

    public SimpleIntegerProperty getSubUserId() {
        return subUserId;
    }

    public void setSubUserId(SimpleIntegerProperty subUserId) {
        this.subUserId = subUserId;
    }

    public SimpleIntegerProperty getAge() {
        return age;
    }

    public void setAge(SimpleIntegerProperty age) {
        this.age = age;
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

    public StringProperty getSex() {
        return sex;
    }

    public void setSex(StringProperty sex) {
        this.sex = sex;
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
