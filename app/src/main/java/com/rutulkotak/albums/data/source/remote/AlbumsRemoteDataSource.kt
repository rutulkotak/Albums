package com.rutulkotak.albums.data.source.remote

import androidx.lifecycle.LiveData
import com.rutulkotak.albums.data.Album
import com.rutulkotak.albums.data.AlbumDataSource
import com.rutulkotak.albums.data.Result
import timber.log.Timber

class AlbumsRemoteDataSource : AlbumDataSource {

    override fun observeAlbums(): LiveData<List<Album>> {
        Timber.d("observeAlbums")
        TODO("Not yet implemented")
    }

    override suspend fun getAlbums(): Result<List<Album>> {
        Timber.d("getAlbums")
        return Result.Success(AlbumsApi.retrofitService.getAlbums())
    }

    override suspend fun deleteAllAlbums() {
        Timber.d("deleteAllAlbums")
    }

    override suspend fun saveAlbum(album: Album) {
        Timber.d("saveAlbum")
    }

    override suspend fun saveAllAlbum(albumList: List<Album>) {
        Timber.d("saveAllAlbum")
    }

}