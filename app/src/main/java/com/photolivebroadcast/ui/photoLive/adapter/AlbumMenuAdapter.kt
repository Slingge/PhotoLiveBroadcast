package com.photolivebroadcast.ui.photoLive.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.photoLive.AlbumsClassificationModel

/**
 * 相册分类
 * Created by Slingge on 2018/9/28.
 */
class AlbumMenuAdapter(val context: Context, val menuList: ArrayList<AlbumsClassificationModel.dataModel>) : RecyclerView.Adapter<AlbumMenuAdapter.ViewHolder>() {

    private var flag = 0

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tv_name.text = menuList[position].menu_name

        if (flag == position) {
            holder.tv_name.setTextColor(context.resources.getColor(R.color.black))
            holder.iv_down.visibility = View.VISIBLE
        } else {
            holder.tv_name.setTextColor(context.resources.getColor(R.color.colorTheme))
            holder.iv_down.visibility = View.INVISIBLE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_album_menu, parent, false)
        return ViewHolder(view)
    }


    fun setFlag(flag: Int) {
        this.flag = flag
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val iv_down = view.findViewById<ImageView>(R.id.iv_down)
    }
}