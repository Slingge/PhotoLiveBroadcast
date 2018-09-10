package com.photolivebroadcast.ui.mine.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.photolivebroadcast.R

/**
 * 推广订单
 * Created by Slingge on 2018/9/8.
 */
class ExtensionOrderAdapter(val context: Context) : RecyclerView.Adapter<ExtensionOrderAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_extension_order, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return 6
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val detailsAdapter = ExtensionOrderDetailsAdapter(context)
        holder.rv_service.adapter = detailsAdapter

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val rv_service = view.findViewById<RecyclerView>(R.id.rv_service)

        init {
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            rv_service.layoutManager = linearLayoutManager
        }

    }


}