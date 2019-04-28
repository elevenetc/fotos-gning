package com.elevenetc.fotosgning.search

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class GridViewAdapter(photos: List<Photo>) : BaseAdapter() {

    val photos = photos.toMutableList()

    fun append(photos: List<Photo>) {
        this.photos.addAll(photos)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view: TextView

        if (convertView == null) {
            view = TextView(parent.context)
        } else {
            view = convertView as TextView
        }

        view.text = photos[position].url

        return view
    }

    override fun getItem(position: Int): Any {
        return ""
    }

    override fun getItemId(position: Int): Long {
        return 666
    }

    override fun getCount(): Int {
        return photos.size
    }
}