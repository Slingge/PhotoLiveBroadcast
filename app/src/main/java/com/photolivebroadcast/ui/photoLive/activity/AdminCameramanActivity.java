package com.photolivebroadcast.ui.photoLive.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.lixin.amuseadjacent.app.ui.base.BaseActivity;
import com.photolivebroadcast.R;

import org.jetbrains.annotations.Nullable;

/**
 * Created by zhf on 2018/9/17.
 */

public class AdminCameramanActivity extends BaseActivity {
    private TextView tvUpdateCode;
    private TextView tvCopy;
    private TextView tvCode;
    private Button btnSubmit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cameraman);
        inittitle("管理摄影师");
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        tvUpdateCode = (TextView) findViewById(R.id.tv_update_code);
        tvCopy = (TextView) findViewById(R.id.tv_copy);
        tvCode = (TextView) findViewById(R.id.tv_code);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    private void initData() {

    }

    private void initEvent() {

    }
}
