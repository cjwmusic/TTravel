package com.wukong.ttravel.service.home.model;

import com.alibaba.fastjson.JSONObject;


/**
 * Created by wukong on 2/19/16.
 */
public class TailorDetail {

    private String membId;
    private String membPhoto;
    private String membNickName;
    private String[] membAlbum;
    private boolean membHouse;
    private boolean membCar;
    private String membHomeTown;
    private String membOccupation;
    private String membExperience;
    private boolean isFavorites;
    private Integer membScore;
    private Integer evalCount;
    private String evalContent;
    private String evalCreate;


    public TailorDetail(JSONObject jsonObject) {

        membId = jsonObject.getString("MembID");
        membPhoto = jsonObject.getString("MembPhoto");
        membNickName = jsonObject.getString("MembNickname");

       // membAlbum 待处理
        membAlbum = jsonObject.getString("MembAlbum").split(",");

        membHouse = jsonObject.getBoolean("MembHouse");
        membCar = jsonObject.getBoolean("MembCar");
        membHomeTown = jsonObject.getString("MembHometown");
        membOccupation = jsonObject.getString("MembOccupation");
        membExperience = jsonObject.getString("MembExperience");

        isFavorites = jsonObject.getBoolean("IsFavorites");
        membScore = jsonObject.getInteger("MembScore");
        evalCount = jsonObject.getInteger("EvalCount");
        evalContent = jsonObject.getString("EvalContent");
        evalCreate = jsonObject.getString("EvalCreate");

    }

    public String getMembId() {
        return membId;
    }

    public void setMembId(String membId) {
        this.membId = membId;
    }

    public String getMembPhoto() {
        return membPhoto;
    }

    public void setMembPhoto(String membPhoto) {
        this.membPhoto = membPhoto;
    }

    public String getMembNickName() {
        return membNickName;
    }

    public void setMembNickName(String membNickName) {
        this.membNickName = membNickName;
    }

    public String[] getMembAlbum() {
        return membAlbum;
    }

    public void setMembAlbum(String[] membAlbum) {
        this.membAlbum = membAlbum;
    }

    public boolean isMembHouse() {
        return membHouse;
    }

    public void setMembHouse(boolean membHouse) {
        this.membHouse = membHouse;
    }

    public boolean isMembCar() {
        return membCar;
    }

    public void setMembCar(boolean membCar) {
        this.membCar = membCar;
    }

    public String getMembHomeTown() {
        return membHomeTown;
    }

    public void setMembHomeTown(String membHomeTown) {
        this.membHomeTown = membHomeTown;
    }

    public String getMembOccupation() {
        return membOccupation;
    }

    public void setMembOccupation(String membOccupation) {
        this.membOccupation = membOccupation;
    }

    public String getMembExperience() {
        return membExperience;
    }

    public void setMembExperience(String membExperience) {
        this.membExperience = membExperience;
    }

    public boolean isFavorites() {
        return isFavorites;
    }

    public void setIsFavorites(boolean isFavorites) {
        this.isFavorites = isFavorites;
    }

    public Integer getMembScore() {
        return membScore;
    }

    public void setMembScore(Integer membScore) {
        this.membScore = membScore;
    }

    public Integer getEvalCount() {
        return evalCount;
    }

    public void setEvalCount(Integer evalCount) {
        this.evalCount = evalCount;
    }

    public String getEvalContent() {
        return evalContent;
    }

    public void setEvalContent(String evalContent) {
        this.evalContent = evalContent;
    }

    public String getEvalCreate() {
        return evalCreate;
    }

    public void setEvalCreate(String evalCreate) {
        evalCreate = evalCreate;
    }
}
