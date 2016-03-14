package com.wukong.ttravel.service.my.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.Helper;
import com.wukong.ttravel.Utils.ImgUtil;
import com.wukong.ttravel.service.login.model.TTUser;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wukong on 3/14/16.
 */
public class MeDetailActivity extends BaseActivity{

    //头像
    @Bind(R.id.me_avatar)
    SimpleDraweeView avatar;
    @OnClick(R.id.me_avatar_cell)
    void onClickAvatarCell(View v) {


    }
    //昵称
    @Bind(R.id.me_name)
    TextView nickName;
    @OnClick(R.id.me_name_cell)
    void onClickNameCell(View v) {

    }

    //职业
    @Bind(R.id.me_job)
    TextView job;
    @OnClick(R.id.me_job_cell)
    void onClickJobCell(View v) {

    }

    //家乡
    @Bind(R.id.me_home_town)
    TextView homeTown;
    @OnClick(R.id.me_home_town_cell)
    void onClickHometownCell(View v) {

    }

    //一句话了解我
    @Bind(R.id.me_onewords)
    TextView oneWords;
    @OnClick(R.id.me_onewords_cell)
    void onClickOneWordsCell(View v) {

    }

    //我的标签
    @OnClick(R.id.me_tags_cell)
    void onClickTagsCell(View v) {

    }

    //相册
    @OnClick(R.id.me_album_cell)
    void onClickAlbumCell(View v) {

    }

    //实名认证
    @Bind(R.id.me_realname)
    TextView realName;
    @Bind(R.id.me_realname_no_label)
    TextView nameNoCertLabel;
    @OnClick(R.id.me_realname_cert)
    void onClickRealNameCertCell(View v) {

    }

    //手机认证
    @Bind(R.id.me_phone)
    TextView phone;
    @Bind(R.id.me_phone_cert_no_label)
    TextView phoneNoCertLabel;
    @OnClick(R.id.me_phone_cert)
    void onClickPhoneCertCell(View v) {

    }

    //邮箱
    @Bind(R.id.me_email)
    TextView email;
    @Bind(R.id.me_email_no_label)
    TextView emailNoCertLabel;
    @OnClick(R.id.me_email_cert)
    void onClickEmailCertCell(View v) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_detail);
        ButterKnife.bind(this);
        loadData();


    }

    void loadData() {

        HttpClient.postLogin("Enter/PasswordLogin", getParams(), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                JSONObject data = (JSONObject) obj;
                showSuccess(data.getString("Message"));

                JSONObject userModel = data.getJSONObject("Model");
                TTUser user = new TTUser(userModel);
                updateUI(user);

            }

            @Override
            public void onFail(HttpError error) {
                showError(error.getMessage());
            }
        });

    }

    private JSONObject getParams() {
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strCode", Helper.sharedHelper().getCurrentUer().getUserId());
            params.put("strPassword", Helper.sharedHelper().getCurrentUer().getPassword());
        } catch (Exception e) {
            params = null;
        }
        return params;
    }

    void updateUI (TTUser user) {

        avatar.setImageURI(ImgUtil.getCDNUrlWithPathStr(user.getUserAvatar()));
        nickName.setText(user.getNickName());
        job.setText(user.getOccupation());
        homeTown.setText(user.getHometown());
        oneWords.setText(user.getExperience());

        if (user.getUserRealName() != null) {
            nameNoCertLabel.setVisibility(View.GONE);
            realName.setVisibility(View.VISIBLE);
            realName.setText(user.getUserRealName());

        } else {
            nameNoCertLabel.setVisibility(View.VISIBLE);
            realName.setVisibility(View.GONE);


        }

        if (user.getEmail() != null) {
            emailNoCertLabel.setVisibility(View.GONE);
            email.setVisibility(View.VISIBLE);
            email.setText(user.getEmail());

        } else {
            emailNoCertLabel.setVisibility(View.VISIBLE);
            email.setVisibility(View.GONE);

        }

        if (user.getUserPhoneNumber() != null) {
            phoneNoCertLabel.setVisibility(View.GONE);
            phone.setVisibility(View.VISIBLE);
            phone.setText(user.getUserPhoneNumber());

        } else {
            phoneNoCertLabel.setVisibility(View.VISIBLE);
            phone.setVisibility(View.GONE);

        }

    }
}
