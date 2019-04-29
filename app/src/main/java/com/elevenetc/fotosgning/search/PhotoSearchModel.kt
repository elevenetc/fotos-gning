package com.elevenetc.fotosgning.search

interface PhotoSearchModel {
    fun queryPhotos(query: String, page: Int, count: Int): List<Photo>
}