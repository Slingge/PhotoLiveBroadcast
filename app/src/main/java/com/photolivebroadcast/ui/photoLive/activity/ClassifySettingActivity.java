package com.photolivebroadcast.ui.photoLive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import com.lixin.amuseadjacent.app.ui.base.BaseActivity;
import com.photolivebroadcast.R;
import com.photolivebroadcast.ui.photoLive.adapter.ClassifySettingAdapter;
import com.photolivebroadcast.view.MyRecyclerView;

import org.jetbrains.annotations.Nullable;

/**
 * Created by zhf on 2018/9/18.
 */

public class ClassifySettingActivity extends BaseActivity implements View.OnClickListener {

    private MyRecyclerView recyclerView;

    private Button btnSubmit;

    private ClassifySettingAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify_setting);
        inittitle("设置相册分类");
        StatusBarWhiteColor();
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        recyclerView = (MyRecyclerView) findViewById(R.id.rv_classify);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        btnSubmit = findViewById(R.id.btn_submit);
    }

    private void initData() {
        adapter = new ClassifySettingAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void initEvent() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                Intent intent = new Intent(this, AddClassifyActivity.class);
                startActivity(intent);
                break;
        }
    }
}
