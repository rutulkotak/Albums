package com.rutulkotak.albums.data

import androidx.lifecycle.LiveData

class FakeDataSource(var albums: MutableList<Album>? = mutableListOf()) : AlbumDataSource {

    override fun observeAlbums(): LiveData<List<Album>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAlbums(): Result<List<Album>> {
        albums?.let { return Result.Success(ArrayList(it)) }
        return Result.Error(
            Exception("Albums not found")
        )
    }

    override suspend fun deleteAllAlbums() {
        albums?.clear()
    }

    override suspend fun saveAlbum(album: Album) {
        albums?.add(album)
    }

    override suspend fun saveAllAlbum(albumList: List<Album>) {
        albums?.addAll(albumList)
    }

}