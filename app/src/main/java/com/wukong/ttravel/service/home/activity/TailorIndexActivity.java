package com.wukong.ttravel.service.home.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.Base.views.MyListView;
import com.wukong.ttravel.R;
import com.wukong.ttravel.service.home.model.TailorDetail;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wukong on 2/19/16.
 */
public class TailorIndexActivity extends Activity{

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

    @Bind(R.id.linesList)
    MyListView linesListView;

    //全部评论的Button
    @Bind(R.id.comment_button)
    Button commentButton;

    //可预约时间cell
    @Bind(R.id.book_time_button)
    RelativeLayout bookTimeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tailor_main);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        tailorId = extras.getString("id");



        loadData();
    }

    private void loadData() {

        HttpClient.post("Traveler/GetTailor", getParams(tailorId), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
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

        albumImageView.setImageURI(Uri.parse("http://img1.ph.126.net/CUw1qGDq-BbSK9KGhWhKLg==/3747557840026058971.jpg"));

        avatarImageView.setImageURI(Uri.parse("http://img1.ph.126.net/CUw1qGDq-BbSK9KGhWhKLg==/3747557840026058971.jpg"));
//        avatarImageView.setImageURI(Uri.parse("http://app.traveltailor.cn" + tailorDetail.getMembPhoto()));
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

                    System.out.println("wukong Debug " + "点击了全部评论");

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
