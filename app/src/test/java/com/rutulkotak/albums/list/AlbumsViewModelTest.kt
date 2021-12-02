package com.rutulkotak.albums.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rutulkotak.albums.data.Album
import com.rutulkotak.albums.data.FakeTestRepository
import com.rutulkotak.albums.getOrAwaitValue
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Rule
import org.junit.Test

class AlbumsViewModelTest {

    // Use a fake repository to be injected into the viewmodel
    private var albumsRepository: FakeTestRepository = FakeTestRepository()
    // Subject under test
    private var albumsViewModel: AlbumsViewModel = AlbumsViewModel(albumsRepository)

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @After
    fun resetRepository() {
        albumsRepository.deleteAll()
    }

    @Test
    fun validate_getAlbums_returns_correct_size() {
        // Given
        val album1 = Album("Album1", 1, 1)
        val album2 = Album("Album2", 2, 2)
        val album3 = Album("Album3", 3, 3)
        albumsRepository.addTasks(album1, album2, album3)
        // When
        val albums = albumsViewModel.getAlbums().getOrAwaitValue()
        // Then
        assertThat(albums.size, CoreMatchers.`is`(3))
    }

    @Test
    fun validate_getAlbums_returns_correct_album() {
        // Given
        val album1 = Album("Album1", 1, 1)
        val album2 = Album("Album2", 2, 2)
        val album3 = Album("Album3", 3, 3)
        albumsRepository.addTasks(album1, album2, album3)
        // When
        val albums = albumsViewModel.getAlbums().getOrAwaitValue()
        // Then
        assertThat(albums[1], CoreMatchers.`is`(album2))
    }

    @Test
    fun validate_getAlbums_returns_orderBy_title_list() {
        // Given
        val album1 = Album("Grapes", 1, 1)
        val album2 = Album("Apple", 2, 2)
        val album3 = Album("Date", 3, 3)
        albumsRepository.addTasks(album1, album2, album3)
        // When
        val albums = albumsViewModel.getAlbums().getOrAwaitValue()
        // Then
        assertThat(albums[0].title, CoreMatchers.`is`("Grapes"))
    }

}