package com.wukong.ttravel.service.order.activity;

import android.os.Bundle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.Base.views.tagview.Tag;
import com.wukong.ttravel.Base.views.tagview.TagListView;
import com.wukong.ttravel.Base.views.tagview.TagView;
import com.wukong.ttravel.R;
import com.wukong.ttravel.service.home.model.Comment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wukong on 3/11/16.
 */
public class PostCommentActivity extends BaseActivity {

    private List<String> mList;
    private final String[] titles = {"怀念我的母校","家乡","和我最亲爱的朋友"};
    private final List<Tag> mTags = new ArrayList<Tag>();

    @Bind(R.id.tagview)
    TagListView tagListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comment);
        ButterKnife.bind(this);

        loadData();

        tagListView.setOnTagClickListener(new TagListView.OnTagClickListener() {
            @Override
            public void onTagClick(TagView tagView, Tag tag) {
                System.out.println("tag id is " + tag.getTagId() + tag.getTitle() + "---isSeleted:" + tag.isChecked());
            }
        });

    }
    void loadData() {

        showLoading("正在获取标签数据");

        HttpClient.post("Traveler/GetLabelList", getParams(), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                dismissSvHud();
                JSONObject data = (JSONObject) obj;
                JSONArray jsonArray = (JSONArray) data.get("List");
                if (jsonArray != null) {
                    setUpData(jsonArray);
                    tagListView.setTags(mTags, true);
                }
            }

            @Override
            public void onFail(HttpError error) {
                showError(error.getMessage());
            }
        });

    }

    private void setUpData(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            Tag tag = new Tag();
            tag.setTagId(jsonObject.getString("LablID"));
            tag.setTitle(jsonObject.getString("LablName"));
            mTags.add(tag);
        }
    }

    private JSONObject getParams() {
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("", "");

        } catch (Exception e) {
            params = null;
        }
        return params;
    }

}
