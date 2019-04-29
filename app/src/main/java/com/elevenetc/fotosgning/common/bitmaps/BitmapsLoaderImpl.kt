package com.elevenetc.fotosgning.common.bitmaps

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.elevenetc.fotosgning.common.scheduling.Observable
import com.elevenetc.fotosgning.common.scheduling.Scheduler
import com.elevenetc.fotosgning.common.network.HttpClient
import java.io.BufferedInputStream
import java.io.InputStream

class BitmapsLoaderImpl(
        private val httpClient: HttpClient,
        private val scheduler: Scheduler
) : BitmapsLoader {

    override fun load(url: String, view: ImageView) {

        if (view.tag != null) {
            val tag = (view.tag as TagObject)

            if (tag.url == url) {
                return
            } else {
                view.setImageBitmap(null)
                tag.observable.unsubscribe()
            }
        }

        val observable = load(url)

        view.tag = TagObject(observable, url)

        observable.subscribe({
            view.setImageBitmap(it)
        }, {
            //TODO: add error handling
        })
    }

    private fun load(url: String): Observable<Bitmap> {
        return Observable({

            var stream: InputStream? = null
            var bis: BufferedInputStream? = null

            try {

                stream = httpClient.getStream(url)
                bis = BufferedInputStream(stream)
                val bm = BitmapFactory.decodeStream(stream)
                bis.close()
                stream.close()

                if (bm == null) throw NullPointerException("Bitmap is not loaded")

                bm
            } catch (e: Exception) {
                throw e
            } finally {
                bis?.close()
                stream?.close()
            }
        }, scheduler)
    }

    private data class TagObject(val observable: Observable<Bitmap>, val url: String)
}