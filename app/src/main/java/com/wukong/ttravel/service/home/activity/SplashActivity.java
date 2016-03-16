package com.wukong.ttravel.service.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.BasePageAdapter;
import com.wukong.ttravel.Base.Router.Router;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.Constant;
import com.wukong.ttravel.Utils.Helper;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import viewpagerindicator.CirclePageIndicator;

/**
 * Created by wukong on 3/16/16.
 */
public class SplashActivity extends BaseActivity {

    private final int SINGIN_REQUEST_CODE = 201;
    private final int REGISTER_QEQUAEST_CODE = 202;

    @Bind(R.id.tutorialLayout)
    ViewGroup tutorialLayout;
    @Bind(R.id.tutorialViewpager)
    ViewPager tutorialViewpager;
    @Bind(R.id.indicator)
    CirclePageIndicator indicator;

    @Bind(R.id.bottom_buttons)
    LinearLayout bottomButtons;

    @OnClick(R.id.register_button)
    void doRegister(View v) {
        Router.sharedRouter().openFormResult("doRegister", REGISTER_QEQUAEST_CODE, SplashActivity.this);
    }

    @OnClick(R.id.login_button)
    void doLogin(View v) {
        Router.sharedRouter().openFormResult("doLogin", SINGIN_REQUEST_CODE, SplashActivity.this);
    }

    @OnClick(R.id.goto_button)
    void doToMain(View v) {
        showMainScreen();
        finish();
    }

    TutorialPageAdapter tutorialPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initComponents();
        doNextAction();
    }

    private void initComponents() {
        tutorialPageAdapter = new TutorialPageAdapter();
        tutorialViewpager.setAdapter(tutorialPageAdapter);
        indicator.setViewPager(tutorialViewpager);
        bottomButtons.setVisibility(View.GONE);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == tutorialPageAdapter.getCount() - 1) {
                    Helper.sharedHelper().setBooleanUserInfo(Constant.SP_FIRST_START, false);
                    bottomButtons.setVisibility(View.VISIBLE);
                    indicator.setVisibility(View.GONE);
                } else {
                    bottomButtons.setVisibility(View.GONE);
                    indicator.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void doNextAction() {
        if (Helper.sharedHelper().getBooleanUserInfo(Constant.SP_FIRST_START, true)) {
            tutorialLayout.setVisibility(View.VISIBLE);
        } else {
            showMainScreen();
        }
    }

    void showMainScreen() {
        Router.sharedRouter().open("main");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SINGIN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                showMainScreen();
                finish();

            }
        } else if (requestCode == REGISTER_QEQUAEST_CODE) {
            if (resultCode == RESULT_OK) {
                showMainScreen();
                finish();
            }
        }
    }



    public class TutorialPageAdapter extends BasePageAdapter {
        private int[] images = new int[]{
                R.mipmap.start_page_1,
                R.mipmap.start_page_2,
                R.mipmap.start_page_3,
        };

        public TutorialPageAdapter() {
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public View newView(int position) {
            ImageView view = new ImageView(SplashActivity.this);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setImageResource(images[position]);
            return view;
        }
    }
}
