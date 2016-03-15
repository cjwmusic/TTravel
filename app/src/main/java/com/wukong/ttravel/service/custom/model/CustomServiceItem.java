package com.wukong.ttravel.service.custom.model;

import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.service.custom.fragment.CustomServiceFragment;

/**
 * Created by wukong on 3/15/16.
 */
public class CustomServiceItem {

    private String prodId;
    private String prodName;
    private String prodPicture;
    private String prodPrice;
    private String prodDescription;
    private String prodStatus;
    private String updateTime;
    private String unitText;

    private Boolean isHideRightButton1;
    private Boolean isHideRightButton2;

    private String rightButton1Text;
    private String rightButton2Text;

    private int type;


    public CustomServiceItem () {

    }

    public CustomServiceItem (JSONObject jsonObject) {
        prodId = jsonObject.getString("ProdID");
        prodName = jsonObject.getString("ProdName");
        prodPicture = jsonObject.getString("ProdPicture");
        prodPrice = jsonObject.getInteger("ProdPrice") + "";
        prodDescription = jsonObject.getString("ProdDescription");
        updateTime = "";
        unitText = "";
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;

        if (type == 0) {
            prodStatus = "待审核";
            setIsHideRightButton1(true);
            setIsHideRightButton2(true);
        } else if (type == 1) {
            prodStatus = "已上架";

            isHideRightButton1 = false;
            isHideRightButton2 = false;
            rightButton1Text = "编辑";
            rightButton2Text = "下架";
        } else if (type == -1) {
            prodStatus = "已下架";

            isHideRightButton1 = false;
            isHideRightButton2 = true;
            rightButton1Text = "上架";
            rightButton2Text = "";
        }
    }

    public String getRightButton1Text() {
        return rightButton1Text;
    }

    public void setRightButton1Text(String rightButton1Text) {
        this.rightButton1Text = rightButton1Text;
    }

    public String getRightButton2Text() {
        return rightButton2Text;
    }

    public void setRightButton2Text(String rightButton2Text) {
        this.rightButton2Text = rightButton2Text;
    }

    public Boolean isHideRightButton1() {
        return isHideRightButton1;
    }

    public void setIsHideRightButton1(Boolean isHideRightButton1) {
        this.isHideRightButton1 = isHideRightButton1;
    }

    public Boolean isHideRightButton2() {
        return isHideRightButton2;
    }

    public void setIsHideRightButton2(Boolean isHideRightButton2) {
        this.isHideRightButton2 = isHideRightButton2;
    }

    public String getUnitText() {
        return unitText;
    }

    public void setUnitText(String unitText) {
        this.unitText = unitText;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getProdStatus() {
        return prodStatus;
    }

    public void setProdStatus(String prodStatus) {
        this.prodStatus = prodStatus;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
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

    public String getProdDescription() {
        return prodDescription;
    }

    public void setProdDescription(String prodDescription) {
        this.prodDescription = prodDescription;
    }
}
