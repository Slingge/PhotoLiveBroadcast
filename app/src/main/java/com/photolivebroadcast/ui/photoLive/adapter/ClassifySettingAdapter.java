package com.photolivebroadcast.ui.photoLive.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.photolivebroadcast.R;
import com.photolivebroadcast.ui.dialog.EditDialog;
import com.photolivebroadcast.ui.dialog.ProgressDialog;
import com.photolivebroadcast.ui.photoLive.AlbumsClassificationModel;
import com.photolivebroadcast.ui.photoLive.http.AlbumsClassificationHttp;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by zhf on 2018/9/18.
 */

public class ClassifySettingAdapter extends RecyclerView.Adapter<ClassifySettingAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<AlbumsClassificationModel.dataModel> menuList;

    public ClassifySettingAdapter(Activity context, ArrayList<AlbumsClassificationModel.dataModel> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_classify_setting, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_watermark.setText(menuList.get(position).getMenu_name());

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog.INSTANCE.showDialog(context);
                AlbumsClassificationHttp.INSTANCE.DelClassification(menuList.get(position).getId(), new AlbumsClassificationHttp.DelClassificationCallBack() {
                    @Override
                    public void delClassification() {
                        menuList.remove(position);
                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.iv_editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditDialog.INSTANCE.communityDialog(context, new EditDialog.EditTextCallBack() {
                    @Override
                    public void editText(@NotNull String name) {
                        ProgressDialog.INSTANCE.showDialog(context);
                        AlbumsClassificationHttp.INSTANCE.EditClassification(menuList.get(position).getId(), name, new AlbumsClassificationHttp.DelClassificationCallBack() {
                            @Override
                            public void delClassification() {
                                menuList.get(position).setMenu_name(name);
                                notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_watermark;
        ImageView iv_editor;
        ImageView iv_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_watermark = itemView.findViewById(R.id.tv_watermark);
            iv_editor = itemView.findViewById(R.id.iv_editor);
            iv_delete = itemView.findViewById(R.id.iv_delete);
        }
    }
}
