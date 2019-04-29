package com.elevenetc.fotosgning.search

class PhotoSearchModelImpl(
        private val photoSearchService: PhotoSearchService
) : PhotoSearchModel {
    override fun queryPhotos(query: String, page:Int, count:Int): List<Photo> {
        return photoSearchService.search(query, page, count)
    }
}