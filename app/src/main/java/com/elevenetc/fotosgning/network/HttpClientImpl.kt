package com.elevenetc.fotosgning.network

import android.net.Uri
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL

class HttpClientImpl : HttpClient {

    override fun get(url: String, queryParams: List<Pair<String, String>>): String {
        val urlObj = URL(appendQueryParams(url, queryParams))
        val connection = urlObj.openConnection() as HttpURLConnection
        val responseCode = connection.responseCode
        return connection.inputStream.bufferedReader().use(BufferedReader::readText)
    }

    private fun appendQueryParams(url: String, queryParams: List<Pair<String, String>>): String {
        if (queryParams.isEmpty()) return url

        val builder = Uri.parse(url).buildUpon()
        queryParams.forEach { builder.appendQueryParameter(it.first, it.second) }

        return builder.build().toString()
    }

}