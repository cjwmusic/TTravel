package com.wukong.ttravel.service.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.Base.BaseFragment;
import com.wukong.ttravel.Base.Router.Router;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.R;
import com.wukong.ttravel.service.home.adapter.TailorsAdapter;
import com.wukong.ttravel.service.home.model.DestCity;
import com.wukong.ttravel.service.home.model.Tailor;


import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wukong on 16/2/17.
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private View listFooter;
    private TextView footerMessage;
    private ProgressBar footerLoading;
    private Button footerReload;

    private ArrayList listData;
    private TailorsAdapter adapter;

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);

            ButterKnife.bind(this,rootView);

            //初始化ListView
            listFooter = getLayoutInflater(savedInstanceState).inflate(R.layout.view_list_footer, null);
            footerLoading = (ProgressBar) listFooter.findViewById(R.id.loading);
            footerMessage = (TextView) listFooter.findViewById(R.id.message);
            footerReload = (Button) listFooter.findViewById(R.id.footerReload);

            listView.addFooterView(listFooter);
            listFooter.setVisibility(View.GONE);

            listData = new ArrayList<>();
            adapter = new TailorsAdapter(getActivity(),listData);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //点击的是伴客item
                    if (listData.get(position).getClass().equals(Tailor.class)) {
                        Tailor tailor = (Tailor) listData.get(position);
                        Router.sharedRouter().open("tailorIndex/" + tailor.getMembID());
//                        Router.sharedRouter().open("chat/" + "20160119001");
                    } else { //点击的是城市item
                        DestCity city = (DestCity)listData.get(position);
                        Router.sharedRouter().open("cityTailorList/" + city.getDestId() + "/" + city.getDestCityName());
                    }
                }
            });

            //下拉刷新
//            mSwipeRefreshLayout.setVisibility(View.GONE);
//            mSwipeRefreshLayout.setEnabled(false);
            mSwipeRefreshLayout.setOnRefreshListener(this);
            //请求数据
            loadData();
        }
        return rootView;
    }


    private void loadData() {

        showLoading("正在加载...");
        HttpClient.post("Traveler/GetHotDestination", getParams(0), null, new HttpClient.HttpCallback<Object>() {

            @Override
            public void onSuccess(Object obj) {

                listData.clear();
                dismissSvHud();;
                JSONObject data = (JSONObject)obj;

                JSONArray jsonArray = (JSONArray)data.get("List");

                for (int i = 0; i < jsonArray.size();i ++) {
                    JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                    DestCity destCity = new DestCity(jsonObject);

                    if (i == 0) {
                        destCity.setIsFirst(true);
                    }

                    listData.add(destCity);
                }

                HttpClient.post("Traveler/GetTailorList", getParams(0), null, new HttpClient.HttpCallback<Object>() {
                    @Override
                    public void onSuccess(Object obj) {

                        hideProgressDialog();

                        JSONObject data = (JSONObject)obj;

                        JSONArray jsonArray = (JSONArray)data.get("List");

                        for (int i = 0; i < jsonArray.size();i ++) {
                            JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                            Tailor tailor = new Tailor(jsonObject);
                            listData.add(tailor);
                        }

                        //刷新listView
                        adapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);

                    }

                    @Override
                    public void onFail(HttpError error) {

                        hideProgressDialog();

                        mSwipeRefreshLayout.setRefreshing(false);

                        //刷新listView
//                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFail(HttpError error) {

                mSwipeRefreshLayout.setRefreshing(false);

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


    @Override
    public void onRefresh() {
        loadData();
    }
}
