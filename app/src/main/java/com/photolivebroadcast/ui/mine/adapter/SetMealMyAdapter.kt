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
        val view = LayoutInflater.from(context).inflate(R.layout.item_setmeal, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        ToastUtil.showToast(setMealList.size.toString())
        return setMealList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model=setMealList[position]



//        holder.tv_album5.text=model.remark
//        holder.tv_money5.text="￥"+model.price

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)
        val tv_album5 = view.findViewById<TextView>(R.id.tv_album5)
        val tv_money5 = view.findViewById<TextView>(R.id.tv_money5)
        val tv_pay5 = view.findViewById<TextView>(R.id.tv_pay5)
    }


}