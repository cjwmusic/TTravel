package com.wukong.ttravel.service.message.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.Base.BaseFragment;
import com.wukong.ttravel.Base.Router.Router;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.Helper;
import com.wukong.ttravel.service.home.model.Tailor;
import com.wukong.ttravel.service.message.adapter.ContactListAdapter;
import com.wukong.ttravel.service.message.model.TTContact;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wukong on 3/10/16.
 */
public class ContactListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private ContactListAdapter adapter;
    private ArrayList listData;


    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_contact_list, container, false);

            ButterKnife.bind(this, rootView);

            //初始化ListView

            listData = new ArrayList<>();
            adapter = new ContactListAdapter(getActivity(),listData);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //点击的是伴客item
                    if (listData.get(position).getClass().equals(Tailor.class)) {
                        Tailor tailor = (Tailor) listData.get(position);
//                        Router.sharedRouter().open("tailorIndex/" + tailor.getMembID());

                        Router.sharedRouter().open("chat/" + "20160119001");
                    } else { //点击的是城市item

                    }
                }
            });

            //下拉刷新
//            mSwipeRefreshLayout.setVisibility(View.GONE);
//            mSwipeRefreshLayout.setEnabled(false);
            mSwipeRefreshLayout.setOnRefreshListener(this);
            showLoading("正在加载...");
            //请求数据
            loadData();
        }
        return rootView;

    }


    private void loadData() {

        HttpClient.post("Member/GetLinkmanList", getParams(0), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                dismissSvHud();
                mSwipeRefreshLayout.setRefreshing(false);
                listData.clear();

                JSONObject data = (JSONObject) obj;
                JSONArray jsonArray = (JSONArray) data.get("List");
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    TTContact contact = new TTContact(jsonObject);
                    listData.add(contact);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(HttpError error) {
                mSwipeRefreshLayout.setRefreshing(false);
                showError(error.getMessage());
            }
        });
    }

    @Override
    public void onRefresh() {
        loadData();
    }


    private JSONObject getParams(int page) {

        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strMemberID", Helper.sharedHelper().getUserId());
            params.put("intCount", 10);
            params.put("intBegin", page * 10);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }
}
