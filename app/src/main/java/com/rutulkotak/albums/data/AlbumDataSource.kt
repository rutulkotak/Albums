package com.rutulkotak.albums.data

import androidx.lifecycle.LiveData

interface AlbumDataSource {

    fun observeAlbums(): LiveData<List<Album>>
    suspend fun getAlbums(): Result<List<Album>>
    suspend fun deleteAllAlbums()
    suspend fun saveAlbum(album: Album)
    suspend fun saveAllAlbum(albumList: List<Album>)
}