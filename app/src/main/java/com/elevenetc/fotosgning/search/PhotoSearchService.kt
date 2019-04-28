package com.elevenetc.fotosgning.search

interface PhotoSearchService {
    fun search(query: String, page:Int, count:Int): List<Photo>
}