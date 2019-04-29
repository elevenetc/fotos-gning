package com.elevenetc.fotosgning.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.elevenetc.fotosgning.R
import com.elevenetc.fotosgning.search.PhotoSearchFragment

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
    }
}
