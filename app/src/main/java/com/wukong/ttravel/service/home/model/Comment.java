package com.wukong.ttravel.service.home.model;

import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;

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

        //时间转换
        commentTime = jsonObject.getString("EvalCreate");
        int startIndex =  commentTime.indexOf("(");
        int endIndex = commentTime.indexOf(")");
        String subString = commentTime.substring(startIndex + 1, endIndex);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        commentTime = sdf.format(Long.parseLong(subString));

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
