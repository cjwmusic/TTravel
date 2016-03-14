package com.wukong.ttravel.service.my.activity;

import android.os.Bundle;
import android.view.View;

import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.Router.Router;
import com.wukong.ttravel.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wukong on 3/15/16.
 */
public class SettingActivity extends BaseActivity {

    @OnClick(R.id.setting_user_password)
    void onClickUserPassword(View v) {
        Router.sharedRouter().open("resetPassword");
    }
    @OnClick(R.id.setting_pay_password)
    void onClickPayPassword(View v) {


    }
    @OnClick(R.id.setting_tixian_password)
    void onClickTixianPassword(View v) {


    }
    @OnClick(R.id.setting_black_name)
    void onClickBlackNameList(View v) {


    }
    @OnClick(R.id.setting_logout)
    void onClickLoginLout(View v) {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }
}
