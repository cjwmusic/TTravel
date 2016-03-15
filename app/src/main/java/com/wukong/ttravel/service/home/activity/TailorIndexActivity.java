package com.wukong.ttravel.service.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.Router.Router;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.Base.views.MyListView;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.CommonUtil;
import com.wukong.ttravel.Utils.ImgUtil;
import com.wukong.ttravel.service.home.adapter.TailorLinesListAdapter;
import com.wukong.ttravel.service.home.model.TailorDetail;
import com.wukong.ttravel.service.home.model.TailorLine;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wukong on 2/19/16.
 */
public class TailorIndexActivity extends BaseActivity{

    private String tailorId;

    @Bind(R.id.album_image)
    SimpleDraweeView albumImageView;

    @Bind(R.id.nick_name)
    TextView nickName;

    @Bind(R.id.carImg)
    ImageView carImageView;

    @Bind(R.id.houseImg)
    ImageView houseImageView;

    @Bind(R.id.image_avatar)
    SimpleDraweeView avatarImageView;

    @Bind(R.id.hometown)
    TextView homeTown;

    @Bind(R.id.job)
    TextView job;

    @Bind(R.id.konw_me)
    TextView konwMe;

    //starts
    @Bind(R.id.stars)
    LinearLayout stars;

    @Bind(R.id.start0)
    ImageView start0;
    @Bind(R.id.start1)
    ImageView start1;
    @Bind(R.id.start2)
    ImageView start2;
    @Bind(R.id.start3)
    ImageView start3;
    @Bind(R.id.start4)
    ImageView start4;

    @Bind(R.id.comment_count)
    TextView commentCount;

    @Bind(R.id.comment_txt)
    TextView commentText;

    //全部评论的Button
    @Bind(R.id.comment_button)
    Button commentButton;

    //可预约时间cell
    @Bind(R.id.book_time_button)
    RelativeLayout bookTimeButton;

    @Bind(R.id.linesList)
    MyListView linesListView;

    private TailorLinesListAdapter adapter;
    private ArrayList<TailorLine> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tailor_main);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        tailorId = extras.getString("id");

        showLoading("正在加载");
        loadData();

        initLines();
    }

    void initLines() {
        listData = new ArrayList<>();
        adapter = new TailorLinesListAdapter(this,listData);
        linesListView.setAdapter(adapter);
        loadLinesData();
    }

    private void loadData() {

        HttpClient.post("Traveler/GetTailor", getParams(tailorId), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                dismissSvHud();
                JSONObject data = (JSONObject) obj;
                JSONObject model = data.getJSONObject("Model");
                if (model != null) {
                    TailorDetail tailorDetail = new TailorDetail(model);
                    updateUI(tailorDetail);
                }
            }

            @Override
            public void onFail(HttpError error) {

            }
        });
    }

    private void loadLinesData() {

        HttpClient.post("Traveler/GetTailorProduct", getParams(tailorId), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                JSONObject data = (JSONObject) obj;
                JSONArray listArray = data.getJSONArray("List");
                if (listArray != null && listArray.size() > 0) {
                    for (int i = 0;i < listArray.size(); i++) {
                        TailorLine line = new TailorLine(listArray.getJSONObject(i));
                        line.setProdLineNumber("线路" + CommonUtil.AybTochinese(i + 1));
                        listData.add(line);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(HttpError error) {
                showError(error.getMessage());
            }
        });

    }

    private JSONObject getParams(String tailorId) {

        //查询已经登录的客户编码
        String userId = "";

        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strTailorID", tailorId);
//            params.put("strTravelerID", userId);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }

    private void updateUI(TailorDetail tailorDetail) {

        albumImageView.setImageURI(ImgUtil.getCDNUrlWithPathStr(tailorDetail.getMembAlbum()[0]));
        avatarImageView.setImageURI(ImgUtil.getCDNUrlWithPathStr(tailorDetail.getMembPhoto()));

        nickName.setText(tailorDetail.getMembNickName());
        homeTown.setText(tailorDetail.getMembHomeTown());
        job.setText(tailorDetail.getMembOccupation());
        konwMe.setText(tailorDetail.getMembExperience());
        commentCount.setText(tailorDetail.getEvalCount() + "条评论");

        if (tailorDetail.getEvalCount() > 0) {
            commentText.setVisibility(View.VISIBLE);
            commentButton.setVisibility(View.VISIBLE);
            commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Router.sharedRouter().open("commentList/" + tailorId);
                }
            });
        } else {
            commentText.setHeight(10);
            commentButton.setVisibility(View.GONE);
        }

        commentText.setText(tailorDetail.getEvalContent());

        if (tailorDetail.isMembCar()) {
            carImageView.setVisibility(View.VISIBLE);
        } else {
            carImageView.setVisibility(View.GONE);
        }

        if (tailorDetail.isMembHouse()) {
            houseImageView.setVisibility(View.VISIBLE);
        } else {
            houseImageView.setVisibility(View.GONE);
        }
    }


    /**
     * 返回键点击事件
     * @param view
     */
    public void handleBack(View view) {
        onBackPressed();
    }


}
