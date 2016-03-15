package com.wukong.ttravel.service.home.activity;

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
 * Created by wukong on 3/15/16.
 */
public class TailorLineDetailActivity extends BaseActivity {

    @Bind(R.id.action_bar_title)
    TextView title;
    @Bind(R.id.webView)
    WebView webView;

    private String strProductID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_line_detail);
        ButterKnife.bind(this);

        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setTextZoom(250);

        Bundle extras = getIntent().getExtras();
        strProductID = extras.getString("id");
        String foundTitle = extras.getString("title");
        title.setText(foundTitle);

        loadData();


    }

    private void loadData() {
        showLoading("正在加载...");
        HttpClient.post("Traveler/GetProductDetails", getParams(strProductID), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                dismissSvHud();
                JSONObject jsonObject = (JSONObject) obj;
                String htmlContent = jsonObject.getString("Details");
                webView.loadData(htmlContent, "text/html; charset=UTF-8", null);
            }

            @Override
            public void onFail(HttpError error) {
                showError(error.getMessage());
            }
        });

    }

    private JSONObject getParams(String id) {

        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strProductID", id);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }

}
