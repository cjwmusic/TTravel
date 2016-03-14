package com.wukong.ttravel.service.my.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wukong on 3/14/16.
 */
public class RealNameCertActivity extends BaseActivity{

    @Bind(R.id.front_photo_button)
    Button fontPhotoButton;
    @Bind(R.id.back_photo_button)
    Button backPhotoButton;

    @OnClick(R.id.front_photo_button)
    void onClickFront(View v) {


    }

    @OnClick(R.id.back_photo_button)
    void onClickBack(View v) {


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name_cert);
        ButterKnife.bind(this);
    }
}
