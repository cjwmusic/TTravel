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
import com.wukong.ttravel.Utils.Helper;
import com.wukong.ttravel.Utils.MessageUtils;
import com.wukong.ttravel.service.home.model.TailorLine;
import com.wukong.ttravel.service.trade.adapter.PreBookAdapter;
import com.wukong.ttravel.service.trade.model.TradeInfo;

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
    TextView startTimeTv;
    @Bind(R.id.stop_time)
    TextView stopTimeTv;
    @Bind(R.id.people_count)
    EditText peopleCountEt;
    @Bind(R.id.username_et)
    EditText userNameEt;
    @Bind(R.id.phone_et)
    EditText phoneEt;
    @Bind(R.id.pay_count)
    TextView payCountTv;


    private String startTime;
    private String stopTime;
    private String peopleCount;
    private String userName;
    private String phoneNumber;

    @OnClick(R.id.book_button)
    void onClick(View v) {
        ///TODO
        // 先判断用户是否登录




        //非空校验
        //线路选择验证
        if (seletedLine == null) {
            MessageUtils.showToastCenter("请选择线路");
            return;
        }

        //起止时间验证
        startTime = startTimeTv.getText().toString().trim();
        stopTime = stopTimeTv.getText().toString().trim();
        if (startTime.length() == 0 || stopTime.length() == 0) {
            MessageUtils.showToast("请选择起止时间");
            return;
        }

        //人数验证
        peopleCount = peopleCountEt.getText().toString().trim();
        if (peopleCount.length() == 0) {
            MessageUtils.showToast("请输入人数");
            return;
        }

        //联系人姓名验证
        userName = userNameEt.getText().toString().trim();
        if (userName.length() == 0) {
            MessageUtils.showToast("请输入联系人姓名");
            return;
        }

        //联系人电话验证
        phoneNumber = phoneEt.getText().toString().trim();
        if (phoneNumber.length() == 0) {
            MessageUtils.showToastCenter("请输入联系人姓名");
            return;
        }

        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setStrTravelerID(Helper.sharedHelper().getUserId());
        tradeInfo.setStrTailorID(tailorId);
        tradeInfo.setStrProductID(seletedLine.getProdID());
        tradeInfo.setDatBegin(startTime);
        tradeInfo.setDatEnd(stopTime);
        tradeInfo.setDecPrice(seletedLine.getProdPrice());
        tradeInfo.setIntPeople(peopleCount);
//        tradeInfo.setDecMoney(); //设置总价
        tradeInfo.setStrLinkman(userName);
        tradeInfo.setStrPhone(phoneNumber);
        //全部验证通过，提交订单
        postTrade(tradeInfo);
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

    private void postTrade(TradeInfo tradeInfo) {
        showLoading("正在提交");
        HttpClient.post("Traveler/CreateOrder", getPostTradeParams(tradeInfo), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                dismissSvHud();
                JSONObject data = (JSONObject) obj;
                int result = data.getInteger("result");
                if (result == 1) {
                    showSuccess("订单提交成功");
                } else {
                    showError(data.getString("Message"));
                }
            }

            @Override
            public void onFail(HttpError error) {
                showError(error.getMessage());
            }
        });

    }


    private JSONObject getPostTradeParams(TradeInfo tradeInfo) {
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strTravelerID", tradeInfo.getStrTravelerID());
            params.put("strTailorID",tradeInfo.getStrTailorID());
            params.put("strProductID", tradeInfo.getStrProductID());
            params.put("datBegin", tradeInfo.getDatBegin());
            params.put("datEnd", tradeInfo.getDatEnd());
            params.put("decPrice" , tradeInfo.getDecPrice());
            params.put("intPeople", tradeInfo.getIntPeople());
            params.put("decMoney",  tradeInfo.getDecMoney());
            params.put("strLinkman", tradeInfo.getStrLinkman());
            params.put("strPhone", tradeInfo.getStrPhone());

        } catch (Exception e) {
            params = null;
        }
        return params;
    }

}
