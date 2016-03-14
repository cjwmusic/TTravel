package com.wukong.ttravel.service.my.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.Router.Router;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.Helper;
import com.wukong.ttravel.service.home.model.DestCity;
import com.wukong.ttravel.service.my.adapter.MeHelpAdapter;
import com.wukong.ttravel.service.my.model.TTHelpItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by wukong on 3/14/16.
 */
public class MeHelpActivity extends BaseActivity{

    private MeHelpAdapter adapter;
    private ArrayList<TTHelpItem> listData;

    @Bind(R.id.listView)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_help);
        ButterKnife.bind(this);

        listData = new ArrayList<>();
        adapter = new MeHelpAdapter(this,listData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TTHelpItem helpItem = listData.get(position);
                Router.sharedRouter().open("about/" + helpItem.getHelpID()  + "/" + helpItem.getHelpTitle());
            }
        });

        loadData();
    }

    void loadData() {

        showLoading("正在加载...");
        HttpClient.post("Traveler/GetTravelerHelp", getParams(), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                dismissSvHud();

                JSONObject data = (JSONObject)obj;
                JSONArray jsonArray = (JSONArray)data.get("List");

                for (int i = 0; i < jsonArray.size();i ++) {
                    JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                    TTHelpItem helpItem = new TTHelpItem(jsonObject);
                    listData.add(helpItem);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(HttpError error) {
                showError(error.getMessage());
            }
        });
    }


    private JSONObject getParams() {
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("intBegin", 0);
            params.put("intCount", 20);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }
}
