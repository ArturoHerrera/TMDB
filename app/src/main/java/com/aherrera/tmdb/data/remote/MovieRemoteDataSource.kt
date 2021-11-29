package com.aherrera.tmdb.data.remote

import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService
): BaseDataSource() {

    suspend fun getMovies(page: Int) = getResult { movieService.getAllMovies(page = page) }

    suspend fun getSimilarMovies(movieId: Int) = getResult { movieService.getSimilarMovies(movieId = movieId) }

    suspend fun getMovie(id: Int) = getResult { movieService.getMovie(id = id) }
}