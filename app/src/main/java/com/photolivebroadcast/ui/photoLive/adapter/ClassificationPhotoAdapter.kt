package com.photolivebroadcast.ui.photoLive.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.nostra13.universalimageloader.core.ImageLoader
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.photoLive.model.ClassificationPhotoModel

/**
 * Created by Slingge on 2018/10/3.
 */
class ClassificationPhotoAdapter(val context: Context, val menuPhotoList: ArrayList<ClassificationPhotoModel.recordsModel>) : RecyclerView.Adapter<ClassificationPhotoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_squareimage, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menuPhotoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        ImageLoader.getInstance().displayImage(menuPhotoList[position].imgurl, holder.image)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val image = view.findViewById<ImageView>(R.id.image)

    }

}