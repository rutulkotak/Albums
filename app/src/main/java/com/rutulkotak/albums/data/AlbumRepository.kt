package com.rutulkotak.albums.data

import androidx.lifecycle.LiveData

interface AlbumRepository {

    suspend fun getAlbums(forceUpdate: Boolean = false): Result<List<Album>>
    suspend fun deleteAlbums(id: Int)
    suspend fun insertAlbum(album: Album)
    suspend fun insertAllAlbum(albumList: List<Album>)
    fun observeAlbums(): LiveData<List<Album>>
}