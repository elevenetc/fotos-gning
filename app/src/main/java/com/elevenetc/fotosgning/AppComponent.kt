package com.elevenetc.fotosgning

import com.elevenetc.fotosgning.common.bitmaps.BitmapsLoaderImpl
import com.elevenetc.fotosgning.common.network.HttpClientImpl
import com.elevenetc.fotosgning.common.scheduling.SchedulerImpl

class AppComponent {
    val httpClient by lazy { HttpClientImpl() }
    val scheduler by lazy { SchedulerImpl() }
    val bitmapsLoader by lazy { BitmapsLoaderImpl(httpClient, scheduler) }
}