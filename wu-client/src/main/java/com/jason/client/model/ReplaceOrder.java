package com.jason.client.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReplaceOrder extends RecursiveTreeObject<ReplaceOrder> {

    private StringProperty orderId;
    private StringProperty shopName;
    private StringProperty subUserName;
    private StringProperty orderAmount;
    private StringProperty orderComission;
    private StringProperty orderStatus;
    private StringProperty replaceOrderId;

    public ReplaceOrder(String orderId, String shopName, String subUserName, String orderAmount, String orderComission, String orderStatus, String replaceOrderId) {
        this.orderId =  new SimpleStringProperty(orderId);
        this.shopName = new SimpleStringProperty(shopName);
        this.subUserName = new SimpleStringProperty(subUserName);
        this.orderAmount = new SimpleStringProperty(orderAmount);
        this.orderComission = new SimpleStringProperty(orderComission);
        this.orderStatus = new SimpleStringProperty(orderStatus);
        this.replaceOrderId = new SimpleStringProperty(replaceOrderId);
    }

    public StringProperty getOrderId() {
        return orderId;
    }

    public StringProperty orderIdProperty() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId.set(orderId);
    }

    public StringProperty getShopName() {
        return shopName;
    }

    public StringProperty shopNameProperty() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName.set(shopName);
    }

    public StringProperty getSubUserName() {
        return subUserName;
    }

    public StringProperty subUserNameProperty() {
        return subUserName;
    }

    public void setSubUserName(String subUserName) {
        this.subUserName.set(subUserName);
    }

    public StringProperty getOrderAmount() {
        return orderAmount;
    }

    public StringProperty orderAmountProperty() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount.set(orderAmount);
    }

    public StringProperty getOrderComission() {
        return orderComission;
    }

    public StringProperty orderComissionProperty() {
        return orderComission;
    }

    public void setOrderComission(String orderComission) {
        this.orderComission.set(orderComission);
    }

    public StringProperty getOrderStatus() {
        return orderStatus;
    }

    public StringProperty orderStatusProperty() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus.set(orderStatus);
    }

    public StringProperty getReplaceOrderId() {
        return replaceOrderId;
    }

    public StringProperty replaceOrderIdProperty() {
        return replaceOrderId;
    }

    public void setReplaceOrderId(String replaceOrderId) {
        this.replaceOrderId.set(replaceOrderId);
    }
}
