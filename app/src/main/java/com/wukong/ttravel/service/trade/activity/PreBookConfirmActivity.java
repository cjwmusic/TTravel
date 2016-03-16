package com.wukong.ttravel.service.trade.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.ImgUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wukong on 3/17/16.
 */
public class PreBookConfirmActivity extends BaseActivity {

    @Bind(R.id.avatar)
    SimpleDraweeView avatar;
    @Bind(R.id.user_nick)
    TextView userNick;

    //下一步的Button
    @OnClick(R.id.next_button)
    void onClickNextButton(View v) {

    }

    //退款政策
    @OnClick(R.id.tdzc_button)
    void onClickTdzcButton(View v) {

    }

    //免责声明
    @OnClick(R.id.mzsm_tv)
    void onClickMzsmButton(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prebook_confirm);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        avatar.setImageURI(ImgUtil.getCDNUrlWithPathStr(bundle.getString("tailorAvatar")));
        userNick.setText(bundle.getString("tailorNick"));



    }
}
