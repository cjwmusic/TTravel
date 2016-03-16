package com.wukong.ttravel.service.trade.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.Base.views.MyListView;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.CommonUtil;
import com.wukong.ttravel.service.home.model.TailorLine;
import com.wukong.ttravel.service.trade.adapter.PreBookAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by wukong on 3/15/16.
 */
public class PreBookTailorActivity extends BaseActivity {

    @Bind(R.id.listView)
    MyListView listView;
    @Bind(R.id.start_time)
    TextView startTime;
    @Bind(R.id.stop_time)
    TextView stopTime;
    @Bind(R.id.people_count)
    EditText peopleCountEt;
    @Bind(R.id.username_et)
    EditText userNameEt;
    @Bind(R.id.phone_et)
    EditText phoneEt;
    @Bind(R.id.pay_count)
    TextView payCount;

    @OnClick(R.id.book_button)
    void onClick(View v) {

    }

    //负责处理点击空白处,键盘退出
    @OnClick(R.id.preboook)
    void onClickBg(View v) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private PreBookAdapter adapter;
    private ArrayList<TailorLine> listData;

    private String tailorId;
    private TailorLine seletedLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prebook_tailor);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        tailorId = extras.getString("tailorId");
        listData = new ArrayList<>();
        adapter = new PreBookAdapter(this, listData);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("点击了---------");
                TailorLine line = adapter.getItem(position);
                adapter.setCurrentSelectedIndex(position);
                adapter.notifyDataSetChanged();
            }
        });

        loadLinesData();
    }


    private void loadLinesData() {

        showLoading("正在加载");
        HttpClient.post("Traveler/GetTailorProduct", getParams(tailorId), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                dismissSvHud();
                JSONObject data = (JSONObject) obj;
                JSONArray listArray = data.getJSONArray("List");
                if (listArray != null && listArray.size() > 0) {
                    for (int i = 0; i < listArray.size(); i++) {
                        TailorLine line = new TailorLine(listArray.getJSONObject(i));
                        line.setProdLineNumber("线路" + CommonUtil.AybTochinese(i + 1));
                        listData.add(line);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(HttpError error) {
                showError(error.getMessage());
            }
        });

    }

    private JSONObject getParams(String tailorId) {

        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strTailorID", tailorId);
        } catch (Exception e) {
            params = null;
        }
        return params;
    }

}
