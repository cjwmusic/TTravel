package com.wukong.ttravel.service.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
public class RegisterActivity extends BaseActivity{
    @Bind(R.id.username_et)
    EditText userNameEt;
    @Bind(R.id.sms_code_et)
    EditText smsCodeEt;
    @Bind(R.id.password_new_et)
    EditText passwordNewEt;
    @Bind(R.id.password_confirm_et)
    EditText passswordConfirmEt;


    @OnClick(R.id.user_protocol_text)
    void doViewUserProtocol(View v) {
        Intent intent = new Intent();
        intent.setClass(this,UserProtocolActivity.class);
        startActivity(intent);
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

    @OnClick(R.id.action_button)
    void doResetPwd(View v) {
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

        String passwordNew = passwordNewEt.getText().toString().trim();
        if (passwordNew.length() == 0) {
            MessageUtils.showToastCenter("密码不能为空");
            return;
        }

        String passwordConfirm = passswordConfirmEt.getText().toString().trim();
        if (passwordConfirm.length() == 0) {
            MessageUtils.showToastCenter("请输入确认密码");
            return;
        }

        if (!passwordNew.equals(passwordConfirm)) {
            MessageUtils.showToastCenter("两次密码输入不一致");
            return;
        }

        passwordConfirm = new String(Hex.encodeHex(DigestUtils.md5(passwordConfirm)));

        showLoading("正在注册...");

        HttpClient.postLogin("Enter/RegisterMember", getRegisterParams(username, smsCode, passwordConfirm), null, new HttpClient.HttpCallback<Object>() {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }


    private JSONObject getParams(String username) {
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strMobileEmail", username);
            params.put("intWhy",1);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }

    private JSONObject getRegisterParams(String username, String smsCode, String passWord) {
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strMobileEmail", username);
            params.put("strVerificationCode" ,smsCode);
            params.put("strPassword",passWord);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }
}
