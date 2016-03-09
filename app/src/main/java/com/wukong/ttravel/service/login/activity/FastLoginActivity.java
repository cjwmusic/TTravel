package com.wukong.ttravel.service.login.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.MessageUtils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wukong on 3/10/16.
 */
public class FastLoginActivity extends BaseActivity {


    @Bind(R.id.username_et)
    EditText userNameEt;

    @Bind(R.id.sms_code_et)
    EditText smsCodeEt;

    @Bind(R.id.login_button)
    Button loginButton;

    @Bind(R.id.get_sms_code_button)
    Button getSmsCodeButton;

    @OnClick(R.id.login_button)
    void doLogin(View v) {

        //验证手机
        String username = userNameEt.getText().toString().trim();
        if (username.length() == 0) {
            MessageUtils.showToastCenter("手机号码或者邮箱不能为空");
            return;
        }

        String smsCode = smsCodeEt.getText().toString().trim();
        if (smsCode.length() == 0) {
            MessageUtils.showToastCenter("验证码不能为空");
            return;
        }

        showLoading("正在登录...");

        HttpClient.postLogin("Enter/QuickLogin", getLoginParams(username, smsCode), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                JSONObject data = (JSONObject) obj;
                showSuccess(data.getString("Message"));
            }

            @Override
            public void onFail(HttpError error) {
                showError(error.getMessage());
            }
        });



    }

    @OnClick(R.id.get_sms_code_button)
    void doGetSmsCode(View v) {

        //验证手机
        String username = userNameEt.getText().toString().trim();
        if (username.length() == 0) {
            MessageUtils.showToastCenter("手机号码或者邮箱不能为空");
            return;
        }

        showLoading("正在获取验证码...");

        HttpClient.postLogin("Enter/GetVerificationCode", getParams(username), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                JSONObject data = (JSONObject) obj;
                showSuccess(data.getString("Message"));
            }

            @Override
            public void onFail(HttpError error) {
                showError(error.getMessage());
            }
        });

    }

    private JSONObject getParams(String username) {
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strMobileEmail", username);
            params.put("intWhy",2);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }

    private JSONObject getLoginParams(String username, String smsCode) {
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strCode", username);
            params.put("strVerify",smsCode);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_login);
        ButterKnife.bind(this);
    }
}
