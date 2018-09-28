package com.photolivebroadcast.ui.photoLive.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xrecyclerview.XRecyclerView;
import com.lixin.amuseadjacent.app.ui.base.BaseFragment;
import com.photolivebroadcast.R;
import com.photolivebroadcast.ui.Activity;
import com.photolivebroadcast.ui.dialog.ProgressDialog;
import com.photolivebroadcast.ui.establish.result.SendMSMrHttp;
import com.photolivebroadcast.ui.mine.model.MySendModel;
import com.photolivebroadcast.ui.mine.result.MySendHttp;
import com.photolivebroadcast.ui.photoLive.adapter.HomePhotoAdapter;
import com.photolivebroadcast.util.StatusBarBlackWordUtil;
import com.photolivebroadcast.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by zhf on 2018/9/11.
 */

public class LiveFragment extends BaseFragment implements View.OnClickListener {

    private View viewStatue;
    private XRecyclerView rvHome;
    private HomePhotoAdapter adapter;
    private ArrayList list = new ArrayList<MySendModel.listalbumsModel>();
    private TextView tvVideo;
    private TextView tvPhoto;
    private ImageView ivMessage;
    private ImageView ivSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live, null);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onActivityCreated(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        initData();
        initEvent();
    }

    private void initView(View view) {
        if (Build.VERSION.SDK_INT > 19) {
            viewStatue = view.findViewById(R.id.view_staus);
            viewStatue.setVisibility(View.VISIBLE);
            StatusBarUtil.setStutaViewHeight(getActivity(), viewStatue);
            StatusBarUtil.setColorNoTranslucent(getActivity(), getResources().getColor(R.color.white));
            StatusBarBlackWordUtil.StatusBarLightMode(getActivity());
        }

        rvHome = view.findViewById(R.id.rv_home);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvHome.setLayoutManager(linearLayoutManager);

        adapter = new HomePhotoAdapter(getActivity(), list);
        rvHome.setAdapter(adapter);


        tvVideo = (TextView) view.findViewById(R.id.tv_video);
        tvPhoto = (TextView) view.findViewById(R.id.tv_photo);
        ivMessage = (ImageView) view.findViewById(R.id.iv_message);
        ivSearch = (ImageView) view.findViewById(R.id.iv_search);
    }

    private void initData() {

    }

    private void initEvent() {
        ivMessage.setOnClickListener(this);
        tvPhoto.setOnClickListener(this);
//        tvVideo.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
    }

    @Override
    public void loadData() {
        ProgressDialog.INSTANCE.showDialog(getActivity());
        MySendHttp.INSTANCE.mySend();
    }

    @Subscribe
    public void onEvent(MySendModel.dataModel moddel) {
        list.addAll(moddel.getListalbums());
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //视频直播
            case R.id.tv_video:
                tvVideo.setTextColor(getResources().getColor(R.color.white));
                tvVideo.setBackgroundResource(R.drawable.bg_them6);
                tvPhoto.setTextColor(getResources().getColor(R.color.tv_home));
                tvPhoto.setBackgroundResource(R.drawable.bg_white7);
                break;
            //照片直播
            case R.id.tv_photo:
                tvPhoto.setTextColor(getResources().getColor(R.color.white));
                tvPhoto.setBackgroundResource(R.drawable.bg_white9);
                tvVideo.setTextColor(getResources().getColor(R.color.tv_home));
                tvVideo.setBackgroundResource(R.drawable.bg_them8);
                break;
            //搜索
            case R.id.iv_search:
                break;
            //消息
            case R.id.iv_message:
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
