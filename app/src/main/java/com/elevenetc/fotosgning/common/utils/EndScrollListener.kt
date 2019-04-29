package com.elevenetc.fotosgning.common.utils

import android.widget.AbsListView

class EndScrollListener(val listener: () -> Unit) : AbsListView.OnScrollListener {
    override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        val lastInScreen = firstVisibleItem + visibleItemCount

        if (totalItemCount > 0 && lastInScreen == totalItemCount) {
            listener()
        }
    }

    override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {

    }
}