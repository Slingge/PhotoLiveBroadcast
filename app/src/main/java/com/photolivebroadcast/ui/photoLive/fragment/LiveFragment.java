package com.photolivebroadcast.ui.photoLive.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xrecyclerview.XRecyclerView;
import com.lixin.amuseadjacent.app.ui.base.BaseFragment;
import com.lxkj.huaihuatransit.app.util.StrCallback;
import com.photolivebroadcast.R;
import com.photolivebroadcast.ui.MyApplication;
import com.photolivebroadcast.ui.dialog.ProgressDialog;
import com.photolivebroadcast.ui.dialog.WatchSettingDialog;
import com.photolivebroadcast.ui.mine.model.MySendModel;
import com.photolivebroadcast.ui.mine.result.MySendHttp;
import com.photolivebroadcast.ui.photoLive.activity.WebViewActivity;
import com.photolivebroadcast.ui.photoLive.adapter.HomePhotoAdapter;
import com.photolivebroadcast.util.StatickUtil;
import com.photolivebroadcast.util.StatusBarBlackWordUtil;
import com.photolivebroadcast.util.StatusBarUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import okhttp3.Call;

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

    private int onRefresh = 0;

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

        rvHome.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                if (!list.isEmpty()) {
                    list.clear();
                    adapter.notifyDataSetChanged();
                }
                onRefresh = 1;
                MySendHttp.INSTANCE.mySend();
            }

            @Override
            public void onLoadMore() {

            }
        });


        tvVideo = (TextView) view.findViewById(R.id.tv_video);
        tvPhoto = (TextView) view.findViewById(R.id.tv_photo);
        ivMessage = (ImageView) view.findViewById(R.id.iv_message);
        ivSearch = (ImageView) view.findViewById(R.id.iv_search);

        if (list.isEmpty()) {
            ProgressDialog.INSTANCE.showDialog(getActivity());
            MySendHttp.INSTANCE.mySend();
        }
    }

    private void initData() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
            }, 1);
        }
    }

    private void initEvent() {
        ivMessage.setOnClickListener(this);
        tvPhoto.setOnClickListener(this);
        tvVideo.setOnClickListener(this);
        ivSearch.setOnClickListener(this);

    }

    @Override
    public void loadData() {

    }

    @Subscribe
    public void onEvent(MySendModel.dataModel moddel) {
        list.addAll(moddel.getListalbums());
        if (onRefresh == 1) {
            rvHome.refreshComplete();
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //视频直播
            case R.id.tv_video:
              /*  tvVideo.setTextColor(getResources().getColor(R.color.white));
                tvVideo.setBackgroundResource(R.drawable.bg_them6);
                tvPhoto.setTextColor(getResources().getColor(R.color.tv_home));
                tvPhoto.setBackgroundResource(R.drawable.bg_white7);*/

                WatchSettingDialog.INSTANCE.dialogEducation(getActivity(), "-1", "", code -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", code);
                    MyApplication.openActivity(getActivity(), WebViewActivity.class, bundle);
                });

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
                search();
                break;
            //消息
            case R.id.iv_message:
                message();
                break;
        }
    }

    private void search() {
        ProgressDialog.INSTANCE.showDialog(getActivity());
        OkHttpUtils.post().addParams("userid", "" + StatickUtil.INSTANCE.getUid())
                .addParams("keywords", "测试")
                .url("http://112.74.169.87/videoCloud/user/user/ajaxsearchalbum").build().execute(new StrCallback() {
            @Override
            public void onError(@NotNull Call call, @NotNull Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(@NotNull String response, int id) {
                super.onResponse(response, id);
            }
        });
    }

    private void message() {
        ProgressDialog.INSTANCE.showDialog(getActivity());
        OkHttpUtils.post().addParams("userid", "" + StatickUtil.INSTANCE.getUid())
                .url("http://www.suxianglive.com/videoCloud/msg/ajaxlistmymessages").build().execute(new StrCallback() {
            @Override
            public void onError(@NotNull Call call, @NotNull Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(@NotNull String response, int id) {
                super.onResponse(response, id);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
