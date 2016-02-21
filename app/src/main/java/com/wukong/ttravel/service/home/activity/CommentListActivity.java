package com.wukong.ttravel.service.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.R;
import com.wukong.ttravel.service.home.adapter.CommentListAdapter;
import com.wukong.ttravel.service.home.model.Comment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wukong on 2/20/16.
 */
public class CommentListActivity extends BaseActivity {

    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;

    @Bind(R.id.comment_list)
    ListView listView;

    private ArrayList<Comment> listData;
    private CommentListAdapter adapter;
    private String strTailorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        strTailorID = extras.getString("id");

        actionBarTitle.setText("全部评价");

        listData = new ArrayList<Comment>();
        adapter = new CommentListAdapter(this,listData);
        listView.setAdapter(adapter);

        showProgressDialog("正在加载...");

        loadData();
    }

    private void loadData() {

        HttpClient.post("Traveler/GetEvaluationToTailor", getParams(), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {

                hideProgressDialog();

                JSONObject data = (JSONObject) obj;
                JSONArray jsonArray = (JSONArray) data.get("List");
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    Comment comment = new Comment(jsonObject);
                    listData.add(comment);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(HttpError error) {
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

    public void handleBack(View view) {
        onBackPressed();
    }


}

