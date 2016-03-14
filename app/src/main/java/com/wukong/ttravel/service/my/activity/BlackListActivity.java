package com.wukong.ttravel.service.my.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.Helper;
import com.wukong.ttravel.service.my.adapter.BlackListAdapter;
import com.wukong.ttravel.service.my.model.TTBlackItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wukong on 3/15/16.
 */
public class BlackListActivity extends BaseActivity {

    private BlackListAdapter adapter;
    private ArrayList<TTBlackItem> listData;

    @Bind(R.id.listView)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_list);
        ButterKnife.bind(this);

        listData = new ArrayList<>();
        adapter = new BlackListAdapter(this,listData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        loadData();
    }

    void loadData() {

        showLoading("正在加载...");
        HttpClient.post("Traveler/GetTravelerPrevent", getParams(), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                dismissSvHud();

                listData.clear();

                JSONObject data = (JSONObject) obj;
                JSONArray jsonArray = (JSONArray) data.get("List");

                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    TTBlackItem item = new TTBlackItem(jsonObject);
                    listData.add(item);


//                    TTHelpItem helpItem = new TTHelpItem(jsonObject);
//                    listData.add(helpItem);

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
            params.put("strTravelerID", Helper.sharedHelper().getUserId());
        } catch (Exception e) {
            params = null;
        }
        return params;
    }
}
