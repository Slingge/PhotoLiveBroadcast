package com.photolivebroadcast.ui.mine.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.mine.model.MyMappingServiceModel

/**
 * 修图
 * Created by Slingge on 2018/9/8.
 */
class MappingServiceAdapter(val context: Context, val listxiutus: ArrayList<MyMappingServiceModel.listxiutusModel>) : RecyclerView.Adapter<MappingServiceAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_maping, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listxiutus.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model=listxiutus[position]
        holder.tv_time.text="修图时间"+model.createtime
        holder.tv_info.text=model.remark
        holder.tv_num.text=model.photonum.toString()+"张"

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tv_num = view.findViewById<TextView>(R.id.tv_num)
        val tv_time = view.findViewById<TextView>(R.id.tv_time)

        val tv_info = view.findViewById<TextView>(R.id.tv_info)
        val tv_edit = view.findViewById<TextView>(R.id.tv_edit)
    }


}