package com.aherrera.tmdb.data.models

data class MovieResponse(
    var page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)

data class SimilarMovieResponse(
    var page: Int,
    val results: List<SimilarMovie>,
    val total_pages: Int,
    val total_results: Int
)

data class TvShowResponse(
    var page: Int,
    val results: List<TvShow>,
    val total_pages: Int,
    val total_results: Int
)

data class SimilarTvShowResponse(
    var page: Int,
    val results: List<SimilarTvShow>,
    val total_pages: Int,
    val total_results: Int
)