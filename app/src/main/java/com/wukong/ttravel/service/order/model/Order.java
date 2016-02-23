package com.wukong.ttravel.service.order.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wukong on 2/24/16.
 */
public class Order {

    private String orderId;
    private String orderTraveler;
    private String orderCreate;
    private String orderStatus;
    private String travelerNickName;
    private String travelerPhoto;
    private String productName;

    public Order() {


    }

    public Order(JSONObject jsonObject) {

        orderId = jsonObject.getString("OrdeID");
        orderTraveler = jsonObject.getString("OrdeTraveler");
        orderCreate = jsonObject.getString("OrdeCreate");
        orderStatus = jsonObject.getString("OrdeStatus");
        travelerNickName = jsonObject.getString("TravelerNickname");
        travelerPhoto = jsonObject.getString("TravelerPhoto");
        productName = jsonObject.getString("ProductName");

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTraveler() {
        return orderTraveler;
    }

    public void setOrderTraveler(String orderTraveler) {
        this.orderTraveler = orderTraveler;
    }

    public String getOrderCreate() {
        return orderCreate;
    }

    public void setOrderCreate(String orderCreate) {
        this.orderCreate = orderCreate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTravelerNickName() {
        return travelerNickName;
    }

    public void setTravelerNickName(String travelerNickName) {
        this.travelerNickName = travelerNickName;
    }

    public String getTravelerPhoto() {
        return travelerPhoto;
    }

    public void setTravelerPhoto(String travelerPhoto) {
        this.travelerPhoto = travelerPhoto;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
