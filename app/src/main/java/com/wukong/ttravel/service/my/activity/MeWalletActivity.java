package com.wukong.ttravel.service.my.activity;

import android.os.Bundle;
import android.support.v7.internal.widget.ViewUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.Helper;
import com.wukong.ttravel.service.my.model.TTHelpItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wukong on 3/15/16.
 */
public class MeWalletActivity extends BaseActivity {


    @Bind(R.id.remain_count)
    TextView remainCount;
    @Bind(R.id.tx_count)
    TextView txCount;
    @Bind(R.id.in_count)
    TextView inCount;
    @Bind(R.id.out_count)
    TextView outCount;

    @OnClick(R.id.detail_button)
    void onClickDetailButton(View v) {

    }

    @OnClick(R.id.tx_button)
    void onClickTxButton(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_wallet);
        ButterKnife.bind(this);
        loadData();
    }

    void loadData() {

        showLoading("正在加载...");
        HttpClient.post("Member/GetWallet", getParams(), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                dismissSvHud();

                JSONObject data = (JSONObject) obj;
                JSONObject model = data.getJSONObject("Model");
                if (model != null) {
                    remainCount.setText(model.getFloat("MembBalance") + "");
                    inCount.setText(model.getFloat("MembIncome") + "");
                    outCount.setText(model.getFloat("MembDefray") + "");
                    txCount.setText(model.getFloat("MembWithdraw") + "");
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
        } catch (Exception e) {
            params = null;
        }
        return params;
    }
}
