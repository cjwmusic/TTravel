package com.wukong.ttravel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.wukong.ttravel.Base.Router.Router;
import com.wukong.ttravel.Base.im.ImTypeMessageEvent;
import com.wukong.ttravel.Utils.Constant;
import com.wukong.ttravel.Utils.Helper;
import com.wukong.ttravel.service.custom.fragment.CustomServiceFragment;
import com.wukong.ttravel.service.discover.fragment.DiscoverFragment;
import com.wukong.ttravel.service.home.fragment.HomeFragment;
import com.wukong.ttravel.service.message.fragment.ContactListFragment;
import com.wukong.ttravel.service.message.model.TTIMMessage;
import com.wukong.ttravel.service.my.fragment.MeFragment;
import com.wukong.ttravel.service.my.model.TTSwitchTailorEvent;
import com.wukong.ttravel.service.order.fragment.OrderFragment;
import com.wukong.ttravel.widget.TabManager;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private TabHost mTabHost;
    private TabManager mTabManager;
    private SlidingMenu slidingMenu;

    private final int MESSAGE_SINGIN_REQUEST_CODE = 100;
    private final int ME_SINGIN_REQUEST_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        initTabHost();
        //slidingMenu
        initSlideMenu();
    }


    void initSlideMenu() {

        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.RIGHT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.me_menu_content);

        MeFragment meFragment = new MeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.right_menu_fragment,meFragment).commit();

    }

    //测试LeanCloud是否工作正常
    private void leanCloudTest() {
        // 测试 SDK 是否正常工作的代码
        AVObject testObject = new AVObject("AndroidTestObject");
        testObject.put("words","Hello World!");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d("leanCloudTest----saved", "success!");
                }
            }
        });
    }

    //初始化TabHost
    private void initTabHost() {
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabManager = new TabManager(this, mTabHost, android.R.id.tabcontent);
        mTabHost.setup();
        //设置tabItem
        mTabManager.addTab(getTabSpecView("home", R.layout.tab_item_home), HomeFragment.class, null);
        mTabManager.addTab(getTabSpecView("discover", R.layout.tab_item_discover), DiscoverFragment.class, null);
        mTabManager.addTab(getTabSpecView("order", R.layout.tab_item_order), OrderFragment.class, null);
        mTabManager.addTab(getTabSpecView("message", R.layout.tab_item_message), ContactListFragment.class, null);
        mTabManager.addTab(getTabSpecView("my", R.layout.tab_item_my), HomeFragment.class, null);

        //设置tabItem分割线的颜色
        mTabHost.getTabWidget().setDividerDrawable(R.color.tab_bg);

        //点击了消息tab
        mTabHost.getTabWidget().getChildTabViewAt(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Helper.sharedHelper().hasUserId()) {
                    mTabHost.setCurrentTab(3);
                } else {
                    Router.sharedRouter().openFormResult("doLogin", ME_SINGIN_REQUEST_CODE, MainActivity.this);
                }
            }
        });

        //点击了我的tab
        mTabHost.getTabWidget().getChildTabViewAt(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Helper.sharedHelper().hasUserId()) {
                    slidingMenu.toggle();
                } else {
                    Router.sharedRouter().openFormResult("doLogin", MESSAGE_SINGIN_REQUEST_CODE, MainActivity.this);
                }
            }
        });

        //绑定点击事件

    }

    public TabHost.TabSpec getTabSpecView(String tag, int layoutId) {
        if (mTabHost != null && mTabHost.newTabSpec(tag) != null) {
            return mTabHost.newTabSpec(tag).setIndicator(getIndicatorView(layoutId));
        }
        return null;
    }

    private View getIndicatorView(int layout) {
        View view = getLayoutInflater().inflate(layout, null);
        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ME_SINGIN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mTabHost.setCurrentTab(3);
            }
        } else if (requestCode == MESSAGE_SINGIN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mTabHost.setCurrentTab(4);
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    //用户切换身份的消息
    public void onEvent(TTSwitchTailorEvent event) {

        System.out.println("用户选择了切换身份");
        slidingMenu.toggle();
        //得到用户身份
        int userType = event.to;
        if (userType == 2) {
            mTabManager.updateHomeTab(getTabSpecView("home", R.layout.tab_item_custom),
                    CustomServiceFragment.class, null, "定制");
        } else {
            mTabManager.updateHomeTab(getTabSpecView("home", R.layout.tab_item_home),
                    HomeFragment.class, null, "首页");
        }

        mTabHost.setCurrentTab(0);

    }
}
