package com.photolivebroadcast.ui.mine.adapter

import android.content.Context
import android.media.Image
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.nostra13.universalimageloader.core.ImageLoader
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.mine.model.MySendModel

/**
 * 我发出的
 * Created by Slingge on 2018/9/8.
 */
class MySendAdapter(val context: Context, val list: ArrayList<MySendModel.listalbumsModel>) : RecyclerView.Adapter<MySendAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_mysend, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        ImageLoader.getInstance().displayImage(model.share_img, holder.iv_header)
        holder.tv_name.text = model.title
        holder.tv_info.text=model.remark


    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tv_money = view.findViewById<TextView>(R.id.tv_money)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_info = view.findViewById<TextView>(R.id.tv_info)
        val iv_header = view.findViewById<ImageView>(R.id.iv_header)
    }


}