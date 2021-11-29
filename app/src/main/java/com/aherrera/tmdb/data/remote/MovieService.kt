package com.aherrera.tmdb.data.remote

import com.aherrera.tmdb.data.models.MovieDetailResponse
import com.aherrera.tmdb.data.models.MovieResponse
import com.aherrera.tmdb.data.models.SimilarMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    //TODO Implementar filtrado
    @GET("discover/movie?language=en&include_adult=false&sort_by=release_date.desc&primary_release_date.lte=2021-12-31")
    suspend fun getAllMovies(
        @Query("page") page: Int
    ): Response<MovieResponse>

    @GET("movie/{movieId}/similar")
    suspend fun getSimilarMovies(
        @Path("movieId") movieId: Int
    ): Response<SimilarMovieResponse>

    @GET("movie/{movieId}")
    suspend fun getMovie(
        @Path("movieId") id: Int
    ): Response<MovieDetailResponse>
}