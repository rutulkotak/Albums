package com.rutulkotak.albums.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class DefaultAlbumRepository(
    private val albumsRemoteDataSource: AlbumDataSource,
    private val albumsLocalDataSource: AlbumDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : AlbumRepository {

    override fun observeAlbums(): LiveData<List<Album>> {
        return albumsLocalDataSource.observeAlbums()
    }

    override suspend fun getAlbums(forceUpdate: Boolean): Result<List<Album>> {
        if (forceUpdate) {
            try {
                Timber.d("updateAlbumsFromRemoteDataSource")
                updateAlbumsFromRemoteDataSource()
            } catch (ex: Exception) {
                Timber.d("updateAlbumsFromRemoteDataSource error%s : ", ex.message)
                return Result.Error(ex)
            }
        }
        return albumsLocalDataSource.getAlbums()
    }

    override suspend fun deleteAlbums(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAlbum(album: Album) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAllAlbum(albumList: List<Album>) {
        TODO("Not yet implemented")
    }

    private suspend fun updateAlbumsFromRemoteDataSource() {
        val remoteAlbums = albumsRemoteDataSource.getAlbums()

        if (remoteAlbums is Result.Success) {
            Timber.d("Got albums from remote, adding to local DB")
            // Real apps might want to do a proper sync.
            albumsLocalDataSource.deleteAllAlbums()
            albumsLocalDataSource.saveAllAlbum(remoteAlbums.data)
        } else if (remoteAlbums is Result.Error) {
            Timber.d("updateAlbumsFromRemoteDataSource : %s", remoteAlbums.exception.message)
            throw remoteAlbums.exception
        }
    }

}