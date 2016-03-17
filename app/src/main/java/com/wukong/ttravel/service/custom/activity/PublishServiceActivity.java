package com.wukong.ttravel.service.custom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.Router.Router;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.Constant;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wukong on 3/17/16.
 */
public class PublishServiceActivity extends BaseActivity{

    private static final int REQUEST_CAMERA_CODE = 11;
    private static final int REQUEST_PREVIEW_CODE = 22;

    @Bind(R.id.title_et)
    EditText serviceTitleEt;
    @Bind(R.id.desc_et)
    EditText serviceDescEt;
    @Bind(R.id.price_et)
    EditText priceEt;

    @OnClick(R.id.add_image_button)
    void onClickAddImageButton(View v) {

        PhotoPickerIntent intent = new PhotoPickerIntent(PublishServiceActivity.this);
        intent.setSelectModel(SelectModel.SINGLE);
        intent.setShowCarema(true);
//                intent.setImageConfig(null);
        startActivityForResult(intent, REQUEST_CAMERA_CODE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_service);
        ButterKnife.bind(this);

    }


    public void selectPic(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isCheckbox", true);
        bundle.putInt("maxLength", 1);
        Router.sharedRouter().openFormResult("pick/photo", bundle,
                Constant.REQUEST_CODE_PICK_PHOTO, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> strings =  data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    System.out.println("选择的照片路径为--->" + strings.get(0));

                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    break;
                // 调用相机拍照
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:

                    break;

            }
        }
    }
}
