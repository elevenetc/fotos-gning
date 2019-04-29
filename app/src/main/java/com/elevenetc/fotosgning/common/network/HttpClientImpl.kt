package com.elevenetc.fotosgning.common.network

import android.net.Uri
import java.io.BufferedReader
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class HttpClientImpl : HttpClient {

    private val readTimeout = 3000
    private val connectionTimeout = 3000

    override fun get(url: String, queryParams: List<Pair<String, String>>): String {
        val urlObj = URL(appendQueryParams(url, queryParams))
        val connection = urlObj.openConnection() as HttpURLConnection
        connection.readTimeout = readTimeout
        connection.connectTimeout = connectionTimeout

        //TODO: add response codes handling

        return connection.inputStream.bufferedReader().use(BufferedReader::readText)
    }

    override fun getStream(url: String): InputStream {
        val urlObj = URL(url)
        val connection = urlObj.openConnection()

        connection.readTimeout = readTimeout
        connection.connectTimeout = connectionTimeout

        return connection.getInputStream()
    }

    private fun appendQueryParams(url: String, queryParams: List<Pair<String, String>>): String {
        if (queryParams.isEmpty()) return url

        val builder = Uri.parse(url).buildUpon()
        queryParams.forEach { builder.appendQueryParameter(it.first, it.second) }

        return builder.build().toString()
    }

}