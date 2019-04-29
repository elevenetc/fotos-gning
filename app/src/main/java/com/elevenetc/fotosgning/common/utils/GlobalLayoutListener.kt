package com.elevenetc.fotosgning.common.utils

import android.view.View
import android.view.ViewTreeObserver

class GlobalLayoutListener(
        private val view: View,
        private val onLayout: () -> Unit) : ViewTreeObserver.OnGlobalLayoutListener {

    init {
        view.viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        view.viewTreeObserver.removeOnGlobalLayoutListener(this)
        onLayout()
    }
}