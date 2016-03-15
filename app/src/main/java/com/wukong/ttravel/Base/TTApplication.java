package com.wukong.ttravel.Base;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.wukong.ttravel.Base.Router.Router;
import com.wukong.ttravel.Base.im.AVImClientManager;
import com.wukong.ttravel.Base.im.MessageHandler;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Utils.Helper;
import com.wukong.ttravel.service.discover.activity.DiscoverDetailActivity;
import com.wukong.ttravel.service.home.activity.CommentListActivity;
import com.wukong.ttravel.service.home.activity.TailorIndexActivity;
import com.wukong.ttravel.service.login.activity.LoginActivity;
import com.wukong.ttravel.service.login.activity.ResetPasswordActivity;
import com.wukong.ttravel.service.message.activity.ChatActivity;
import com.wukong.ttravel.service.my.activity.TTAboutActivity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wukong on 2/18/16.
 */
public class TTApplication  extends Application{

    private static TTApplication context;

    private AVIMConversation imConversation;


    private int currentUserType = 1;

    public int getCurrentUserType() {
        return currentUserType;
    }

    public void setCurrentUserType(int currentUserType) {
        this.currentUserType = currentUserType;
    }

    public AVIMConversation getImConversation() {
        return imConversation;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        HttpClient.init();

        // 设置context
        Helper.sharedHelper().setContext(getApplicationContext());

        Fresco.initialize(context);

        setupRouter();

        //初始化LeanCloud  初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "SriaHIEpzaoFOAr2Ill3KtKp-gzGzoHsz", "HTJXD449o4igTeIPKKL3OYv2");

        // 必须在启动的时候注册 MessageHandler
        // 应用一启动就会重连，服务器会推送离线消息过来，需要 MessageHandler 来处理
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new MessageHandler(this));
        AVIMClient.setMessageQueryCacheEnable(true);

        //登录IM
        AVImClientManager.getInstance().open("20160128001", new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {

                System.out.println("IM登录成功");

                //发送消息
                final AVIMClient client = AVImClientManager.getInstance().getClient();
                AVIMConversationQuery conversationQuery = client.getQuery();
                conversationQuery.withMembers(Arrays.asList("20160119001"), true);
                conversationQuery.whereEqualTo("customConversationType",1);
                conversationQuery.findInBackground(new AVIMConversationQueryCallback() {
                    @Override
                    public void done(List<AVIMConversation> list, AVIMException e) {
                        if (filterException(e)) {
                            //注意：此处仍有漏洞，如果获取了多个 conversation，默认取第一个
                            if (null != list && list.size() > 0) {
//                                chatFragment.setConversation(list.get(0));
                                sendMessage(list.get(0),"我通过android发消息给你了");
                                imConversation = list.get(0);

                            } else {
                                HashMap<String,Object> attributes = new HashMap<String, Object>();
                                attributes.put("customConversationType", 1);
                                client.createConversation(Arrays.asList("20160128001"), null, attributes, false, new AVIMConversationCreatedCallback() {
                                    @Override
                                    public void done(AVIMConversation avimConversation, AVIMException e) {
                                        sendMessage(avimConversation,"我通过android发消息给你了");
                                        imConversation = avimConversation;
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });




    }

    //通过IM发送消息
    private void sendMessage(AVIMConversation imConversation, String msg) {

//        AVIMTextMessage message = new AVIMTextMessage();
//        message.setText(msg);
//        imConversation.sendMessage(message, new AVIMConversationCallback() {
//            @Override
//            public void done(AVIMException e) {
//                System.out.println("发送消息成功");
//            }
//        });
    }

    protected boolean filterException(Exception e) {
        if (e != null) {
            e.printStackTrace();
            toast(e.getMessage());
            return false;
        } else {
            return true;
        }
    }

    protected void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


    private void setupRouter() {

        Router.sharedRouter().setContext(getApplicationContext());
        /**
         * Maps
         */
        Router.sharedRouter().map("tailorIndex/:id", TailorIndexActivity.class);
        Router.sharedRouter().map("commentList/:id", CommentListActivity.class);
        Router.sharedRouter().map("discoverDetail/:id/:title", DiscoverDetailActivity.class);
        Router.sharedRouter().map("about/:id/:title", TTAboutActivity.class);

        Router.sharedRouter().map("doLogin", LoginActivity.class);
        Router.sharedRouter().map("resetPassword", ResetPasswordActivity.class);

        //聊天界面
        Router.sharedRouter().map("chat/:receiverId", ChatActivity.class);

    }

    public static TTApplication getInstance() {
        return context;
    }
}
