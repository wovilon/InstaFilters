package com.example.instafilters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_filter.view.*

class FilterAdapter(val filterItems :  ArrayList<FilterItem>, val context : Context) : RecyclerView.Adapter<ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_filter, parent, false))
    }

    override fun getItemCount(): Int {
        return filterItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageBitmap(filterItems[position].bitmap)
        holder.name.text = filterItems[position].name
    }

}


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val image = itemView.ivFilter
    val name = itemView.tvFilterName
}