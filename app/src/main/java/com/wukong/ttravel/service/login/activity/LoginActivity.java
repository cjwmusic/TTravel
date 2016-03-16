package com.wukong.ttravel.service.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.Events.LoginSuccessEvent;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.Constant;
import com.wukong.ttravel.Utils.Helper;
import com.wukong.ttravel.Utils.MessageUtils;
import com.wukong.ttravel.service.login.model.TTUser;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by wukong on 3/9/16.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.username_et)
    EditText userNameEt;

    @Bind(R.id.password_et)
    EditText passwordEt;

    @Bind(R.id.forget_password)
    TextView forgetPasswordButton;

    @Bind(R.id.fast_login)
    TextView fastLoginButton;

    @Bind(R.id.login_button)
    Button loginButton;

    @Bind(R.id.register_button)
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login_button)
    void doLogin(View v) {
        //验证手机
        String username = userNameEt.getText().toString().trim();
        if (username.length() == 0) {
            MessageUtils.showToastCenter("手机号码或者邮箱不能为空");
            return;
        }

        String password = passwordEt.getText().toString().trim();
        if (password.length() == 0) {
            MessageUtils.showToastCenter("密码不能为空");
            return;
        } else {
            password = new String(Hex.encodeHex(DigestUtils.md5(password)));
        }

        showLoading("正在登录...");

        HttpClient.postLogin("Enter/PasswordLogin", getParams(username, password.toLowerCase()), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                JSONObject data = (JSONObject) obj;
                showSuccess(data.getString("Message"));
                //登录成功，发通知
                JSONObject userModel = data.getJSONObject("Model");
                TTUser user = new TTUser(userModel);
                loginSuccess(user);
            }

            @Override
            public void onFail(HttpError error) {
                showError(error.getMessage());
            }
        });

    }

    //注册
    @OnClick(R.id.register_button)
    void doRegister(View v) {
        Intent intent = new Intent();
        intent.setClass(this, RegisterActivity.class);
        startActivity(intent);
    }

    //忘记密码
    @OnClick(R.id.forget_password)
    void doForgetPassword(View v) {
        Intent intent = new Intent();
        intent.setClass(this,ResetPasswordActivity.class);
        startActivity(intent);
    }

    //快速登录
    @OnClick(R.id.fast_login)
    void doFastLogin(View v) {
        Intent intent = new Intent();
        intent.setClass(this, FastLoginActivity.class);
        startActivity(intent);
    }

    //登录成功
    void loginSuccess(TTUser user){
        //1 获取用户基本信息，存储到本地
        Helper.sharedHelper().setUserId(user.getUserId());
        storeUserProfile(user);

        //2 登录LeanCloud

        //3 发通知出去
        LoginSuccessEvent event = new LoginSuccessEvent();
        event.userId = user.getUserId();
        EventBus.getDefault().post(event);


        //3 finish
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();

    }

    //存储用户的基本信息
    private void storeUserProfile(TTUser user) {

        Helper.sharedHelper().setStringUserInfo(Constant.USER_NICK, user.getNickName());
        Helper.sharedHelper().setStringUserInfo(Constant.USER_PASSWORD,user.getPassword());
        Helper.sharedHelper().setStringUserInfo(Constant.USER_MOBILE,user.getUserPhoneNumber());
        Helper.sharedHelper().setStringUserInfo(Constant.USER_ID, user.getUserId());
        Helper.sharedHelper().setStringUserInfo(Constant.USER_AVATAR, user.getUserAvatar());
        Helper.sharedHelper().setIntUserInfo(Constant.USER_GENDER, user.getGender());

    }

    private JSONObject getParams(String username, String password) {
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strCode", username);
            params.put("strPassword", password);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }

}
