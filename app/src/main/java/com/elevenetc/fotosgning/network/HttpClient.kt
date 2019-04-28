package com.elevenetc.fotosgning.network

interface HttpClient {
    fun get(url: String, queryParams: List<Pair<String, String>>): String
}