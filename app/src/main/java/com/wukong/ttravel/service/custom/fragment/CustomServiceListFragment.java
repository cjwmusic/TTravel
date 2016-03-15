package com.wukong.ttravel.service.custom.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.Base.BaseFragment;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.Helper;
import com.wukong.ttravel.Utils.MessageUtils;
import com.wukong.ttravel.service.custom.adapter.CustomServiceListAdapter;
import com.wukong.ttravel.service.custom.model.CustomServiceItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wukong on 3/15/16.
 */
public class CustomServiceListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private int status;

    private View rootView;
    private String title;

    private CustomServiceListAdapter adapter;
    private ArrayList<CustomServiceItem> listData;

    @Bind(R.id.listView)
    ListView listView;

    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    public static CustomServiceListFragment newInstance(String title,int status) {

        CustomServiceListFragment customServiceListFragment = new CustomServiceListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        customServiceListFragment.setArguments(bundle);
        customServiceListFragment.setTitle(title);
        customServiceListFragment.setStatus(status);
        return customServiceListFragment;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.custom_service_list,container,false);
            ButterKnife.bind(this, rootView);

            listData = new ArrayList<>();

            adapter = new CustomServiceListAdapter(getActivity(),listData);
            listView.setAdapter(adapter);

            mSwipeRefreshLayout.setOnRefreshListener(this);

//            showLoading("正在加载...");
            showProgressDialog("正在加载...");
            loadData();

        }

        return rootView;
    }

    private void loadData() {

        HttpClient.post("Tailor/GetProductList", getParams(), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {

//                dismissSvHud();
                hideProgressDialog();
                listData.clear();
                mSwipeRefreshLayout.setRefreshing(false);

                JSONObject data = (JSONObject) obj;
                JSONArray jsonArray = (JSONArray) data.get("List");

                for (int i = 0; i< jsonArray.size(); i++) {
                    CustomServiceItem item = new CustomServiceItem((JSONObject)jsonArray.get(i));
                    item.setType(status);
                    listData.add(item);
                }
                if (jsonArray.size() > 0) {
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFail(HttpError error) {
                mSwipeRefreshLayout.setRefreshing(false);
                MessageUtils.showToastCenter(error.getMessage());

            }
        });

    }

    private JSONObject getParams() {
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strTailorID", Helper.sharedHelper().getUserId());
            params.put("intStatus", status);

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
