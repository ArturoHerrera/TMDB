package com.aherrera.tmdb.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aherrera.tmdb.utils.Router

@Entity(tableName = "tv_show")
data class TvShow(
    @PrimaryKey
    val id: Int,
    val poster_path: String?,
    val popularity: Float,
    val backdrop_path: String?,
    val vote_average: Float,
    val overview: String,
    val first_air_date: String,
    val origin_country: List<String>,
    val genre_ids: List<Int>,
    val original_language: String,
    val vote_count: Int,
    val name: String,
    val original_name: String,
    var page: Int
){
    fun getPosterUrl() = "${Router.base_image_url}$poster_path"
}

@Entity(tableName = "similar_tvshow")
data class SimilarTvShow(
    @PrimaryKey
    val id: Int,
    val poster_path: String?,
    val popularity: Float,
    val backdrop_path: String?,
    val vote_average: Float,
    val overview: String,
    val first_air_date: String,
    val origin_country: List<String>,
    val genre_ids: List<Int>,
    val original_language: String,
    val vote_count: Int,
    val name: String,
    val original_name: String,
    var page: Int,
    var tvShowRoot: Int = 0
){
    fun getPosterUrl() = "${Router.base_image_url}$poster_path"
}

