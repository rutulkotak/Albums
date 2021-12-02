package com.rutulkotak.albums

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rutulkotak.albums.AlbumsApplication
import com.rutulkotak.albums.R
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
        setContentView(R.layout.activity_main)
    }

}