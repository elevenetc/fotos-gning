package com.elevenetc.fotosgning.common.bitmaps

import android.widget.ImageView

interface BitmapsLoader {
    fun load(url: String, view: ImageView)
}