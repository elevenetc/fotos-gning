package com.elevenetc.fotosgning.search

import com.elevenetc.fotosgning.common.scheduling.Observable
import com.elevenetc.fotosgning.common.scheduling.Scheduler

class PhotoSearchViewModel(
        private val model: PhotoSearchModel,
        private val scheduler: Scheduler
) {

    var result: PhotoSearchResult = PhotoSearchResult("", 0, 0, emptyList())
    var loadingState = State.IDLE
    var page = 0

    fun getFirstPage(query: String, count: Int): Observable<PhotoSearchResult> {
        return getPage(query, page, count)
    }

    fun getNextPage(query: String, count: Int): Observable<PhotoSearchResult> {
        return getPage(query, ++page, count)
    }

    private fun getPage(query: String, page: Int, count: Int): Observable<PhotoSearchResult> {

        loadingState = State.LOADING

        return Observable({
            val photos = model.queryPhotos(query, page, count)
            result = PhotoSearchResult(query, page, count, photos)
            loadingState = State.IDLE
            result
        }, scheduler).onErrorResumeNext {
            result = PhotoSearchResult(query, page, count, result.photos)
            loadingState = State.ERROR
            result
        }
    }

    data class PhotoSearchResult(
            val query: String,
            val page: Int,
            val photosPerPage: Int,
            val photos: List<Photo>
    )

    enum class State {
        IDLE, LOADING, ERROR
    }
}