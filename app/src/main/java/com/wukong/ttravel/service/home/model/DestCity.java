package com.wukong.ttravel.service.home.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wukong on 2/18/16.
 */
public class DestCity {

    private String destId;
    private String destCityName;
    private String destCityPinyin;
    private String destCityPicture;
    private boolean isFirst;

    public DestCity(JSONObject jsonObject) {


        destId = jsonObject.getString("DestID");
        destCityName = jsonObject.getString("DestCityName");
        destCityPinyin = jsonObject.getString("DestCityPinyin");
        destCityPicture = jsonObject.getString("DestCityPicture");

    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public String getDestId() {
        return destId;
    }

    public void setDestId(String destId) {
        this.destId = destId;
    }

    public String getDestCityName() {
        return destCityName;
    }

    public void setDestCityName(String destCityName) {
        this.destCityName = destCityName;
    }

    public String getDestCityPinyin() {
        return destCityPinyin;
    }

    public void setDestCityPinyin(String destCityPinyin) {
        this.destCityPinyin = destCityPinyin;
    }

    public String getDestCityPicture() {
        return destCityPicture;
    }

    public void setDestCityPicture(String destCityPicture) {
        this.destCityPicture = destCityPicture;
    }
}
