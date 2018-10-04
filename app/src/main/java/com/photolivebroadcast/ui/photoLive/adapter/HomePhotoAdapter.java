package com.photolivebroadcast.ui.photoLive.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxkj.linxintechnologylibrary.app.util.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.photolivebroadcast.R;
import com.photolivebroadcast.ui.MyApplication;
import com.photolivebroadcast.ui.mine.model.MySendModel;
import com.photolivebroadcast.ui.photoLive.activity.EditorPhotoActivity;
import com.photolivebroadcast.ui.photoLive.activity.LiveDetailActivity;
import com.photolivebroadcast.ui.photoLive.activity.CloudSeedingActivity;
import com.photolivebroadcast.util.StatickUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.util.ArrayList;

/**
 * Created by zhf on 2018/9/11.
 */

public class HomePhotoAdapter extends RecyclerView.Adapter<HomePhotoAdapter.ViewHolder> {

    private Activity context;
    private ArrayList list = new ArrayList<MySendModel.listalbumsModel>();

    private int pid;

    public HomePhotoAdapter(Activity context, ArrayList<MySendModel.listalbumsModel> list) {
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
        MySendModel.listalbumsModel model = (MySendModel.listalbumsModel) list.get(position);

        ImageLoader.getInstance().displayImage(model.getBgimage(), holder.ivBg);
        holder.tvTitle.setText(model.getTitle());
//        holder.tvName.setText(model.g());

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("model", model);
            bundle.putString("id", model.getId() + "");
            MyApplication.openActivity(context, LiveDetailActivity.class, bundle);
        });


        holder.tvEditor.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", model.getId() + "");
            MyApplication.openActivity(context, EditorPhotoActivity.class, bundle);
        });

        holder.tvShare.setOnClickListener(v -> {
            pid = model.getId();
            SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                    {SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE/*,
                            SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE*/
                    };
            new ShareAction(context).setDisplayList(displaylist)
                    .setShareboardclickCallback(shareBoardlistener).open();
        });

        holder.tvLive.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", model.getId() + "");
            MyApplication.openActivity(context, CloudSeedingActivity.class, bundle);
        });
    }


    //分享面板的点击事件
    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {
        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            UMImage image = new UMImage(context, R.mipmap.ic_launcher);//网络图片
            UMWeb web = new UMWeb(StatickUtil.INSTANCE.getShareUrl() + pid);
            web.setTitle("速享直播");
            web.setDescription("我的相册");
            web.setThumb(image);
            new ShareAction(context).setPlatform(share_media).setCallback(umShareListener)
                    .withMedia(web)
                    .share();
        }
    };


    //分享的回调
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastUtil.INSTANCE.showToast("分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            ToastUtil.INSTANCE.showToast("分享失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            ToastUtil.INSTANCE.showToast("取消分享");
        }
    };


    @Override
    public int getItemCount() {
        return list.size();
//        return 2;
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
