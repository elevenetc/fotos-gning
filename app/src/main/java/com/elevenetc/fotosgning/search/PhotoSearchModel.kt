package com.elevenetc.fotosgning.search

class PhotoSearchModel(
        private val photoSearchService: PhotoSearchService
) {
    fun queryPhotos(query: String, page:Int, count:Int): List<Photo> {
        return photoSearchService.search(query, page, count)
    }
}