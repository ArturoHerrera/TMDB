package com.aherrera.tmdb.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aherrera.tmdb.R
import com.aherrera.tmdb.data.models.MovieDetailResponse
import com.aherrera.tmdb.data.models.TvShowDetailResponse
import com.aherrera.tmdb.databinding.DetailsFragmentBinding
import com.aherrera.tmdb.ui.discover.MoviesDiscoverAdapter
import com.aherrera.tmdb.utils.FormatedResponse
import com.aherrera.tmdb.utils.Utils
import com.aherrera.tmdb.utils.autoCleared
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailsFragment : Fragment(), MoviesDiscoverAdapter.ItemListener {

    private val viewModel: DetailsViewModel by viewModels()
    private var binding: DetailsFragmentBinding by autoCleared()
    private lateinit var similarMoviesAdapter: MoviesSimilarAdapter
    private lateinit var similarTvShowsAdapter: TvShowsSimilarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailsFragmentBinding
            .inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("movieId")?.let { viewModel.mediaId = it }
        arguments?.getString("type")?.let { viewModel.mediaType = it }

        viewModel.requestMediaInfo()
        viewModel.checkFavorite(requireContext())

        setObservers()
        setListeners()
    }

    private fun setListeners() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack(
                R.id.discoverFragment,
                false
            )
        }

        binding.favoriteBtn.setOnClickListener {
            viewModel.doFavorite(requireContext())
        }
    }

    private fun setObservers() {
        when (viewModel.mediaType) {
            "movie" -> {
                binding.similarMedia.text = getString(R.string.similar_movies)
                movieObserver()
                setMovieRecyclerViews()
            }
            "tvShow" -> {
                binding.similarMedia.text = getString(R.string.similar_tvshow)
                tvShowObserver()
                setTvShowRecyclerViews()
            }
        }

        viewModel.isFav.observe(viewLifecycleOwner, {
            when (it) {
                true -> binding.favorite.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.like_red
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                )
                false -> binding.favorite.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                )
            }
        })
    }

    private fun onMovieRecyclerVisibility(visibility: Int) {
        when (visibility) {
            View.GONE -> {
                binding.similarMoviesRecyclerView.visibility = View.GONE
                binding.similarMedia.visibility = View.GONE
            }
            View.VISIBLE -> {
                binding.similarMoviesRecyclerView.visibility = View.VISIBLE
                binding.similarMedia.visibility = View.VISIBLE
            }
        }
    }

    private fun setMovieRecyclerViews() {
        similarMoviesAdapter = MoviesSimilarAdapter(this)
        binding.similarMoviesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.similarMoviesRecyclerView.adapter = similarMoviesAdapter
        binding.similarMoviesRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (Utils.isLastItemDisplaying(recyclerView)) {
                    //viewModel.getMovies()
                }
            }
        })
    }

    private fun setTvShowRecyclerViews() {
        similarTvShowsAdapter = TvShowsSimilarAdapter(this)
        binding.similarMoviesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.similarMoviesRecyclerView.adapter = similarTvShowsAdapter
        binding.similarMoviesRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (Utils.isLastItemDisplaying(recyclerView)) {
                    //viewModel.getMovies()
                }
            }
        })
    }

    private fun movieObserver() {
        viewModel.movie.observe(viewLifecycleOwner, {
            when (it.status) {
                FormatedResponse.Status.SUCCESS -> {
                    it.data?.let { safeMovie -> setMovieUi(safeMovie) }
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

        viewModel.similarMovies.observe(viewLifecycleOwner, {
            when (it.status) {
                FormatedResponse.Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        onMovieRecyclerVisibility(View.VISIBLE)
                        similarMoviesAdapter.setItems(ArrayList(it.data))
                    } else {
                        onMovieRecyclerVisibility(View.GONE)
                    }
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
    }

    private fun tvShowObserver() {
        viewModel.tvShow.observe(viewLifecycleOwner, {
            when (it.status) {
                FormatedResponse.Status.SUCCESS -> {
                    it.data?.let { safeMovie -> setTvShowUi(safeMovie) }
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

        viewModel.similarTvShows.observe(viewLifecycleOwner, {
            when (it.status) {
                FormatedResponse.Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        onMovieRecyclerVisibility(View.VISIBLE)
                        similarTvShowsAdapter.setItems(ArrayList(it.data))
                    } else {
                        onMovieRecyclerVisibility(View.GONE)
                    }
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
    }

    private fun setMovieUi(movie: MovieDetailResponse) {
        binding.titleTv.text = movie.original_title
        binding.sumaryTv.text = movie.overview
        //binding.detailRank.rating = (movie.vote_average / 2)
        binding.detailRank.rating =
            3.5F //TODO Solo para propósitos visuales ya que muchos lo traen en 0.

        Glide.with(requireContext())
            .load(movie.getPosterUrl())
            .placeholder(R.drawable.ic_local_play)
            .error(R.drawable.ic_local_play)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.posterImage)
    }

    private fun setTvShowUi(tvShow: TvShowDetailResponse) {
        binding.titleTv.text = tvShow.original_name
        binding.sumaryTv.text = tvShow.overview
        //binding.detailRank.rating = (movie.vote_average / 2)
        binding.detailRank.rating =
            3.5F //TODO Solo para propósitos visuales ya que muchos lo traen en 0

        Glide.with(requireContext())
            .load(tvShow.getPosterUrl())
            .placeholder(R.drawable.ic_local_play)
            .error(R.drawable.ic_local_play)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.posterImage)
    }

    override fun onItemClicked(movieId: Int, type: String) {
        Log.wtf("testMovies", "onMovieClicked ---> movieId: $movieId, type: $type")
        findNavController().navigate(
            R.id.action_detailFragment_to_detailFragment,
            bundleOf("movieId" to movieId, "type" to type)
        )
    }


}