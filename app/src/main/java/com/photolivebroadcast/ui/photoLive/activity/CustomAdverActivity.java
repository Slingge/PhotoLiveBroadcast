package com.photolivebroadcast.ui.photoLive.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lixin.amuseadjacent.app.ui.base.BaseActivity;
import com.photolivebroadcast.R;

import org.jetbrains.annotations.Nullable;

/**
 * Created by zhf on 2018/9/18.
 */

public class CustomAdverActivity extends BaseActivity {
    private EditText etTitle;
    private RadioGroup radio;
    private RadioButton rbPhoto;
    private RadioButton rbReturn;
    private RadioButton rbDial;
    private EditText etLink;
    private Button btnAdd;
    private Button btnKeep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_adver);
        inittitle("自定义广告设置");
        StatusBarWhiteColor();
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        etTitle = (EditText) findViewById(R.id.et_title);
        radio = (RadioGroup) findViewById(R.id.radio);
        rbPhoto = (RadioButton) findViewById(R.id.rb_photo);
        rbReturn = (RadioButton) findViewById(R.id.rb_return);
        rbDial = (RadioButton) findViewById(R.id.rb_dial);
        etLink = (EditText) findViewById(R.id.et_link);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnKeep = (Button) findViewById(R.id.btn_keep);
    }

    private void initData() {

    }

    private void initEvent() {

    }
}
