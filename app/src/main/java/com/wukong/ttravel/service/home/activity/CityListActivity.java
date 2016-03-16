package com.wukong.ttravel.service.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.Router.Router;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.R;
import com.wukong.ttravel.service.home.adapter.CityListAdapter;
import com.wukong.ttravel.service.home.model.DestCity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wukong on 3/16/16.
 */
public class CityListActivity extends BaseActivity{

    @Bind(R.id.listView)
    ListView listView;

    private CityListAdapter adapter;
    private ArrayList<DestCity> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        ButterKnife.bind(this);

        listData = new ArrayList<>();
        adapter = new CityListAdapter(this,listData);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DestCity city = listData.get(position);
                Router.sharedRouter().open("cityTailorList/" + city.getDestId() + "/" + city.getDestCityName());
            }
        });

        loadData();
    }


    void loadData() {

        showLoading("正在加载...");
        HttpClient.post("Traveler/GetDestinationList", getParams(0), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {

                dismissSvHud();
                JSONObject data = (JSONObject)obj;
                JSONArray jsonArray = (JSONArray)data.get("List");
                listData.clear();

                for (int i = 0; i < jsonArray.size();i ++) {
                    JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                    DestCity destCity = new DestCity(jsonObject);

                    if (i == 0) {
                        destCity.setIsFirst(true);
                    }

                    listData.add(destCity);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(HttpError error) {
                showError(error.getMessage());
            }
        });

    }

    private JSONObject getParams(int page) {
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("intCount", 20);
            params.put("intBegin", page * 20);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }
}
