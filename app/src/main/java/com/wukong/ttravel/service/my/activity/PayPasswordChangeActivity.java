package com.wukong.ttravel.service.my.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.Helper;
import com.wukong.ttravel.Utils.MessageUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wukong on 3/15/16.
 */
public class PayPasswordChangeActivity extends BaseActivity {

    @Bind(R.id.action_bar_title)
    TextView titleTextView;

    @Bind(R.id.old_password)
    EditText oldPwEt;
    @Bind(R.id.new_password)
    EditText newPwEt;

    @OnClick(R.id.action_button)
    void doAction(View v) {

        String oldPw = oldPwEt.getText().toString().trim();
        if (oldPw.length() == 0) {
            MessageUtils.showToastCenter("旧密码不能为空");
            return;
        }

        String newPw = oldPwEt.getText().toString().trim();
        if (newPw.length() == 0) {
            MessageUtils.showToastCenter("新密码不能为空");
            return;
        }

        String txPassword = Helper.sharedHelper().getTxPassword();
        if (!txPassword.equals(oldPw)) {
            MessageUtils.showToastCenter("旧密码不正确");
            return;
        }

        loadData(oldPw,newPw);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tx_change);
        ButterKnife.bind(this);
        titleTextView.setText("支付密码修改");
    }


    void loadData(String oldPw,String newPw) {

        showLoading("正在修改...");

        HttpClient.post("Member/PayKeyModify", getParams(Helper.sharedHelper().getUserId(), oldPw, newPw), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                JSONObject data = (JSONObject) obj;
                showInfo(data.getString("Message"));
            }

            @Override
            public void onFail(HttpError error) {
                showError(error.getMessage());
            }
        });
    }

    private JSONObject getParams(String username,String oldPw,String newPw) {
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strMemberID",username);
            params.put("strOldPassword", oldPw);
            params.put("strNewPassword",newPw);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }

}
