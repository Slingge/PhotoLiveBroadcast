package com.photolivebroadcast.ui.mine.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.mine.model.SetMealaAllModel
import com.photolivebroadcast.ui.mine.model.SetMealaMyModel

/**
 * Created by Slingge on 2018/10/9.
 */
class SetMealMyAdapter(val context: Context, val setMealList: ArrayList<SetMealaMyModel.listbuysModel>) : RecyclerView.Adapter<SetMealMyAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_setmeal_my, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        ToastUtil.showToast(setMealList.size.toString())
        return setMealList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model=setMealList[position]

        holder.tv_album.text=model.tcname
        holder.tv_date.text="创建时间："+model.createtime



    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)
        val tv_album = view.findViewById<TextView>(R.id.tv_album)
        val tv_date = view.findViewById<TextView>(R.id.tv_date)
        val tv_surplus = view.findViewById<TextView>(R.id.tv_surplus)
    }


}