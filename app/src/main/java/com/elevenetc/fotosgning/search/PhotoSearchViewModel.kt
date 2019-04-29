package com.elevenetc.fotosgning.search

import com.elevenetc.fotosgning.common.scheduling.Observable
import com.elevenetc.fotosgning.common.scheduling.Scheduler
import com.elevenetc.fotosgning.search.PhotoSearchViewModel.SearchState

/**
 * Provides two methods for endless fetching photos:
 * - [getFirstPage] loads initial page
 * - [getNextPage] loads subsequent pages
 *
 * Loading state is updated with [updateSearchState]:
 * - [SearchState.IDLE] nothing is loading
 * - [SearchState.LOADING] loading is in progress
 * - [SearchState.ERROR] error during loading, retry with [getNextPage]
 */
class PhotoSearchViewModel(
        private val model: PhotoSearchModel,
        private val scheduler: Scheduler,
        private val viewConfig: ViewConfig
) {

    private val firstPage = 1
    private val emptyResult = PhotoSearchResult(emptyList())

    private var searchResult: PhotoSearchResult = emptyResult
    private var searchState = SearchState.IDLE
    private var page = firstPage
    private var searchStateListener: (state: SearchState) -> Unit = {}

    fun getSearchState(): SearchState {
        return searchState
    }

    fun getFirstPage(query: String): Observable<PhotoSearchResult> {
        return getPage(query, firstPage)
    }

    fun getNextPage(query: String): Observable<PhotoSearchResult> {
        return getPage(query, page + 1)
    }

    fun setSearchStateListener(listener: (state: SearchState) -> Unit) {
        this.searchStateListener = listener
        this.searchStateListener(searchState)
    }

    private fun getPage(query: String, page: Int): Observable<PhotoSearchResult> {

        if (query.isEmpty()) {
            this.page = firstPage
            updateSearchState(SearchState.IDLE)
            return Observable({ emptyResult }, scheduler)
        }

        updateSearchState(SearchState.LOADING)

        return Observable({
            val photos = model.queryPhotos(query, page, viewConfig.photosPerPage)
            PhotoSearchResult(photos)
        }, scheduler).doOnError {
            updateSearchState(SearchState.ERROR)
        }.doOnNext { result ->
            this.page = page
            this.searchResult = result
            updateSearchState(SearchState.IDLE)
        }
    }

    private fun updateSearchState(state: SearchState) {
        this.searchState = state
        this.searchStateListener(state)
    }

    data class PhotoSearchResult(val photos: List<Photo>)

    enum class SearchState {
        IDLE, LOADING, ERROR
    }
}