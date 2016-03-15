package com.wukong.ttravel.service.my.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
public class MeTxActivity extends BaseActivity {


    private String alipayId;
    private String txPassword;
    private String txCount;

    @Bind(R.id.tx_name_et)
    EditText txNameEt;

    @Bind(R.id.alipay_id_et)
    EditText alipayIdEt;

    @Bind(R.id.tx_count_et)
    EditText txCountEt;

    @Bind(R.id.tx_password_et)
    EditText txPasswordEt;

    @OnClick(R.id.tx_do_button)
    void onClickDoButton(View v) {
        //输入验证
        String name = txNameEt.getText().toString().trim();
        if (name.length() == 0) {
            MessageUtils.showToastCenter("请输入实名认证姓名");
            return;
        }

        String alipayId = alipayIdEt.getText().toString().trim();
        if (alipayId.length() == 0) {
            MessageUtils.showToastCenter("请输入支付宝帐号");
            return;
        }

        String count = txCountEt.getText().toString().trim();
        if (count.length() == 0) {
            MessageUtils.showToastCenter("请输入提现金额");
            return;
        }

        String password = txPasswordEt.getText().toString().trim();
        if (password.length() == 0) {
            MessageUtils.showToastCenter("请输入提现密码");
            return;
        }

        //验证通过发送请求
        loadData();

    }

    @OnClick(R.id.tx_forget_pw_button)
    void onClickForgetPwButton(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tx);
        ButterKnife.bind(this);

    }

    void loadData() {

        showLoading("正在加载...");
        HttpClient.post("Finance/WithdrawCash", getParams(), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {

                JSONObject data = (JSONObject) obj;
                if (data.getInteger("Result") == 1) {
                    showSuccess("提现成功");
                } else {
                    showSuccess(data.getString("Message"));
                }
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
            params.put("strMemberID", Helper.sharedHelper().getUserId());
            params.put("strAliCode", alipayId);
            params.put("strKey", txPassword);
            params.put("decMoney", txCount);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }
}
