package com.wukong.ttravel.service.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.Router.Router;
import com.wukong.ttravel.Events.LoginOutEvent;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.Helper;
import com.wukong.ttravel.service.my.adapter.BlackListAdapter;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;

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
        //读取本地支付密码，来判断用户是否已经设置过提现密码
        String txPassword =  Helper.sharedHelper().getPayPassword();
        Intent intent = new Intent();
        if (txPassword != null && txPassword.length() > 0) {
            intent.setClass(this,PayPasswordChangeActivity.class);
        } else {
            intent.setClass(this,PayPasswordSettingActivity.class);
        }

        startActivity(intent);
    }
    @OnClick(R.id.setting_tixian_password)
    void onClickTixianPassword(View v) {
        //读取本地提现密码，来判断用户是否已经设置过提现密码
        String txPassword =  Helper.sharedHelper().getTxPassword();
        Intent intent = new Intent();
        if (txPassword != null && txPassword.length() > 0) {
            intent.setClass(this,TxChangeActivity.class);
        } else {
            intent.setClass(this,TxSettingActivity.class);
        }

        startActivity(intent);
    }

    @OnClick(R.id.setting_black_name)
    void onClickBlackNameList(View v) {
        Intent intent = new Intent();
        intent.setClass(this,BlackListActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.setting_logout)
    void onClickLoginLout(View v) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("退出登录")
                .setContentText("您将退出登录~")
                .setCancelText("确认")
                .setConfirmText("取消")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                        //清除本地用户Id信息
                        Helper.sharedHelper().clearAllUserInfo();
                        LoginOutEvent event = new LoginOutEvent();
                        EventBus.getDefault().post(event);

                        finish();
                    }
                })
                .show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }
}
