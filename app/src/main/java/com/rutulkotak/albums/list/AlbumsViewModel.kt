package com.rutulkotak.albums.list

import androidx.lifecycle.*
import com.rutulkotak.albums.data.Album
import com.rutulkotak.albums.data.AlbumRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class AlbumsViewModel(
    private val albumsRepository: AlbumRepository
) : ViewModel() {

    // Data Loading
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading
    // Force Update
    private val _forceUpdate = MutableLiveData(false)
    // List of Albums
    private val items: LiveData<List<Album>> = _forceUpdate.switchMap { forceUpdate ->
        if (forceUpdate) {
            Timber.d("forceUpdate true")
            _dataLoading.value = true
            viewModelScope.launch {
                albumsRepository.getAlbums(true)
                _dataLoading.value = false
            }
        }
        albumsRepository.observeAlbums()
    }

    // This LiveData depends on another so we can use a transformation.
    val empty: LiveData<Boolean> = Transformations.map(items) {
        it.isEmpty()
    }

    fun getAlbums() : LiveData<List<Album>> {
        Timber.d("getAlbums")
        return albumsRepository.observeAlbums()
    }

    fun refresh() {
        Timber.d("refresh")
        _forceUpdate.value = true
    }

}

@Suppress("UNCHECKED_CAST")
class AlbumsViewModelFactory (
    private val albumsRepository: AlbumRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (AlbumsViewModel(albumsRepository) as T)
}