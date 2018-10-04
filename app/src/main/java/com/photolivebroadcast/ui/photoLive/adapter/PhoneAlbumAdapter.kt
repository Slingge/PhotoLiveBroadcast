package com.photolivebroadcast.ui.photoLive.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.photoLive.model.UpAlbunmModel
import com.photolivebroadcast.util.ImageFileUtil
import com.yalantis.ucrop.util.FileUtils

/**
 * Created by Slingge on 2018/9/29.
 */
class PhoneAlbumAdapter(val context: Context, val list: ArrayList<UpAlbunmModel>) : RecyclerView.Adapter<PhoneAlbumAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_phone_album, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageBitmap(ImageFileUtil.getBitmapFromPath(list[position].path))
        if(list[position].isAdopt==0){
            holder.image.setImageResource(R.drawable.ic_adopt)
            holder.image.visibility=View.VISIBLE
        }else if(list[position].isAdopt==1){
            holder.image.setImageResource(R.drawable.ic_adopt_not)
            holder.image.visibility=View.VISIBLE
        }else{
            holder.image.visibility=View.GONE
        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val image = view.findViewById<ImageView>(R.id.image)

    }


}