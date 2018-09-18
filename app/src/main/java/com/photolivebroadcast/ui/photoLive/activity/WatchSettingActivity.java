package com.photolivebroadcast.ui.photoLive.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lixin.amuseadjacent.app.ui.base.BaseActivity;
import com.photolivebroadcast.R;

import org.jetbrains.annotations.Nullable;

/**
 * Created by zhf on 2018/9/17.
 */

public class WatchSettingActivity extends BaseActivity {

    private TextView tvRight;

    private RadioGroup radio;
    private RadioButton rbPublic;
    private RadioButton rbPay;
    private RadioButton rbPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_setting);
        inittitle("观看设置");
        StatusBarWhiteColor();
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        tvRight = findViewById(R.id.tv_right);
        tvRight.setText("提交");
        tvRight.setVisibility(View.VISIBLE);
        radio = (RadioGroup) findViewById(R.id.radio);
        rbPublic = (RadioButton) findViewById(R.id.rb_public);
        rbPay = (RadioButton) findViewById(R.id.rb_pay);
        rbPwd = (RadioButton) findViewById(R.id.rb_pwd);
    }

    private void initData() {

    }

    private void initEvent() {

    }
}
