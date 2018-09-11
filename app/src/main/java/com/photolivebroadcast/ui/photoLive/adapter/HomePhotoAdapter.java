package com.photolivebroadcast.ui.photoLive.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.photolivebroadcast.R;
import com.photolivebroadcast.ui.photoLive.activity.EditorPhotoActivity;

/**
 * Created by zhf on 2018/9/11.
 */

public class HomePhotoAdapter extends RecyclerView.Adapter<HomePhotoAdapter.ViewHolder> {

    private Context context;

    public HomePhotoAdapter(Context context) {
        this.context = context;
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
        holder.tvEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditorPhotoActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
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
