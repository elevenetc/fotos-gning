package com.elevenetc.fotosgning.search

import com.elevenetc.fotosgning.AppComponent
import com.elevenetc.fotosgning.BuildConfig
import com.elevenetc.fotosgning.search.flickr.FlickrService

class SearchPhotosComponent(private val appComponent: AppComponent) {

    fun provideViewModel(viewConfig: ViewConfig): PhotoSearchViewModel {
        return PhotoSearchViewModel(provideModel(), appComponent.scheduler, viewConfig)
    }

    private fun provideModel(): PhotoSearchModel {
        return PhotoSearchModelImpl(provideSearchService())
    }

    private fun provideSearchService(): PhotoSearchService {
        return FlickrService(appComponent.httpClient, BuildConfig.FLICKR_API_KEY)
    }
}