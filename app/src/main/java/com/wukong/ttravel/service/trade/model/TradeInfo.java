package com.wukong.ttravel.service.trade.model;

/**
 * Created by wukong on 3/16/16.
 */
public class TradeInfo {

    private String strTravelerID; //游客编码
    private String strTailorID;//伴客编码
    private String strProductID;//产品编码
    private String datBegin;//开始时间
    private String datEnd;//结束时间
    private String decPrice;//单价
    private String intPeople;//游客人数
    private String decMoney;//订单金额 =  decPrice * intDays * intPeople
    private String strLinkman;//联系人
    private String strPhone;//联系电话

    public String getStrTravelerID() {
        return strTravelerID;
    }

    public void setStrTravelerID(String strTravelerID) {
        this.strTravelerID = strTravelerID;
    }

    public String getStrTailorID() {
        return strTailorID;
    }

    public void setStrTailorID(String strTailorID) {
        this.strTailorID = strTailorID;
    }

    public String getStrProductID() {
        return strProductID;
    }

    public void setStrProductID(String strProductID) {
        this.strProductID = strProductID;
    }

    public String getDatBegin() {
        return datBegin;
    }

    public void setDatBegin(String datBegin) {
        this.datBegin = datBegin;
    }

    public String getDatEnd() {
        return datEnd;
    }

    public void setDatEnd(String datEnd) {
        this.datEnd = datEnd;
    }

    public String getDecPrice() {
        return decPrice;
    }

    public void setDecPrice(String decPrice) {
        this.decPrice = decPrice;
    }

    public String getIntPeople() {
        return intPeople;
    }

    public void setIntPeople(String intPeople) {
        this.intPeople = intPeople;
    }

    public String getDecMoney() {
        return decMoney;
    }

    public void setDecMoney(String decMoney) {
        this.decMoney = decMoney;
    }

    public String getStrLinkman() {
        return strLinkman;
    }

    public void setStrLinkman(String strLinkman) {
        this.strLinkman = strLinkman;
    }

    public String getStrPhone() {
        return strPhone;
    }

    public void setStrPhone(String strPhone) {
        this.strPhone = strPhone;
    }
}
