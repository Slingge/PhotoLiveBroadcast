package com.photolivebroadcast.ui.photoLive.activity;

import android.os.Bundle;

import com.lixin.amuseadjacent.app.ui.base.BaseActivity;
import com.photolivebroadcast.R;

import org.jetbrains.annotations.Nullable;

/**
 * Created by zhf on 2018/9/18.
 */

public class CustomAdverActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_adver);
        inittitle("自定义广告设置");
        StatusBarWhiteColor();
    }
}
