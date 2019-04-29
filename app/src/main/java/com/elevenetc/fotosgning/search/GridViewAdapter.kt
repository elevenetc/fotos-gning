package com.elevenetc.fotosgning.search

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.elevenetc.fotosgning.common.bitmaps.BitmapsLoader

class GridViewAdapter(
        photos: List<Photo>,
        private val photoSize: Int,
        private val bitmapsLoader:BitmapsLoader
) : BaseAdapter() {

    private val photos = photos.toMutableList()

    fun clear() {
        photos.clear()
        notifyDataSetChanged()
    }

    fun append(photos: List<Photo>) {
        this.photos.addAll(photos)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: ImageView

        if (convertView == null) {
            view = ImageView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(photoSize, photoSize)
            }
        } else {
            view = convertView as ImageView
        }

        val photo = photos[position]

        bitmapsLoader.load(photo.url, view)

        return view
    }

    override fun getItem(position: Int): Any {
        return photos[position]
    }

    override fun getItemId(position: Int): Long {
        return 13
    }

    override fun getCount(): Int {
        return photos.size
    }


}