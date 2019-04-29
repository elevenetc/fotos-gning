package com.elevenetc.fotosgning.search.flickr

import com.elevenetc.fotosgning.common.network.HttpClient
import com.elevenetc.fotosgning.search.Photo
import com.elevenetc.fotosgning.search.PhotoSearchService
import org.json.JSONObject


class FlickrService(
        private val httpClient: HttpClient,
        private val apiKey: String
) : PhotoSearchService {

    private val baseUrl = "https://api.flickr.com/services/rest/"
    private val mapper = FlickrMapper()

    override fun search(query: String, page: Int, count: Int): List<Photo> {

        val rawJson = httpClient.get(
                baseUrl,
                listOf(
                        Pair("method", "flickr.photos.search"),
                        Pair("api_key", apiKey),
                        Pair("format", "json"),
                        Pair("nojsoncallback", "1"),
                        Pair("safe_search", "1"),
                        Pair("text", query),
                        Pair("per_page", count.toString()),
                        Pair("page", page.toString())
                )
        )

        return mapper.mapPhotosList(JSONObject(rawJson))
    }

}