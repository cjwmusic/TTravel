package com.wukong.ttravel.Base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.wukong.ttravel.Base.request.HttpClient;

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


    }

    public static TTApplication getInstance() {
        return context;
    }
}
