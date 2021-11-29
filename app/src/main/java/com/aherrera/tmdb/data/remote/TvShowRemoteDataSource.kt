package com.aherrera.tmdb.data.remote

import android.util.Log
import javax.inject.Inject

class TvShowRemoteDataSource @Inject constructor(
    private val tvShowService: TvShowService
): BaseDataSource() {

    suspend fun getAllTvShows(page: Int) = getResult { tvShowService.getAllTvShows(page = page) }

    suspend fun getSimilarTvShows(movieId: Int) = getResult { tvShowService.getSimilarTvShows(tvShowId = movieId) }

    suspend fun getTvShow(id: Int) = getResult { tvShowService.getTvShow(id = id) }
}