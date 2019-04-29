package com.elevenetc.fotosgning.utils

import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class StubFuture(
        val result: Any = ""
) : Future<Any> {

    private var canceled: Boolean = false

    override fun isCancelled(): Boolean {
        return canceled
    }

    override fun isDone(): Boolean {
        return canceled
    }

    override fun get(): Any {
        return result
    }

    override fun get(timeout: Long, unit: TimeUnit?): Any {
        return result
    }

    override fun cancel(mayInterruptIfRunning: Boolean): Boolean {
        canceled = true
        return true
    }
}