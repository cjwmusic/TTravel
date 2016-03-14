package com.wukong.ttravel.service.home.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wukong on 2/20/16.
 */
public class Comment {

    private String nickName;
    private String avatar;
    private String commentTime;
    private String commentContent;
    private Integer score;
    private String serviceName;

    public void setScore(Integer score) {
        this.score = score;
    }

    public Comment(JSONObject jsonObject) {
        nickName = jsonObject.getString("TravelerNickname");
        avatar = jsonObject.getString("TravelerPhoto");
        commentContent = jsonObject.getString("EvalContent");
        commentTime = jsonObject.getString("EvalCreate");
        score = jsonObject.getInteger("EvalScore");
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getScore() {
        return score;
    }
}
