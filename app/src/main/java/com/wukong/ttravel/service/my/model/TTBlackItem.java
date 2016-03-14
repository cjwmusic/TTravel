package com.wukong.ttravel.service.my.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by wukong on 3/15/16.
 */
public class TTBlackItem {
    String userAvatar;
    String userNick;

    public TTBlackItem(JSONObject jsonObject) {
        ///TO DO
        userNick = jsonObject.getString("TailorNickname");
        userAvatar = jsonObject.getString("TailorPhoto");

    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }
}
