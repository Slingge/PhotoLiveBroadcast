package com.photolivebroadcast.ui.mine.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.photolivebroadcast.R

/**
 * 我
 * Created by Slingge on 2018/9/8.
 */
class ExtensionTeamAdapter(val context: Context) : RecyclerView.Adapter<ExtensionTeamAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_extension_team, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return 6
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {



    }


}