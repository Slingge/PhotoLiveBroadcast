package com.photolivebroadcast.ui.mine.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.photolivebroadcast.R;

/**
 * Created by zhf on 2018/7/2.
 * 单选
 */

public class PopPay extends PopupWindow {

    private View view;


    public PopPay(final Context context, View.OnClickListener onClickListener) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_pay, null);
        RelativeLayout relWxPay = view.findViewById(R.id.rel_wx_pay);
        ImageView ivWxPay = view.findViewById(R.id.wx_pay);

        RelativeLayout relAlipay = view.findViewById(R.id.rel_alipay);
        ImageView ivAlipay = view.findViewById(R.id.alipay);

        TextView tvSubmit = view.findViewById(R.id.tv_submit);

        relAlipay.setOnClickListener(onClickListener);
        relWxPay.setOnClickListener(onClickListener);
        tvSubmit.setOnClickListener(onClickListener);

//        //显示弹出框
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        this.setWidth((int) (MyApplication.Width * 0.25));
//        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置弹窗可点击
        this.setFocusable(true);
        //设置弹出窗体的动画效果
        this.setAnimationStyle(R.style.AppTheme);
//        this.setAnimationStyle(R.style.popwin_fade_style);
        ColorDrawable cd = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(cd);

//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                dismiss();
//                return true;
//            }
//        });
    }
}
