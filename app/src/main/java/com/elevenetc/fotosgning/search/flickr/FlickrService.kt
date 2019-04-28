package com.elevenetc.fotosgning.search.flickr

import com.elevenetc.fotosgning.network.HttpClient
import com.elevenetc.fotosgning.search.Photo
import com.elevenetc.fotosgning.search.PhotoSearchService
import org.json.JSONObject


class FlickrService(
        private val httpClient: HttpClient,
        private val apiKey: String
) : PhotoSearchService {

    override fun search(query: String, page: Int, count: Int): List<Photo> {
        val url = "https://api.flickr.com/services/rest/"


        val rawJson = httpClient.get(
                url,
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


        val json = JSONObject(rawJson)
        val photos = json.getJSONObject("photos").getJSONArray("photo")
        val list = mutableListOf<Photo>()

        for (i in 0 until photos.length()) {
            val photo = photos.get(i) as JSONObject
            val farm = photo.getString("farm")
            val id = photo.getString("id")
            val owner = photo.getString("owner")
            val server = photo.getString("server")
            val secret = photo.getString("secret")
            list.add(Photo("https://farm$farm.staticflickr.com/$server/${id}_$secret.jpg"))
        }

        return list
    }

}