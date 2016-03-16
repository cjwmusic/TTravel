package com.wukong.ttravel.service.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.Router.Router;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.Base.views.tagview.Tag;
import com.wukong.ttravel.Base.views.tagview.TagListView;
import com.wukong.ttravel.R;
import com.wukong.ttravel.service.home.adapter.TailorsAdapter;
import com.wukong.ttravel.service.home.model.Tailor;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wukong on 3/16/16.
 */
public class CityTailorListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.action_bar_title)
    TextView actionBarTitleTv;

    @Bind(R.id.listView)
    ListView listView;

    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.popup_view)
    RelativeLayout popupView;

    @OnClick(R.id.popup_view)
    void onPoppupViewClick(View v) {
        togglePopupView();
    }

    @Bind(R.id.sex_tags)
    TagListView tagSexView;

    @Bind(R.id.m_tags)
    TagListView mTagsView;


    private TailorsAdapter adapter;
    private ArrayList<Tailor> listData;

    private String cityId;
    private String cityName;
    private int sex = -1;
    private ArrayList<String> tagsStr;
    private ArrayList<Tag>  sexTags;
    private ArrayList<Tag>  mTags;

    @OnClick(R.id.action_bar_button_right)
    void onClickFindPeopleButton(View v) {
        togglePopupView();
    }

    @OnClick(R.id.ok_button)
    void onClickOkButton(View v) {
        loadSpecifyData();
    }


    void togglePopupView() {
        int vis = popupView.getVisibility();
        if (vis == View.GONE) {
            popupView.setVisibility(View.VISIBLE);
        } else {
            popupView.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_tailor_list);
        ButterKnife.bind(this);

        //获取传递过来的城市编码
        Intent intent = getIntent();
        cityId = intent.getExtras().getString("id");
        cityName = intent.getExtras().getString("title");

        actionBarTitleTv.setText(cityName);

        listData = new ArrayList<>();
        adapter = new TailorsAdapter(this,listData);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tailor tailor = (Tailor) listData.get(position);
                Router.sharedRouter().open("tailorIndex/" + tailor.getMembID());
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(this);
        loadData();

        initPopupView();

    }

    private void initPopupView() {

        //初始化性别选择
        sexTags = new ArrayList<>();
        Tag sexTagM = new Tag();
        sexTagM.setTagId("1"); //男
        sexTagM.setTitle("男");
        sexTags.add(sexTagM);

        Tag sexTagF = new Tag();
        sexTagF.setTagId("0");
        sexTagF.setTitle("女");
        sexTags.add(sexTagF);

        tagSexView.setTags(sexTags, true);

        //初始化标签选择
        mTags = new ArrayList<>();
        //从网络加载标签数据
        loadTags();

    }

    void loadTags() {

        HttpClient.post("Traveler/GetLabelList", getTagsParams(), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                JSONObject data = (JSONObject) obj;
                JSONArray jsonArray = (JSONArray) data.get("List");
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        Tag tag = new Tag();
                        tag.setTagId(jsonObject.getString("LablID"));
                        tag.setTitle(jsonObject.getString("LablName"));
                        mTags.add(tag);
                    }
                    mTagsView.setTags(mTags, true);
                }
            }

            @Override
            public void onFail(HttpError error) {
                showError(error.getMessage());
            }
        });

    }

    void loadData() {
        showLoading("正在加载");
        HttpClient.post("Traveler/GetTailorList", getParams(0), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {

                dismissSvHud();
                listData.clear();

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
                showError(error.getMessage());
            }
        });


    }

    void loadSpecifyData() {
        //得到用户选择的数据
        //1 性别


    }

    private JSONObject getParams(int page) {
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strDestinationID",cityId);

            if (sex != -1) {
                params.put("intSex", sex);
            }

            if (tagsStr != null && tagsStr.size() > 0) {
                params.put("strLableIDs", tagsStr);
            }

            params.put("intCount", 20);
            params.put("intBegin", page * 20);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }

    private JSONObject getTagsParams() {
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("", "");

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
