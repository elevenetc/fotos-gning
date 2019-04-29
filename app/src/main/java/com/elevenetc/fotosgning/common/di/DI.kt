package com.elevenetc.fotosgning.common.di

import com.elevenetc.fotosgning.AppComponent
import com.elevenetc.fotosgning.search.SearchPhotosComponent

/**
 * Simplified DI setup.
 * Components should be replaced by interfaces
 */
object DI {
    val appComponent by lazy { AppComponent() }
    val searchComponent by lazy { SearchPhotosComponent(appComponent) }
}