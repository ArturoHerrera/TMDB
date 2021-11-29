package com.aherrera.tmdb.data.remote

import com.aherrera.tmdb.data.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowService {

    @GET("discover/tv?language=en&include_adult=false&sort_by=release_date.desc&primary_release_date.lte=2021-12-31")
    suspend fun getAllTvShows(
        @Query("page") page: Int
    ): Response<TvShowResponse>

    @GET("tv/{tvShowId}/similar")
    suspend fun getSimilarTvShows(
        @Path("tvShowId") tvShowId: Int
    ): Response<SimilarTvShowResponse>

    @GET("tv/{id}")
    suspend fun getTvShow(
        @Path("id") id: Int
    ): Response<TvShowDetailResponse>
}