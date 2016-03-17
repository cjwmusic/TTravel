package com.wukong.ttravel.service.my.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by wukong on 3/15/16.
 */
public class TTBlackItem {
    String userAvatar;
    String userNick;
    String userId;
    String linkId;

    public TTBlackItem(JSONObject jsonObject) {
        ///TO DO
        userNick = jsonObject.getString("TailorNickname");
        userAvatar = jsonObject.getString("TailorPhoto");
        userId = jsonObject.getString("LinkTailor");
        linkId = jsonObject.getString("LinkID");

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }
}
