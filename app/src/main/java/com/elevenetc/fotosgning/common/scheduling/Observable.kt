package com.elevenetc.fotosgning.common.scheduling

import java.util.concurrent.Future


/**
 * Simplified RxJava-like Observable class.
 * Limitations:
 *  - Work only on Main thread
 *  - Handles only single result
 */
class Observable<T>(
        private val callable: () -> T,
        private val scheduler: Scheduler
) {

    private var future: Future<*>? = null
    private var onErrorItem: (() -> T)? = null
    private var doOnError: ((t: Throwable) -> Unit)? = null
    private var doOnComplete: (() -> Unit)? = null
    private var doOnNext: ((T) -> Unit)? = null

    @Volatile
    private var completed = false

    fun subscribe(
            okHandler: (t: T) -> Unit,
            errorHandler: (t: Throwable) -> Unit = {}): Observable<T> {

        if (future != null) throw IllegalStateException("Single subscription only allowed")
        checkMainThread()

        future = scheduler.doOnBackground {

            try {
                val result = callable()
                scheduler.doOnUi {
                    deliverResult(okHandler, result)
                }
            } catch (t: Throwable) {
                if (!completed) scheduler.doOnUi {
                    deliverError(t, okHandler, errorHandler)
                }
            }
        }

        return this
    }

    fun unsubscribe() {
        checkMainThread()
        future?.cancel(true)
        setCompleted()
    }

    fun onErrorResumeNext(errorItem: () -> T): Observable<T> {
        this.onErrorItem = errorItem
        return this
    }

    fun doOnError(doOnError: (t: Throwable) -> Unit): Observable<T> {
        this.doOnError = doOnError
        return this
    }

    fun doOnComplete(doOnComplete: () -> Unit): Observable<T> {
        this.doOnComplete = doOnComplete
        return this
    }

    fun doOnNext(doOnNext: (T) -> Unit): Observable<T> {
        this.doOnNext = doOnNext
        return this
    }

    private fun checkMainThread() {
        if (!scheduler.isOnUi()) throw RuntimeException("Main thread subscription only allowed.")
    }

    private fun deliverResult(okHandler: (t: T) -> Unit, result: T) {
        okHandler(result)
        if (doOnNext != null) doOnNext!!(result)
        if (doOnComplete != null) doOnComplete!!()
        setCompleted()
    }

    private fun deliverError(t: Throwable, okHandler: (t: T) -> Unit, errorHandler: (t: Throwable) -> Unit) {
        if (doOnError != null) doOnError!!(t)
        if (onErrorItem != null) okHandler(onErrorItem!!())
        else errorHandler(t)
        setCompleted()
    }

    private fun setCompleted() {
        completed = true
    }


}