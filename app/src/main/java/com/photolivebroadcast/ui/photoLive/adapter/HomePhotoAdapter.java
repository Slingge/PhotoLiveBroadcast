package com.photolivebroadcast.ui.photoLive.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.photolivebroadcast.R;
import com.photolivebroadcast.ui.MyApplication;
import com.photolivebroadcast.ui.mine.model.MySendModel;
import com.photolivebroadcast.ui.photoLive.activity.EditorPhotoActivity;
import com.photolivebroadcast.ui.photoLive.activity.LiveDetailActivity;

import java.util.ArrayList;

/**
 * Created by zhf on 2018/9/11.
 */

public class HomePhotoAdapter extends RecyclerView.Adapter<HomePhotoAdapter.ViewHolder> {

    private Context context;
    private ArrayList list = new ArrayList<MySendModel.listalbumsModel>();

    public HomePhotoAdapter(Context context, ArrayList<MySendModel.listalbumsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_home_photo, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        MySendModel.listalbumsModel model = (MySendModel.listalbumsModel) list.get(position);
//
//        ImageLoader.getInstance().displayImage(model.getBgimage(), holder.ivBg);
//        holder.tvTitle.setText(model.getTitle());
//        holder.tvName.setText(model.g());

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
//            bundle.putSerializable("model", model);
            MyApplication.openActivity(context, LiveDetailActivity.class, bundle);
        });


        holder.tvEditor.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditorPhotoActivity.class);
//            intent.putExtra("id", model.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
//        return list.size();
        return 2;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivBg;
        private ImageView ivType;
        private TextView tvTitle;
        private TextView tvName;
        private TextView tvLive;
        private TextView tvShare;
        private TextView tvEditor;

        public ViewHolder(View view) {
            super(view);
            ivBg = (ImageView) view.findViewById(R.id.iv_bg);
            ivType = (ImageView) view.findViewById(R.id.iv_type);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvLive = (TextView) view.findViewById(R.id.tv_live);
            tvShare = (TextView) view.findViewById(R.id.tv_share);
            tvEditor = (TextView) view.findViewById(R.id.tv_editor);
        }
    }
}
