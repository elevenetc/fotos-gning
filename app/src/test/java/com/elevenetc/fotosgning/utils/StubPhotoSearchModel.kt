package com.elevenetc.fotosgning.utils

import com.elevenetc.fotosgning.search.Photo
import com.elevenetc.fotosgning.search.PhotoSearchModel

class StubPhotoSearchModel(
        private val result: Map<String, List<Photo>>
) : PhotoSearchModel {

    override fun queryPhotos(query: String, page: Int, count: Int): List<Photo> {
        val maxIndex = (page - 1) * count + count
        return if (result.containsKey(query) && maxIndex <= result.getValue(query).size) {

            val photos = result[query]
            val from = (page - 1) * count
            val to = from + count
            photos!!.subList(from, to)
        } else {
            emptyList()
        }
    }

}