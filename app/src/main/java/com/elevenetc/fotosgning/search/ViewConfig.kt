package com.elevenetc.fotosgning.search

data class ViewConfig(
        val width: Int,
        val height: Int,
        val columns: Int) {

    val photoSize: Int = width / columns
    val photosPerPage: Int = (height / photoSize) * columns + columns
}