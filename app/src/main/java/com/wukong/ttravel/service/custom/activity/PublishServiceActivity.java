package com.wukong.ttravel.service.custom.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.wukong.ttravel.Base.BaseActivity;
import com.wukong.ttravel.Base.request.HttpClient;
import com.wukong.ttravel.Base.request.HttpError;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.Helper;
import com.wukong.ttravel.Utils.ImgUtil;
import com.wukong.ttravel.Utils.MessageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wukong on 3/17/16.
 */
public class PublishServiceActivity extends BaseActivity{

    private static final int REQUEST_CAMERA_CODE = 11;
    private static final int REQUEST_PREVIEW_CODE = 22;

    private String imagePath;
    private String serverImagePath;

    @Bind(R.id.title_et)
    EditText serviceTitleEt;
    @Bind(R.id.desc_et)
    EditText serviceDescEt;
    @Bind(R.id.price_et)
    EditText priceEt;
    @Bind(R.id.choosed_image)
    SimpleDraweeView choosedImageView;

    String serviceTitle;
    String serviceDesc;
    String servicePrice;

    @OnClick(R.id.publish_button)
    void onClickPublishButton(View v) {

        serviceTitle = serviceTitleEt.getText().toString().trim();
        serviceDesc = serviceDescEt.getText().toString().trim();
        servicePrice = priceEt.getText().toString().trim();

        if (serviceTitle.length() == 0) {
            MessageUtils.showToast("请输入标题");
            return;
        }

        if (serviceDesc.length() == 0) {
            MessageUtils.showToast("请输入描述文字");
            return;
        }

        if (servicePrice.length() == 0) {
            MessageUtils.showToast("请输入价格");
            return;
        }

        if (imagePath == null) {
            MessageUtils.showToast("请选择图片");
            return;
        }

        if (imagePath != null) {
            showLoading("正在发布...");
            HttpClient.uploadImage("Picture/UploadPicture", imagePath, null, new HttpClient.HttpCallback<Object>() {
                @Override
                public void onSuccess(Object obj) {

                    JSONObject jsonObject = (JSONObject) obj;
                    String imageServerPath = jsonObject.getString("SaveUrl");
                    if (imageServerPath != null && imageServerPath.length() > 0) {
                        serverImagePath = imageServerPath;
                        //上传文件成功，开始发布请求
                        doPublish();

                    }

                }

                @Override
                public void onFail(HttpError error) {
                    showError(error.getMessage());

                }
            });
        }
    }

    void doPublish() {
        HttpClient.post("Tailor/CreateProduct", getParams(), null, new HttpClient.HttpCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                showSuccess("发布成功");
                new CountDownTimer(1600, 1600) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        finish();
                    }
                }.start();
            }

            @Override
            public void onFail(HttpError error) {
                showError(error.getMessage());
            }
        });

    }

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

    JSONObject getParams() {
        /*
        * strTailorID：伴客会员编码，不可为空
		strName：名称，不可为空
		strDescription：描述
		decPrice：单价（元/天/人），不可为空
		strPicture：图片地址
        * */
        JSONObject params;
        try {
            params = new JSONObject();
            params.put("strTailorID", Helper.sharedHelper().getUserId());
            params.put("strName", serviceTitle);
            params.put("strDescription", serviceDesc);
            params.put("decPrice", servicePrice);
            params.put("strPicture", imagePath);
        } catch (Exception e) {
            params = null;
        }

        return params;
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
                    Uri uri = Uri.fromFile(new File(strings.get(0)));
                    imagePath = strings.get(0);

                    Glide.with(PublishServiceActivity.this)
                            .load(new File(strings.get(0)))
                            .placeholder(R.mipmap.default_error)
                            .error(R.mipmap.default_error)
                            .centerCrop()
                            .crossFade()
                            .into(choosedImageView);
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
