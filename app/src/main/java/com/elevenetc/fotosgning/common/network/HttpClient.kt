package com.elevenetc.fotosgning.common.network

import java.io.InputStream

interface HttpClient {
    fun get(url: String, queryParams: List<Pair<String, String>>): String
    fun getStream(url: String): InputStream
}