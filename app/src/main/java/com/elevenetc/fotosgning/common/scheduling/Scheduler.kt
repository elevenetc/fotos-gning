package com.elevenetc.fotosgning.common.scheduling

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors
import java.util.concurrent.Future

class Scheduler {

    private val executor = Executors.newFixedThreadPool(1)
    private val uiHandler: Handler = Handler(Looper.getMainLooper())

    fun doOnUi(u: () -> Unit) {
        uiHandler.post { u() }
    }

    fun doOnBackground(u: () -> Unit): Future<*> {
        return executor.submit(u)
    }
}