package com.wukong.ttravel.service.discover.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wukong.ttravel.Base.BaseFragment;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.R;
import com.wukong.ttravel.service.discover.adapter.DiscoverAdapter;
import com.wukong.ttravel.service.discover.model.DiscoverItem;
import com.wukong.ttravel.service.home.model.DestCity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wukong on 2/21/16.
 */
public class DiscoverFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    private View rootView;
    private DiscoverAdapter adapter;
    private ArrayList<DiscoverItem> listData;

    @Bind(R.id.listView)
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_discover,container,false);
            ButterKnife.bind(this,rootView);

            listData = new ArrayList<>();
            adapter = new DiscoverAdapter(getActivity(),listData);
            listView.setAdapter(adapter);

            loadData();

        }

        return rootView;

    }

    private void loadData(){

        HttpClient.post("Traveler/GetFoundList", getParams(0), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                JSONObject data = (JSONObject)obj;
                JSONArray jsonArray = (JSONArray)data.get("List");

                for (int i = 0; i < jsonArray.size();i ++) {
                    JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                    DiscoverItem discoverItem = new DiscoverItem(jsonObject);
                    listData.add(discoverItem);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(HttpError error) {

            }
        });

    }

    private JSONObject getParams(int page) {
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("intCount", 10);
            params.put("intBegin", page * 10);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }



    @Override
    public void onRefresh() {

    }
}