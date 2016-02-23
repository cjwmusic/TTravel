package com.wukong.ttravel.service.order.fragment;

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
import com.wukong.ttravel.service.order.adapter.OrderListAdapter;
import com.wukong.ttravel.service.order.model.Order;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wukong on 2/23/16.
 */
public class OrderListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{


    private String strTailorID = "20160106004";

    private View rootView;
    private String title;

    private OrderListAdapter adapter;
    private ArrayList<Order> listData;

    private String myId = "12345";

    @Bind(R.id.listView)
    ListView listView;

    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    public static OrderListFragment newInstance(String title,String strTailorID) {

        OrderListFragment orderListFragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        orderListFragment.setArguments(bundle);
        orderListFragment.setTitle(title);
        orderListFragment.setStrTailorID(strTailorID);
        return orderListFragment;
    }

    public void setStrTailorID(String strTailorID) {
        this.strTailorID = strTailorID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.order_list,container,false);
            ButterKnife.bind(this,rootView);

            listData = new ArrayList<>();
            adapter = new OrderListAdapter(getActivity(),listData);

            listView.setAdapter(adapter);

            mSwipeRefreshLayout.setOnRefreshListener(this);
            showProgressDialog("正在加载...");
            loadData();

        }

        return rootView;
    }

    private void loadData() {

        HttpClient.post("Traveler/GetEvaluationToTailor", getParams(), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {

                listData.clear();

                mSwipeRefreshLayout.setRefreshing(false);
                hideProgressDialog();

                JSONObject data = (JSONObject) obj;
                JSONArray jsonArray = (JSONArray) data.get("List");
                for (int i = 0; i < 3; i++) {
                    Order order = new Order();
                    order.setOrderCreate("2016-02-24 01:19");
                    order.setOrderStatus("已完成");
                    order.setProductName("带着你游遍整个庆阳");
                    order.setTravelerPhoto("/UP2016/01/p_1E3101627.png");
                    order.setTravelerNickName("西北帅气少年");

                    listData.add(order);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(HttpError error) {
                mSwipeRefreshLayout.setRefreshing(false);
                hideProgressDialog();
            }
        });

    }

    private JSONObject getParams() {
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strTailorID", strTailorID);

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
