package com.elevenetc.fotosgning.search.flickr

import com.elevenetc.fotosgning.search.Photo
import org.json.JSONObject

class FlickrMapper {
    fun mapPhoto(photo: JSONObject): Photo {
        val farm = photo.getString("farm")
        val id = photo.getString("id")
        val server = photo.getString("server")
        val secret = photo.getString("secret")
        val photoUrl = "https://farm$farm.staticflickr.com/$server/${id}_${secret}_q.jpg"
        return Photo(photoUrl)
    }

    fun mapPhotosList(json: JSONObject): List<Photo> {

        val photos = json.getJSONObject("photos").getJSONArray("photo")
        val result = mutableListOf<Photo>()

        for (i in 0 until photos.length()) {
            val photo = photos.get(i) as JSONObject
            result.add(mapPhoto(photo))
        }

        return result
    }
}