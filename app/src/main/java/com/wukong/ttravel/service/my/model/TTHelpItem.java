package com.wukong.ttravel.service.my.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wukong on 3/15/16.
 */
public class TTHelpItem {

    private String helpID;
    private String helpTitle;

    public TTHelpItem(JSONObject jsonObject) {
        helpID = jsonObject.getString("HelpID");
        helpTitle = jsonObject.getString("HelpTitle");
    }

    public String getHelpID() {
        return helpID;
    }

    public void setHelpID(String helpID) {
        this.helpID = helpID;
    }

    public String getHelpTitle() {
        return helpTitle;
    }

    public void setHelpTitle(String helpTitle) {
        this.helpTitle = helpTitle;
    }
}
