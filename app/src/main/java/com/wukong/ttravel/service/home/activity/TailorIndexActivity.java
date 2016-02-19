package com.wukong.ttravel.service.home.activity;

import android.app.Activity;
import android.os.Bundle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.R;

import java.util.Objects;

import butterknife.ButterKnife;

/**
 * Created by wukong on 2/19/16.
 */
public class TailorIndexActivity extends Activity{



    private String tailorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tailor_main);

        tailorId = "20151201002";
        ButterKnife.bind(this);
        loadData();

    }

    private void loadData() {

        HttpClient.post("Traveler/GetTailor", getParams(tailorId), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                JSONObject data = (JSONObject)obj;


            }

            @Override
            public void onFail(HttpError error) {

            }
        });


    }


    private JSONObject getParams(String tailorId) {

        //查询已经登录的客户编码
        String userId = "";

        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strTailorID", tailorId);
//            params.put("strTravelerID", userId);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }

}
