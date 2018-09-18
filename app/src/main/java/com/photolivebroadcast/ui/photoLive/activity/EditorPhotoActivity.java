package com.photolivebroadcast.ui.photoLive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lixin.amuseadjacent.app.ui.base.BaseActivity;
import com.photolivebroadcast.R;
import com.photolivebroadcast.ui.photoLive.adapter.ClassifySettingAdapter;

import org.jetbrains.annotations.Nullable;

/**
 * Created by zhf on 2018/9/11.
 */

public class EditorPhotoActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvInformation;
    private TextView tvShot;
    private TextView tvPhotoType;
    private TextView tvAdvertise;
    private TextView tvWatermark;
    private TextView tvInteractSetting;
    private TextView tvAdminCameraman;
    private TextView tvAdminExamine;
    private TextView tvAdminTrim;
    private TextView tvWatchSetting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_photo);
        StatusBarWhiteColor();
        inittitle("编辑相册");
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        tvInformation = (TextView) findViewById(R.id.tv_information);
        tvShot = (TextView) findViewById(R.id.tv_shot);
        tvPhotoType = (TextView) findViewById(R.id.tv_photo_type);
        tvAdvertise = (TextView) findViewById(R.id.tv_advertise);
        tvWatermark = (TextView) findViewById(R.id.tv_watermark);
        tvInteractSetting = (TextView) findViewById(R.id.tv_interact_setting);
        tvAdminCameraman = (TextView) findViewById(R.id.tv_admin_cameraman);
        tvAdminExamine = (TextView) findViewById(R.id.tv_admin_examine);
        tvAdminTrim = (TextView) findViewById(R.id.tv_admin_trim);
        tvWatchSetting = (TextView) findViewById(R.id.tv_watch_setting);
    }

    private void initData() {

    }

    private void initEvent() {
        tvInformation.setOnClickListener(this);
        tvShot.setOnClickListener(this);
        tvPhotoType.setOnClickListener(this);
        tvAdvertise.setOnClickListener(this);
        tvWatermark.setOnClickListener(this);
        tvInteractSetting.setOnClickListener(this);
        tvAdminCameraman.setOnClickListener(this);
        tvAdminExamine.setOnClickListener(this);
        tvAdminTrim.setOnClickListener(this);
        tvWatchSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            //基本信息
            case R.id.tv_information:
                break;
            //拍摄活动信息
            case R.id.tv_shot:
                intent = new Intent(this, ShotImageActivity.class);
                break;
            //相册分类
            case R.id.tv_photo_type:
                intent = new Intent(this, ClassifySettingActivity.class);
                break;
            //下方自定义广告
            case R.id.tv_advertise:
                intent = new Intent(this, CustomAdverActivity.class);
                break;
            //水印功能
            case R.id.tv_watermark:
                intent = new Intent(this, WaterMarkActivity.class);
                break;
            //互动设置
            case R.id.tv_interact_setting:

                break;
            //管理摄影师
            case R.id.tv_admin_cameraman:
                intent = new Intent(this, AdminCameramanActivity.class);
                break;
            //管理审核员
            case R.id.tv_admin_examine:
                intent = new Intent(this, AdminExamineActivity.class);
                break;
            //管理修图师
            case R.id.tv_admin_trim:
                intent = new Intent(this, AdminTrimActivity.class);
                break;
            //观看设置
            case R.id.tv_watch_setting:
                intent = new Intent(this, WatchSettingActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
