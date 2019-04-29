package com.elevenetc.fotosgning.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.GridView
import android.widget.Toast
import com.elevenetc.fotosgning.R
import com.elevenetc.fotosgning.common.di.DI
import com.elevenetc.fotosgning.common.scheduling.Observable
import com.elevenetc.fotosgning.common.utils.ChangedTextListener
import com.elevenetc.fotosgning.common.utils.EndScrollListener
import com.elevenetc.fotosgning.common.utils.GlobalLayoutListener

class PhotoSearchFragment : Fragment() {

    init {
        retainInstance = true
    }

    private val columns = 3

    private lateinit var viewModel: PhotoSearchViewModel
    private lateinit var adapter: GridViewAdapter
    private lateinit var btnRetry: View
    private lateinit var progressView: View
    private lateinit var gridView: GridView
    private lateinit var editSearch: EditText

    private lateinit var viewConfig: ViewConfig

    private var observable: Observable<PhotoSearchViewModel.PhotoSearchResult>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_search_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        gridView = view.findViewById(R.id.grid_view)
        btnRetry = view.findViewById(R.id.btn_retry)
        progressView = view.findViewById(R.id.progress_view)
        editSearch = view.findViewById(R.id.edit_search)


        GlobalLayoutListener(gridView) {

            viewConfig = ViewConfig(gridView.width, gridView.height, columns)
            viewModel = DI.searchComponent.provideViewModel(viewConfig)

            initViews()

            restoreState(savedInstanceState)
        }
    }

    override fun onDestroyView() {
        observable?.unsubscribe()
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(STATE_SEARCH_QUERY, editSearch.text.toString())
    }

    private fun onEditSearchChanged(query: String) {
        loadFirstPage(query)
    }

    private fun onEndScroll() {
        loadNextPage()
    }

    private fun loadFirstPage(query: String) {
        observable?.unsubscribe()
        adapter.clear()
        subscribeToSearch(viewModel.getFirstPage(query))
    }

    private fun loadNextPage() {
        if (viewModel.getSearchState() == PhotoSearchViewModel.SearchState.LOADING) return
        val query = editSearch.text.toString()
        subscribeToSearch(viewModel.getNextPage(query))
    }

    private fun initViews() {

        gridView.numColumns = viewConfig.columns
        gridView.setOnScrollListener(EndScrollListener { onEndScroll() })

        adapter = GridViewAdapter(emptyList(), viewConfig.photoSize, DI.appComponent.bitmapsLoader)
        gridView.adapter = adapter

        viewModel.setSearchStateListener { onSearchStateChanged(it) }

        editSearch.addTextChangedListener(ChangedTextListener { onEditSearchChanged(it) })

        btnRetry.setOnClickListener { loadNextPage() }
    }

    private fun onSearchStateChanged(state: PhotoSearchViewModel.SearchState) {
        when (state) {
            PhotoSearchViewModel.SearchState.IDLE -> {
                btnRetry.visibility = View.GONE
                progressView.visibility = View.GONE
            }
            PhotoSearchViewModel.SearchState.LOADING -> {
                btnRetry.visibility = View.GONE
                progressView.visibility = View.VISIBLE
            }
            PhotoSearchViewModel.SearchState.ERROR -> {
                btnRetry.visibility = View.VISIBLE
                progressView.visibility = View.GONE
            }
        }
    }

    private fun subscribeToSearch(observable: Observable<PhotoSearchViewModel.PhotoSearchResult>) {
        this.observable = observable.subscribe({ result ->
            adapter.append(result.photos)
        }, {
            Toast.makeText(context, R.string.error_loading_photoes, Toast.LENGTH_SHORT).show()
        })
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val query = savedInstanceState.getString(STATE_SEARCH_QUERY)!!
            onEditSearchChanged(query)
        }
    }

    companion object {
        const val STATE_SEARCH_QUERY = "searchQuery"
    }
}