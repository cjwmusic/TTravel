package com.wukong.ttravel.service.discover.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wukong on 2/21/16.
 */
public class DiscoverItem {

    private String founId;
    private String founTitle;
    private String founPicture;
    private String founTailor;

    public DiscoverItem(JSONObject jsonObject) {

        founId = jsonObject.getString("FounID");
        founTitle = jsonObject.getString("FounTitle");
        founPicture = jsonObject.getString("FounPicture");
        founTailor = jsonObject.getString("FounTailor");

    }

    public String getFounId() {
        return founId;
    }

    public void setFounId(String founId) {
        this.founId = founId;
    }

    public String getFounTitle() {
        return founTitle;
    }

    public void setFounTitle(String founTitle) {
        this.founTitle = founTitle;
    }

    public String getFounPicture() {
        return founPicture;
    }

    public void setFounPicture(String founPicture) {
        this.founPicture = founPicture;
    }

    public String getFounTailor() {
        return founTailor;
    }

    public void setFounTailor(String founTailor) {
        this.founTailor = founTailor;
    }
}
