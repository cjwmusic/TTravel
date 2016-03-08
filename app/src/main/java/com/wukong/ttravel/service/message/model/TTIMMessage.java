package com.wukong.ttravel.service.message.model;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by wukong on 16/3/7.
 */
public class TTIMMessage {

    /**
     * 文本消息
     */
    public static int MESSAGE_TYPE_TEXT = 1;
    /**
     * 图片消息
     */
    public static int MESSAGE_TYPE_IMG = 2;
    /**
     * 时间消息
     */
    public static int MESSAGE_TYPE_TIME = 3;
    /**
     * 语音消息
     */
    public static int MESSAGE_TYPE_VOICE = 4;


    private int type;  //消息类型
    private String sendUserId;      //发送方的userId
    private String sendUserAvatar;  //发送方的头像
    private String senderName;      //发送方的昵称
    private String image;           //发送方发的image
    private String text;            //发送方发的文本消息
    private long voiceTime;         //发送方发的语音信息的时长
    private String voiceUrl;        //发送方发的语音信息的url
    private AVIMMessage.AVIMMessageStatus sendStatus;             //消息的发送状态
    private Map<String, String> ext;  //消息扩展信息字段

    private AVIMTypedMessage message; //LeanMessage对象

    private Boolean loading = false;
    private Boolean retry = false;

    public TTIMMessage(AVIMMessage avMessage) {

        if (avMessage instanceof AVIMTextMessage) {
            AVIMTextMessage textMessage = (AVIMTextMessage)avMessage;
            type = MESSAGE_TYPE_TEXT;
            text = textMessage.getText();
            System.out.println("IM消息内容为---->" + textMessage.getText());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            String time = dateFormat.format(textMessage.getTimestamp());
            System.out.println("时间戳---->" + time);
            sendStatus = avMessage.getMessageStatus();


            Map attrs = textMessage.getAttrs();
            ext = attrs;
            if (ext != null) {
                senderName = (String)attrs.get("senderNickName");
                sendUserId = (String)attrs.get("senderId");
                sendUserAvatar = (String)attrs.get("senderAvatar");
            }

        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getSendUserAvatar() {
        return sendUserAvatar;
    }

    public void setSendUserAvatar(String sendUserAvatar) {
        this.sendUserAvatar = sendUserAvatar;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(long voiceTime) {
        this.voiceTime = voiceTime;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public Map<String, String> getExt() {
        return ext;
    }

    public void setExt(Map<String, String> ext) {
        this.ext = ext;
    }

    public AVIMTypedMessage getMessage() {
        return message;
    }

    public void setMessage(AVIMTypedMessage message) {
        this.message = message;
    }

    public Boolean getLoading() {
        return loading;
    }

    public void setLoading(Boolean loading) {
        this.loading = loading;
    }

    public Boolean getRetry() {
        return retry;
    }

    public void setRetry(Boolean retry) {
        this.retry = retry;
    }
}
