package com.elevenetc.fotosgning

import com.elevenetc.fotosgning.common.scheduling.Observable
import com.elevenetc.fotosgning.utils.CurrentThreadScheduler
import org.junit.Assert
import org.junit.Test

class TestObservable {
    @Test
    fun success() {

        val result = "a"
        val observable = Observable({ result }, CurrentThreadScheduler())

        observable.subscribe({
            Assert.assertEquals(result, it)
        })
    }

    @Test
    fun error() {

        val exception = RuntimeException()
        val observable = Observable({ throw exception }, CurrentThreadScheduler())

        observable.subscribe({

        }, {
            Assert.assertEquals(exception, it)
        })
    }
}