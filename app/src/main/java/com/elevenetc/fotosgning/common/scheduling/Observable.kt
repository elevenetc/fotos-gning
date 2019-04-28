package com.elevenetc.fotosgning.common.scheduling

import android.os.Looper
import java.util.concurrent.Future


class Observable<T>(
        private val callable: () -> T,
        private val scheduler: Scheduler
) {

    private var future: Future<*>? = null
    private var errorItem: (() -> T)? = null

    fun subscribe(
            okHandler: (t: T) -> Unit,
            errorHandler: (t: Throwable
            ) -> Unit) {

        if (future != null) throw IllegalStateException()
        if (Thread.currentThread() != Looper.getMainLooper().thread) throw RuntimeException()

        future = scheduler.doOnBackground {

            try {
                val result = callable()
                scheduler.doOnUi {
                    okHandler(result)
                }
            } catch (t: Throwable) {
                if (!future!!.isCancelled) scheduler.doOnUi {

                    if (errorItem != null) {
                        okHandler(errorItem!!())
                    } else {
                        errorHandler(t)
                    }

                }
            }
        }
    }

    fun unsubscribe() {
        future?.cancel(true)
    }

    fun onErrorResumeNext(errorItem: () -> T): Observable<T> {
        this.errorItem = errorItem
        return this
    }

}