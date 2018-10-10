package com.photolivebroadcast.ui.photoLive.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lixin.amuseadjacent.app.ui.base.BaseActivity;
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil;
import com.photolivebroadcast.R;
import com.photolivebroadcast.ui.photoLive.http.AddClunmHttp;

import org.jetbrains.annotations.Nullable;

/**
 * Created by zhf on 2018/9/18.
 */

public class AddColumnActivity extends BaseActivity {

    private EditText etContent;
    private Button btnSubmit;

    private String pId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_classify);


        pId = getIntent().getStringExtra("id");

        StatusBarWhiteColor();
        initView();
        initData();
    }

    private void initView() {
        etContent = findViewById(R.id.et_content);
        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etContent.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.INSTANCE.showToast("请输入内容");
                    return;
                }
                AddClunmHttp.INSTANCE.addClunm(AddColumnActivity.this, pId,content);
            }
        });
    }

    private void initData() {

    }
}
