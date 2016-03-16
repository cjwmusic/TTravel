package com.wukong.ttravel.service.my.activity;

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
 * Created by wukong on 3/14/16.
 */
public class TTAboutActivity extends BaseActivity {


    @Bind(R.id.action_bar_title)
    TextView title;
    @Bind(R.id.webView)
    WebView webView;

    private String strDocumentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_detail);
        ButterKnife.bind(this);
        ButterKnife.bind(this);

        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setTextZoom(100);

        Bundle extras = getIntent().getExtras();
        strDocumentID = extras.getString("id");
        String foundTitle = extras.getString("title");
        title.setText(foundTitle);

        loadData();

        showLoading("正在加载");

    }

    private void loadData() {
        HttpClient.post("Basic/GetDocumentContent", getParams(strDocumentID), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {

                dismissSvHud();

                JSONObject jsonObject = (JSONObject) obj;
                String htmlContent = jsonObject.getString("Html");
                webView.loadData(htmlContent, "text/html; charset=UTF-8", null);

            }

            @Override
            public void onFail(HttpError error) {
                hideProgressDialog();


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
