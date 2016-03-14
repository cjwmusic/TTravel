package com.wukong.ttravel.service.my.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.Constant;
import com.wukong.ttravel.Utils.Helper;
import com.wukong.ttravel.Utils.MessageUtils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wukong on 3/15/16.
 */
public class PayPasswordSettingActivity extends BaseActivity {


    @Bind(R.id.action_bar_title)
    TextView titleTextView;

    @Bind(R.id.username_et)
    EditText userNameEt;
    @Bind(R.id.sms_code_et)
    EditText smsCodeEt;
    @Bind(R.id.password_new_et)
    EditText passwordNewEt;
    @Bind(R.id.password_confirm_et)
    EditText passswordConfirmEt;

    @Bind(R.id.get_sms_code_button)
    Button getSmsCodeButton;

    @OnClick(R.id.get_sms_code_button)
    void doGetSmsCode(View v) {
        //验证手机
        String username = userNameEt.getText().toString().trim();
        if (username.length() == 0) {
            MessageUtils.showToastCenter("手机号码或者邮箱不能为空");
            return;
        }

        getSmsCodeButton.setEnabled(false);
        getSmsCodeButton.setText("获取中");

        HttpClient.postLogin("Enter/GetVerificationCode", getParams(username), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                JSONObject data = (JSONObject) obj;
                showSuccess(data.getString("Message"));

                getSmsCodeButton.setText("60秒可重发");
                new CountDownTimer(60000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        getSmsCodeButton.setText(millisUntilFinished / 1000 + "秒可重发");
                    }

                    public void onFinish() {
                        getSmsCodeButton.setText("重新获取");
                        getSmsCodeButton.setEnabled(true);
                    }
                }.start();

            }

            @Override
            public void onFail(HttpError error) {
                showError(error.getMessage());
                getSmsCodeButton.setEnabled(true);
            }
        });
    }

    private String pwMd5;

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
            MessageUtils.showToastCenter("新密码不能为空");
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
        pwMd5 = passwordConfirm;

        showLoading("正在设置提现密码...");

        HttpClient.postLogin("Member/PayKeySet", getResetParams(username, smsCode, passwordConfirm), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                JSONObject data = (JSONObject) obj;
                showSuccess(data.getString("Message"));
                //将提现密码存入本地
                Helper.sharedHelper().setStringUserInfo(Constant.USER_TX_PASSWORD, pwMd5);
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
        setContentView(R.layout.activity_tx_setting);
        ButterKnife.bind(this);
        titleTextView.setText("支付密码设置");
    }

    private JSONObject getParams(String username) {
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strMobileEmail", username);
            params.put("intWhy",3);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }

    private JSONObject getResetParams(String username, String smsCode, String passWord) {
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strMemberID", username);
            params.put("strVerify" ,smsCode);
            params.put("strPassword",passWord);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }
}
