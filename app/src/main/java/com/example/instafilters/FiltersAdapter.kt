package com.example.instafilters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class FiltersAdapter(val items: ArrayList<FilterItem>, val callback: FilterCallback, val context: Context): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageBitmap(items.get(position).image)
        holder.caption.text = items[position].text
        holder.layout.setOnClickListener { callback.onSelected(position) }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image = itemView.ivFilterItem
    val caption = itemView.tvFilterName
    val layout = itemView.clItem
}

interface FilterCallback{
    fun onSelected(index: Int)
}