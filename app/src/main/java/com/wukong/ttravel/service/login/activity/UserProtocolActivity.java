package com.wukong.ttravel.service.login.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wukong on 3/10/16.
 */
public class UserProtocolActivity extends BaseActivity {

    @Bind(R.id.action_bar_title)
    TextView title;
    @Bind(R.id.webView)
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_protocol);
        ButterKnife.bind(this);

        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        loadData();

    }


    private void loadData() {

        showLoading("正在加载...");
        HttpClient.post("Basic/GetDocumentContent", getParams("20151225001"), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {

                JSONObject jsonObject = (JSONObject) obj;
                String htmlContent = jsonObject.getString("Html");
                webView.loadData(htmlContent, "text/html; charset=UTF-8", null);
                dismissSvHud();

            }

            @Override
            public void onFail(HttpError error) {
                hideProgressDialog();
                showError(error.getMessage());

            }
        });

    }

    private JSONObject getParams(String id) {

        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strDocumentID", id);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }
}
