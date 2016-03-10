package com.wukong.ttravel.service.message.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wukong on 3/10/16.
 */
public class TTContact {


    private String linkId;
    private String linkLinkMan;
    private Integer linkNoRead;
    private String linkModify;
    private String linkManNickName;
    private String linkManPhoto;



    private String latestMessage;


    public TTContact(JSONObject jsonObject) {

        linkId = jsonObject.getString("LinkID");
        linkLinkMan = jsonObject.getString("LinkLinkman");
        linkNoRead = jsonObject.getInteger("LinkNoRead");
        linkModify = jsonObject.getString("LinkModify");
        linkManNickName = jsonObject.getString("LinkmanNickname");
        linkManPhoto = jsonObject.getString("LinkmanPhoto");

    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }


    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getLinkLinkMan() {
        return linkLinkMan;
    }

    public void setLinkLinkMan(String linkLinkMan) {
        this.linkLinkMan = linkLinkMan;
    }

    public Integer getLinkNoRead() {
        return linkNoRead;
    }

    public void setLinkNoRead(Integer linkNoRead) {
        this.linkNoRead = linkNoRead;
    }

    public String getLinkModify() {
        return linkModify;
    }

    public void setLinkModify(String linkModify) {
        this.linkModify = linkModify;
    }

    public String getLinkManNickName() {
        return linkManNickName;
    }

    public void setLinkManNickName(String linkManNickName) {
        this.linkManNickName = linkManNickName;
    }

    public String getLinkManPhoto() {
        return linkManPhoto;
    }

    public void setLinkManPhoto(String linkManPhoto) {
        this.linkManPhoto = linkManPhoto;
    }
}
