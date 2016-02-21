package com.wukong.ttravel.service.discover.activity;

import android.os.Bundle;
import android.view.View;
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
 * Created by wukong on 2/21/16.
 */
public class DiscoverDetailActivity extends BaseActivity {

    @Bind(R.id.action_bar_title)
    TextView title;
    @Bind(R.id.webView)
    WebView webView;

    private String strFoundID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_detail);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        strFoundID = extras.getString("id");
        String foundTitle = extras.getString("title");
        title.setText(foundTitle);

        loadData();

        showProgressDialog("正在加载");
    }

    private void loadData() {
        HttpClient.post("Traveler/GetFound", getParams(strFoundID), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                hideProgressDialog();

                JSONObject jsonObject = (JSONObject) obj;
                String htmlContent = jsonObject.getString("FoundContent");
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
            params.put("strFoundID", id);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }

    public void handleBack(View view) {
        onBackPressed();
    }
}
