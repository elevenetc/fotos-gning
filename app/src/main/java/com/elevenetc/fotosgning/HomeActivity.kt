package com.elevenetc.fotosgning

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.elevenetc.fotosgning.search.PhotoSearchFragment
import com.elevenetc.fotosgning.search.flickr.FlickrService

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.home_container, PhotoSearchFragment())
                    .commit()
        }

//        Thread({
//            val search = FlickrService().search("")
//            Log.d("search", search[0].url)
//        }).start()
    }
}
