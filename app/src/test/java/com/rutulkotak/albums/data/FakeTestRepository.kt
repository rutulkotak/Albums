package com.rutulkotak.albums.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.runBlocking
import java.util.*

class FakeTestRepository : AlbumRepository {

    private var albumsServiceData: LinkedHashMap<String, Album> = LinkedHashMap()
    private val observableAlbums = MutableLiveData<List<Album>>()

    override suspend fun getAlbums(forceUpdate: Boolean): Result<List<Album>> {
        return Result.Success(albumsServiceData.values.toList())
    }

    override suspend fun deleteAlbums(id: Int) {
        albumsServiceData.remove(id.toString())
    }

    fun deleteAll() {
        albumsServiceData.clear()
    }

    override suspend fun insertAlbum(album: Album) {
        albumsServiceData[album.id.toString()] = album
    }

    override suspend fun insertAllAlbum(albumList: List<Album>) {
        for(album in albumList) {
            insertAlbum(album)
        }
    }

    override fun observeAlbums(): LiveData<List<Album>> {
        runBlocking {
            val result = getAlbums()
            if(result is Result.Success) {
                observableAlbums.value = result.data
            }
        }
        return observableAlbums
    }

    fun addTasks(vararg tasks: Album) {
        for (task in tasks) {
            albumsServiceData[task.id.toString()] = task
        }
        runBlocking { getAlbums() }
    }

}