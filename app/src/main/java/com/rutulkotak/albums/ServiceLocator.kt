package com.rutulkotak.albums

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.rutulkotak.albums.data.AlbumRepository
import com.rutulkotak.albums.data.DefaultAlbumRepository
import com.rutulkotak.albums.data.source.local.AlbumsDatabase
import com.rutulkotak.albums.data.source.local.AlbumsLocalDataSource
import com.rutulkotak.albums.data.source.remote.AlbumsRemoteDataSource
import kotlinx.coroutines.runBlocking

object ServiceLocator {

    private val lock = Any()
    private var database: AlbumsDatabase? = null
    @Volatile
    var albumRepository: AlbumRepository? = null
        @VisibleForTesting set

    fun provideAlbumRepository(context: Context): AlbumRepository {
        synchronized(this) {
            return albumRepository ?: createAlbumRepository(context)
        }
    }

    private fun createAlbumRepository(context: Context): AlbumRepository {
        val newRepo = DefaultAlbumRepository(AlbumsRemoteDataSource(), createAlbumLocalDataSource(context))
        albumRepository = newRepo
        return newRepo
    }

    private fun createAlbumLocalDataSource(context: Context): AlbumsLocalDataSource {
        val database = database ?: createDataBase(context)
        return AlbumsLocalDataSource(database.albumDao())
    }

    private fun createDataBase(context: Context): AlbumsDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            AlbumsDatabase::class.java, "Albums.db"
        ).build()
        database = result
        return result
    }

    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            runBlocking {
                AlbumsRemoteDataSource().deleteAllAlbums()
            }
            // Clear all data to avoid test pollution.
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            albumRepository = null
        }
    }
}
