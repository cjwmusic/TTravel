package com.wukong.ttravel.service.my.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.Helper;
import com.wukong.ttravel.service.home.model.Comment;
import com.wukong.ttravel.service.my.adapter.MeCommentsAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wukong on 3/14/16.
 */
public class MeCommentActivity extends BaseActivity {


    @Bind(R.id.comment_score)
    TextView commentScore;
    @Bind(R.id.listView)
    ListView listView;

    private MeCommentsAdapter adapter;
    private ArrayList<Comment> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_comment);
        ButterKnife.bind(this);

        mData = new ArrayList<>();
        adapter = new MeCommentsAdapter(this,mData);

        loadData();
    }


    void loadData() {
        HttpClient.post("Tailor/GetTailorEvaluation", getParams(), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {

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
            params.put("strTailorID", Helper.sharedHelper().getCurrentUer().getUserId());
            params.put("intBegin",0);
            params.put("intCount",20);

        } catch (Exception e) {
            params = null;
        }
        return params;
    }

}
