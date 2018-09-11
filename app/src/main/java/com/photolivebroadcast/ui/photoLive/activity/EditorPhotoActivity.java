package com.photolivebroadcast.ui.photoLive.activity;

import android.os.Bundle;

import com.lixin.amuseadjacent.app.ui.base.BaseActivity;
import com.photolivebroadcast.R;

import org.jetbrains.annotations.Nullable;

/**
 * Created by zhf on 2018/9/11.
 */

public class EditorPhotoActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_photo);
        inittitle("编辑相册");
        initView();
        initData();
        initEvent();
    }

    private void initView() {

    }

    private void initData() {

    }

    private void initEvent() {

    }
}
