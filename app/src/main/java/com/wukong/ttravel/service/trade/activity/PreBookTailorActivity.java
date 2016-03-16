package com.wukong.ttravel.service.trade.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import com.wukong.ttravel.service.test.CanlendarTestActivity;
import com.wukong.ttravel.service.trade.adapter.PreBookAdapter;
import com.wukong.ttravel.service.trade.model.TradeInfo;
import com.wukong.ttravel.widget.KCalendar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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

    String date = null;// 设置默认选中的日期  格式为 “2014-04-05” 标准DATE格式

    @OnClick(R.id.start_time)
    void onClickStartTime(View v) {
        new PopupWindows(PreBookTailorActivity.this, startTimeTv, new OnCalendarSelectedListener() {
            @Override
            public void onCalendarSelected(String dateFormat) {
                startTimeTv.setText(dateFormat);
                startTime = dateFormat;
            }
        });
    }

    @OnClick(R.id.stop_time)
    void onClickStopTime(View v) {
        new PopupWindows(PreBookTailorActivity.this, stopTimeTv, new OnCalendarSelectedListener() {
            @Override
            public void onCalendarSelected(String dateFormat) {
                stopTimeTv.setText(dateFormat);
                stopTime = dateFormat;
            }
        });
    }


    @OnClick(R.id.book_button)
    void onClick(View v) {
        ///TODO
        // 先判断用户是否登录

        //非空校验
        //线路选择验证
        if (selectedLine == null) {
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
        tradeInfo.setStrProductID(selectedLine.getProdID());
        tradeInfo.setDatBegin(startTime);
        tradeInfo.setDatEnd(stopTime);
        tradeInfo.setDecPrice(selectedLine.getProdPrice());
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
    private TailorLine selectedLine;

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
                TailorLine line = adapter.getItem(position);
                selectedLine = line;
                if (peopleCount != null) {
                    try {

                        Float price = Float.parseFloat(line.getProdPrice());
                        Float count = Float.parseFloat(peopleCount);
                        Float payAmount = price * count;
                        payCountTv.setText("" + payAmount);

                    } catch (Exception e) {
                        showInfo("请输入正确的格式");

                    }
                }

                adapter.setCurrentSelectedIndex(position);
                adapter.notifyDataSetChanged();
            }
        });

        peopleCountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = peopleCountEt.getText().toString().trim();
                if (string.length() > 0) {
                    peopleCount = string;

                    if (selectedLine != null && string.length() > 0) {

                        try {
                            String evPrice = selectedLine.getProdPrice();
                            Float price = Float.parseFloat(evPrice);
                            Float count = Float.parseFloat(string);
                            Float payAmount = price * count;
                            payCountTv.setText("" + payAmount);
                        } catch (Exception e) {
                            showInfo("请输入正确的格式");
                        }

                    }
                }

            }
        });

        loadLinesData();

        userNameEt.setText(Helper.sharedHelper().getCurrentUer().getNickName());
        phoneEt.setText(Helper.sharedHelper().getCurrentUer().getUserPhoneNumber());
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


    public interface OnCalendarSelectedListener {
        void onCalendarSelected(String dateFormat);
    }


    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent, final OnCalendarSelectedListener listener) {

            View view = View.inflate(mContext, R.layout.popupwindow_calendar,
                    null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_in));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_1));

            setWidth(ViewGroup.LayoutParams.FILL_PARENT);
            setHeight(ViewGroup.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            final TextView popupwindow_calendar_month = (TextView) view
                    .findViewById(R.id.popupwindow_calendar_month);
            final KCalendar calendar = (KCalendar) view
                    .findViewById(R.id.popupwindow_calendar);
            Button popupwindow_calendar_bt_enter = (Button) view
                    .findViewById(R.id.popupwindow_calendar_bt_enter);

            popupwindow_calendar_month.setText(calendar.getCalendarYear() + "年"
                    + calendar.getCalendarMonth() + "月");

            if (null != date) {

                int years = Integer.parseInt(date.substring(0,
                        date.indexOf("-")));
                int month = Integer.parseInt(date.substring(
                        date.indexOf("-") + 1, date.lastIndexOf("-")));
                popupwindow_calendar_month.setText(years + "年" + month + "月");

                calendar.showCalendar(years, month);
                calendar.setCalendarDayBgColor(new String[]{date},R.drawable.calendar_date_focused);
//                calendar.setCalendarDayBgColor(date,R.drawable.calendar_date_focused);
            }

            List<String> list = new ArrayList<String>(); //设置标记列表
            list.add("2014-04-01");
            list.add("2014-04-02");
            calendar.addMarks(list, 0);

            //监听所选中的日期
            calendar.setOnCalendarClickListener(new KCalendar.OnCalendarClickListener() {

                public void onCalendarClick(int row, int col, String dateFormat) {
                    int month = Integer.parseInt(dateFormat.substring(
                            dateFormat.indexOf("-") + 1,
                            dateFormat.lastIndexOf("-")));

                    if (calendar.getCalendarMonth() - month == 1//跨年跳转
                            || calendar.getCalendarMonth() - month == -11) {
                        calendar.lastMonth();

                    } else if (month - calendar.getCalendarMonth() == 1 //跨年跳转
                            || month - calendar.getCalendarMonth() == -11) {
                        calendar.nextMonth();

                    } else {
                        calendar.removeAllBgColor();
                        calendar.setCalendarDayBgColor(new String[]{dateFormat}, R.drawable.calendar_date_focused);
//                        calendar.setCalendarDayBgColor(dateFormat, R.drawable.calendar_date_focused);
                        listener.onCalendarSelected(dateFormat);
                    }
                }
            });

            //监听当前月份
            calendar.setOnCalendarDateChangedListener(new KCalendar.OnCalendarDateChangedListener() {
                public void onCalendarDateChanged(int year, int month) {
                    popupwindow_calendar_month
                            .setText(year + "年" + month + "月");
                }
            });

            //上月监听按钮
            RelativeLayout popupwindow_calendar_last_month = (RelativeLayout) view
                    .findViewById(R.id.popupwindow_calendar_last_month);
            popupwindow_calendar_last_month
                    .setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            calendar.lastMonth();
                        }

                    });

            //下月监听按钮
            RelativeLayout popupwindow_calendar_next_month = (RelativeLayout) view
                    .findViewById(R.id.popupwindow_calendar_next_month);
            popupwindow_calendar_next_month
                    .setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            calendar.nextMonth();
                        }
                    });

            //关闭窗口
            popupwindow_calendar_bt_enter
                    .setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            dismiss();
                        }
                    });
        }
    }

}
