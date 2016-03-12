package com.wukong.ttravel.service.order.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.Base.views.tagview.Tag;
import com.wukong.ttravel.Base.views.tagview.TagListView;
import com.wukong.ttravel.Base.views.tagview.TagView;
import com.wukong.ttravel.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wukong on 3/11/16.
 */
public class PostCommentActivity extends BaseActivity {

    private List<String> mList;
    private final String[] titles = {"怀念我的母校","家乡","和我最亲爱的朋友"};
    private final List<Tag> mTags = new ArrayList<Tag>();
    private int seletedStarLevel = 0;

    @Bind(R.id.tagview)
    TagListView tagListView;

    @Bind(R.id.comment_content)
    EditText commentContent;

    @Bind(R.id.star0)
    ImageView star0;
    @Bind(R.id.star1)
    ImageView star1;
    @Bind(R.id.star2)
    ImageView star2;
    @Bind(R.id.star3)
    ImageView star3;
    @Bind(R.id.star4)
    ImageView star4;

    @OnClick(R.id.star0)
    void OnStar0Click(View v) {
        seletecStarLevel(0);
    }
    @OnClick(R.id.star1)
    void OnStar1Click(View v) {
        seletecStarLevel(1);
    }
    @OnClick(R.id.star2)
    void OnStar2Click(View v) {
        seletecStarLevel(2);
    }
    @OnClick(R.id.star3)
    void OnStar3Click(View v) {
        seletecStarLevel(3);
    }
    @OnClick(R.id.star4)
    void OnStar4Click(View v) {
        seletecStarLevel(4);
    }

    @OnClick(R.id.do_comment_button)
    void OnFinishButtonClick(View v) {

        //先判断评论内容
        String string = commentContent.getText().toString();
        if (string.length() < 10) {
            showInfo("评论内容不能少于10个字哦~");
            return;
        }

        //获取星级
        System.out.println("start---level-->" + seletedStarLevel);

        //获取用户选择标签
        for (int i = 0; i < mTags.size();i ++) {
            Tag tag = mTags.get(i);
            if (tag.isChecked()) {
                System.out.println("tagId :" + tag.getTagId() + "\ntagName: " + tag.getTitle());
            }

        }

        ///TO DO 发起网络请求
    }

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

    void seletecStarLevel(int i) {
        switch (i) {
            case 0:
                star0.setImageResource(R.drawable.home_star_active);
                star1.setImageResource(R.drawable.home_star_inactive);
                star2.setImageResource(R.drawable.home_star_inactive);
                star3.setImageResource(R.drawable.home_star_inactive);
                star4.setImageResource(R.drawable.home_star_inactive);
                seletedStarLevel = 1;
                break;
            case 1:
                star0.setImageResource(R.drawable.home_star_active);
                star1.setImageResource(R.drawable.home_star_active);
                star2.setImageResource(R.drawable.home_star_inactive);
                star3.setImageResource(R.drawable.home_star_inactive);
                star4.setImageResource(R.drawable.home_star_inactive);
                seletedStarLevel = 2;
                break;
            case 2:
                star0.setImageResource(R.drawable.home_star_active);
                star1.setImageResource(R.drawable.home_star_active);
                star2.setImageResource(R.drawable.home_star_active);
                star3.setImageResource(R.drawable.home_star_inactive);
                star4.setImageResource(R.drawable.home_star_inactive);
                seletedStarLevel = 3;
                break;
            case 3:
                star0.setImageResource(R.drawable.home_star_active);
                star1.setImageResource(R.drawable.home_star_active);
                star2.setImageResource(R.drawable.home_star_active);
                star3.setImageResource(R.drawable.home_star_active);
                star4.setImageResource(R.drawable.home_star_inactive);
                seletedStarLevel = 4;
                break;
            case 4:
                star0.setImageResource(R.drawable.home_star_active);
                star1.setImageResource(R.drawable.home_star_active);
                star2.setImageResource(R.drawable.home_star_active);
                star3.setImageResource(R.drawable.home_star_active);
                star4.setImageResource(R.drawable.home_star_active);
                seletedStarLevel = 5;
                break;
            default:
                break;
        }

    }

}
