package com.elevenetc.fotosgning.utils

import com.elevenetc.fotosgning.common.scheduling.Scheduler
import java.util.concurrent.Future

class CurrentThreadScheduler : Scheduler {

    override fun doOnUi(callback: () -> Unit) {
        callback()
    }

    override fun doOnBackground(callback: () -> Unit): Future<*> {
        callback()
        return StubFuture()
    }

    override fun isOnUi(): Boolean {
        return true
    }

}