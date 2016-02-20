package com.wukong.ttravel.Base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.wukong.ttravel.Base.Router.Router;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.service.home.activity.CommentListActivity;
import com.wukong.ttravel.service.home.activity.TailorIndexActivity;

/**
 * Created by wukong on 2/18/16.
 */
public class TTApplication  extends Application{

    private static TTApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        HttpClient.init();

        Fresco.initialize(context);

        setupRouter();


    }

    private void setupRouter() {

        Router.sharedRouter().setContext(getApplicationContext());

        /**
         * Maps
         */
        Router.sharedRouter().map("tailorIndex/:id", TailorIndexActivity.class);
        Router.sharedRouter().map("commentList/:id", CommentListActivity.class);


    }

    public static TTApplication getInstance() {
        return context;
    }
}
