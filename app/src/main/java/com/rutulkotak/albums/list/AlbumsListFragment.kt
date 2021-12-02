package com.rutulkotak.albums.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rutulkotak.albums.AlbumsApplication
import com.rutulkotak.albums.databinding.FragmentAlbumsListBinding
import com.rutulkotak.albums.util.setupRefreshLayout
import timber.log.Timber

class AlbumsListFragment : Fragment() {

    private val albumsViewModel by viewModels<AlbumsViewModel> {
        AlbumsViewModelFactory((requireContext().applicationContext as AlbumsApplication).albumRepository)
    }
    private lateinit var viewDataBinding: FragmentAlbumsListBinding
    private lateinit var listAdapter: AlbumListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Timber.d("onCreateView")
        // Get a reference to the binding object and inflate the fragment views.
        viewDataBinding = FragmentAlbumsListBinding.inflate(
            inflater, container, false).apply {
                viewModel = albumsViewModel
        }

        // Inflate the layout for this fragment
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Timber.d("onActivityCreated")
        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        // Set list adapter
        setupListAdapter()
        setupRefreshLayout(viewDataBinding.refreshLayout, viewDataBinding.recyclerview)
    }

    private fun setupListAdapter() {
        Timber.d("setupListAdapter")
        // Observer
        listAdapter = AlbumListAdapter(albumsViewModel)
        viewDataBinding.recyclerview.adapter = listAdapter
        albumsViewModel.getAlbums().observe(viewLifecycleOwner, {
            it?.let {
                listAdapter.submitList(it)
            }
        })
    }

}