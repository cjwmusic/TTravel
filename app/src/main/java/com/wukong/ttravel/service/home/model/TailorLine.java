package com.wukong.ttravel.service.home.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by wukong on 3/15/16.
 */
public class TailorLine implements Serializable {

    private String prodID;
    private String prodName;
    private String prodPicture;
    private String prodPrice;
    private String prodLineNumber;
    private boolean selected;

    public TailorLine(JSONObject jsonObject) {
        prodID = jsonObject.getString("ProdID");
        prodName = jsonObject.getString("ProdName");
        prodPicture = jsonObject.getString("ProdPicture");
        prodPrice = jsonObject.getString("ProdPrice");
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getProdID() {
        return prodID;
    }

    public void setProdID(String prodID) {
        this.prodID = prodID;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdPicture() {
        return prodPicture;
    }

    public void setProdPicture(String prodPicture) {
        this.prodPicture = prodPicture;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getProdLineNumber() {
        return prodLineNumber;
    }

    public void setProdLineNumber(String prodLineNumber) {
        this.prodLineNumber = prodLineNumber;
    }
}
