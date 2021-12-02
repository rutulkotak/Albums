package com.rutulkotak.albums.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultAlbumRepositoryTest {

    private val album1 = Album("Title1", 1, 1)
    private val album2 = Album("Title2", 2, 2)
    private val album3 = Album("Title3", 3, 3)

    private val remoteAlbums = listOf(album1, album2).sortedBy { it.title }
    private val localAlbums = listOf(album3).sortedBy { it.title }
    private val newAlbums = listOf(album3).sortedBy { it.title }
    private lateinit var albumsRemoteDataSource: FakeDataSource
    private lateinit var albumsLocalDataSource: FakeDataSource

    // Class under test
    private lateinit var defaultAlbumRepository: DefaultAlbumRepository

    @Before
    fun createRepository() {
        albumsRemoteDataSource = FakeDataSource(remoteAlbums.toMutableList())
        albumsLocalDataSource = FakeDataSource(localAlbums.toMutableList())
        // Get a reference to the class under test
        defaultAlbumRepository = DefaultAlbumRepository(
            albumsRemoteDataSource, albumsLocalDataSource, Dispatchers.Main)
    }

    @After
    fun clear() = runBlocking() {
        albumsRemoteDataSource.deleteAllAlbums()
        albumsLocalDataSource.deleteAllAlbums()
    }

    @Test
    fun getAlbums_requests_from_remote_datasource() = runBlockingTest {
        // When Albums are requested from the albums repository & forceUpdate is true
        val albums = defaultAlbumRepository.getAlbums(true) as Result.Success

        // Then albums are loaded from the remote data source
        assertThat(albums.data, CoreMatchers.`is`(remoteAlbums))
    }

    @Test
    fun getAlbums_requests_from_local_datasource() = runBlockingTest {
        // When Albums are requested from the albums repository & force update is false
        val albums = defaultAlbumRepository.getAlbums(false) as Result.Success

        // Then albums are loaded from the local data source
        assertThat(albums.data, CoreMatchers.`is`(localAlbums))
    }

}