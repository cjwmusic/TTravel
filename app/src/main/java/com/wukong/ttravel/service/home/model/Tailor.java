package com.wukong.ttravel.service.home.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wukong on 2/18/16.
 */
public class Tailor {

    public enum HomeItemTypeEnum {

        CityList,
        TailorList;

    }

    public Tailor(JSONObject jsonObject) {
        membID = jsonObject.getString("MembID");
        membPhoto = "http://apptt.traveltailor.cn" + jsonObject.getString("MembPhoto");
        membAlbum = "http://apptt.traveltailor.cn" + jsonObject.getString("MembAlbum");
        membNickName = jsonObject.getString("MembNickname");
        prodName = jsonObject.getString("ProdName");
        prodMinPrice = jsonObject.getString("ProdMinPrice");
        prodMaxPrice = jsonObject.getString("ProdMaxPrice");
        prodDate = jsonObject.getString("ProdDate");
        membOrder = jsonObject.getInteger("MembOrder") + "";
        membScore = jsonObject.getInteger("MembScore");

    }

    private String membID;
    private String membPhoto;
    private String membAlbum;
    private String membNickName;
    private String prodName;
    private String prodMaxPrice;
    private String prodMinPrice;
    private String prodDate;
    private String membOrder;
    private Integer membScore;

    private HomeItemTypeEnum type;

    public String getMembID() {
        return membID;
    }

    public void setMembID(String membID) {
        this.membID = membID;
    }

    public String getMembPhoto() {
        return membPhoto;
    }

    public void setMembPhoto(String membPhoto) {
        this.membPhoto = membPhoto;
    }

    public String getMembAlbum() {
        return membAlbum;
    }

    public void setMembAlbum(String membAlbum) {
        this.membAlbum = membAlbum;
    }

    public String getMembNickName() {
        return membNickName;
    }

    public void setMembNickName(String membNickName) {
        this.membNickName = membNickName;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdMaxPrice() {
        return prodMaxPrice;
    }

    public void setProdMaxPrice(String prodMaxPrice) {
        this.prodMaxPrice = prodMaxPrice;
    }

    public String getProdMinPrice() {
        return prodMinPrice;
    }

    public void setProdMinPrice(String prodMinPrice) {
        this.prodMinPrice = prodMinPrice;
    }

    public String getProdDate() {
        return prodDate;
    }

    public void setProdDate(String prodDate) {
        this.prodDate = prodDate;
    }

    public String getMembOrder() {
        return membOrder;
    }

    public void setMembOrder(String membOrder) {
        this.membOrder = membOrder;
    }

    public Integer getMembScore() {
        return membScore;
    }

    public void setMembScore(Integer membScore) {
        this.membScore = membScore;
    }

    public HomeItemTypeEnum getType() {
        return type;
    }

    public void setType(HomeItemTypeEnum type) {
        this.type = type;
    }
}
