package com.aherrera.tmdb.ui.discover

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aherrera.tmdb.R
import com.aherrera.tmdb.databinding.DiscoverFragmentBinding
import com.aherrera.tmdb.utils.FormatedResponse
import com.aherrera.tmdb.utils.Utils.isLastItemDisplaying
import com.aherrera.tmdb.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DiscoverFragment : Fragment(), MoviesDiscoverAdapter.ItemListener {

    private var binding: DiscoverFragmentBinding by autoCleared()
    private val viewModel: DiscoverViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesDiscoverAdapter
    private lateinit var tvShowAdapter: TvShowDiscoverAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DiscoverFragmentBinding
            .inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViews()
        setObservers()
    }

    private fun setRecyclerViews() {
        moviesAdapter = MoviesDiscoverAdapter(this)
        binding.lastMoviesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.lastMoviesRecyclerView.adapter = moviesAdapter
        binding.lastMoviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isLastItemDisplaying(recyclerView)) {
                    viewModel.getMovies()
                }
            }
        })

        tvShowAdapter = TvShowDiscoverAdapter(this)
        binding.lastTvRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.lastTvRecyclerView.adapter = tvShowAdapter
        binding.lastTvRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isLastItemDisplaying(recyclerView)) {
                    viewModel.getTvShow()
                }
            }
        })
    }

    private fun setObservers() {
        viewModel.movies.observe(viewLifecycleOwner, {
            when (it.status) {
                FormatedResponse.Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) moviesAdapter.setItems(ArrayList(it.data))
                    binding.progressBar.visibility = View.GONE
                }
                FormatedResponse.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    Timber.d(it.message)
                }
                FormatedResponse.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })

        viewModel.tvShows.observe(viewLifecycleOwner, {
            when (it.status) {
                FormatedResponse.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) tvShowAdapter.setItems(ArrayList(it.data))
                }
                FormatedResponse.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    Timber.d(it.message)
                }
                FormatedResponse.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onItemClicked(movieId: Int, type: String) {
        Log.wtf("testMovies", "onMovieClicked ---> movieId: $movieId, type: $type")
        findNavController().navigate(
            R.id.action_discoverFragment_to_detailFragment,
            bundleOf("movieId" to movieId, "type" to type)
        )
    }

}