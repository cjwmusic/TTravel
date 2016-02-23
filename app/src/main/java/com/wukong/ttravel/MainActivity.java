package com.wukong.ttravel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;

import com.wukong.ttravel.service.discover.fragment.DiscoverFragment;
import com.wukong.ttravel.service.home.fragment.HomeFragment;
import com.wukong.ttravel.service.order.fragment.OrderFragment;
import com.wukong.ttravel.widget.TabManager;

public class MainActivity extends AppCompatActivity {

    private TabHost mTabHost;
    private TabManager mTabManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTabHost();
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
        mTabManager.addTab(getTabSpecView("message", R.layout.tab_item_message), HomeFragment.class, null);
        mTabManager.addTab(getTabSpecView("my", R.layout.tab_item_my), HomeFragment.class, null);

        //设置tabItem分割线的颜色
        mTabHost.getTabWidget().setDividerDrawable(R.color.tab_bg);

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
}
