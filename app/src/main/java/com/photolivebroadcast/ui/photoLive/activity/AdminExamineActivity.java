package com.photolivebroadcast.ui.photoLive.activity;

import android.os.Bundle;

import com.lixin.amuseadjacent.app.ui.base.BaseActivity;
import com.photolivebroadcast.R;

import org.jetbrains.annotations.Nullable;

/**
 * Created by zhf on 2018/9/17.
 */

public class AdminExamineActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_examine);
        StatusBarWhiteColor();
        inittitle("管理审核员");
    }
}
