package com.rutulkotak.albums

import android.app.Application
import com.rutulkotak.albums.data.AlbumRepository
import timber.log.Timber

class AlbumsApplication : Application() {

    val albumRepository: AlbumRepository
        get() = ServiceLocator.provideAlbumRepository(this)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}