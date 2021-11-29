package com.aherrera.tmdb.ui.discover

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aherrera.tmdb.data.models.Movie
import com.aherrera.tmdb.data.repository.MovieRepository
import com.aherrera.tmdb.data.repository.TvShowRepository
import com.aherrera.tmdb.utils.FormatedResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvShowRepository: TvShowRepository
) : ViewModel() {
    var moviePage: Int = 1
    val movies = movieRepository.getMovies(moviePage)

    var tvShowPage: Int = 1
    val tvShows = tvShowRepository.getTvShows(tvShowPage)

    fun getMovies(){
        moviePage+=1
        movieRepository.getMovies(moviePage)
    }

    fun getTvShow(){
        tvShowPage+=1
        tvShowRepository.getTvShows(tvShowPage)
    }
}