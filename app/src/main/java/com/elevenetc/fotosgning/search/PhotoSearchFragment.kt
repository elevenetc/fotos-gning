package com.elevenetc.fotosgning.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.GridView
import com.elevenetc.fotosgning.R
import com.elevenetc.fotosgning.common.scheduling.Observable
import com.elevenetc.fotosgning.common.scheduling.Scheduler
import com.elevenetc.fotosgning.network.HttpClientImpl
import com.elevenetc.fotosgning.search.flickr.FlickrService

class PhotoSearchFragment : Fragment() {

    init {
        retainInstance = true
    }

    val viewModel by lazy { PhotoSearchViewModel(PhotoSearchModel(FlickrService(HttpClientImpl(), "3e7cc266ae2b0e0d78e279ce8e361736")), Scheduler()) }
    var observable: Observable<PhotoSearchViewModel.PhotoSearchResult>? = null
    var searchResult: PhotoSearchViewModel.PhotoSearchResult? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_search_photo, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val grid = view.findViewById<GridView>(R.id.grid_view)
        grid.numColumns = 3
        grid.setOnScrollListener(object : AbsListView.OnScrollListener {

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {

            }

            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                val lastInScreen = firstVisibleItem + visibleItemCount

                if (lastInScreen == totalItemCount) {
                    Log.d("fragment", "end scroll")
                    loadNextPage()
                }
            }

        })

        grid.adapter = GridViewAdapter(emptyList())

        observable = viewModel.getFirstPage("dog", 30)
        handleObs()
    }

    private fun loadNextPage() {

        if (viewModel.loadingState == PhotoSearchViewModel.State.LOADING) return

        Log.d("fragment", "load next page")

        observable = viewModel.getNextPage("dog", 30)
        handleObs()
    }

    private fun handleObs() {

        val grid = view!!.findViewById<GridView>(R.id.grid_view)

        observable!!.subscribe({

            (grid.adapter as GridViewAdapter).append(it.photos)

            Log.d("search", "success delivered to ui")
        }, {
            Log.d("search", "error delivered $it to ui")
        })
    }

    override fun onDestroyView() {
        observable?.unsubscribe()
        super.onDestroyView()
    }
}