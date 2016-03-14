package com.wukong.ttravel.service.my.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wukong.ttravel.Base.BaseFragment;
import com.wukong.ttravel.Base.Router.Router;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.Helper;
import com.wukong.ttravel.Utils.ImgUtil;
import com.wukong.ttravel.service.my.activity.MeDetailActivity;
import com.wukong.ttravel.service.my.adapter.MeMenuAdapter;
import com.wukong.ttravel.service.my.model.TTMenuItem;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wukong on 3/11/16.
 */
public class MeFragment extends BaseFragment {

    private View rootView;
    private View listHeader;
    private MeMenuAdapter adapter;

    private ArrayList<TTMenuItem> listData;
    private SimpleDraweeView userAvatar;
    private TextView userNickName;


    @Bind(R.id.listView)
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_me, container, false);

            ButterKnife.bind(this, rootView);

            initListData();

            //初始化ListView
            listHeader = getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_me_header, null);
            listHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(),MeDetailActivity.class);
                    startActivity(intent);

                }
            });

            userAvatar = (SimpleDraweeView)listHeader.findViewById(R.id.image_avatar);
            userNickName = (TextView)listHeader.findViewById(R.id.nick_name);

            listView.addHeaderView(listHeader);
            listView.setHeaderDividersEnabled(false);
            listView.setDividerHeight(0);

            adapter = new MeMenuAdapter(getActivity(),listData);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            loadData();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 4) { //关于
                        Router.sharedRouter().open("about/" + "20151225901" + "/" + "关于");
                    }
                }
            });
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    void initListData() {
        listData = new ArrayList<>();
        TTMenuItem item0 = new TTMenuItem(0,"我的钱包", R.drawable.me_wallet);
        listData.add(item0);
        TTMenuItem item1 = new TTMenuItem(1,"我的评价", R.drawable.me_comment);
        listData.add(item1);
        TTMenuItem item2 = new TTMenuItem(2,"设置", R.drawable.me_setting);
        listData.add(item2);
        TTMenuItem item3 = new TTMenuItem(3,"关于", R.drawable.me_about);
        listData.add(item3);
        TTMenuItem item4 = new TTMenuItem(4,"帮助", R.drawable.me_help);
        listData.add(item4);
    }

    void loadData() {
        HttpClient.post("Traveler/GetTailor",getParams() ,null ,new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                JSONObject jsonObject = (JSONObject)obj;
                JSONObject userInfo = jsonObject.getJSONObject("Model");
                userAvatar.setImageURI(ImgUtil.getCDNUrlWithPathStr((String)userInfo.get("MembPhoto")));
                userNickName.setText((String)userInfo.get("MembNickname"));
            }

            @Override
            public void onFail(HttpError error) {

            }
        });

    }

    private JSONObject getParams() {
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strTailorID", Helper.sharedHelper().getUserId());
        } catch (Exception e) {
            params = null;
        }
        return params;
    }

}
