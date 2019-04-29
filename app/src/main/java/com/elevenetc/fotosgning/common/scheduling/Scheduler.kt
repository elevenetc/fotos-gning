package com.elevenetc.fotosgning.common.scheduling

import java.util.concurrent.Future

interface Scheduler {
    fun doOnUi(callback: () -> Unit)
    fun doOnBackground(callback: () -> Unit): Future<*>
    fun isOnUi():Boolean
}