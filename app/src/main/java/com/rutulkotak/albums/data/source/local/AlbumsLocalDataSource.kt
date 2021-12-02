package com.rutulkotak.albums.data.source.local

import androidx.lifecycle.LiveData
import com.rutulkotak.albums.data.Album
import com.rutulkotak.albums.data.AlbumDataSource
import com.rutulkotak.albums.data.Result
import com.rutulkotak.albums.data.Result.Error
import com.rutulkotak.albums.data.Result.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class AlbumsLocalDataSource internal  constructor(
    private val albumDao: AlbumDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): AlbumDataSource {

    override fun observeAlbums(): LiveData<List<Album>> {
        Timber.d("observeAlbums")
        return albumDao.observeAlbums()
    }

    override suspend fun getAlbums(): Result<List<Album>> = withContext(ioDispatcher) {
        Timber.d("getAlbums")
        return@withContext try {
            Success(albumDao.getAlbums())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun deleteAllAlbums() {
        Timber.d("deleteAllAlbums")
        albumDao.deleteAlbums()
    }

    override suspend fun saveAlbum(album: Album) {
        Timber.d("saveAlbum")
        albumDao.insertAlbum(album)
    }

    override suspend fun saveAllAlbum(albumList: List<Album>) {
        Timber.d("saveAllAlbum")
        albumDao.insertAllAlbum(albumList)
    }

}