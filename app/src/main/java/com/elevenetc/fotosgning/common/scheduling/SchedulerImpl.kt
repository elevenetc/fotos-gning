package com.elevenetc.fotosgning.common.scheduling

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors
import java.util.concurrent.Future

class SchedulerImpl : Scheduler {

    private val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
    private val uiHandler: Handler = Handler(Looper.getMainLooper())

    override fun doOnUi(callback: () -> Unit) {
        uiHandler.post { callback() }
    }

    override fun doOnBackground(callback: () -> Unit): Future<*> {
        return executor.submit(callback)
    }

    override fun isOnUi(): Boolean {
        return Looper.getMainLooper().thread == Thread.currentThread()
    }
}