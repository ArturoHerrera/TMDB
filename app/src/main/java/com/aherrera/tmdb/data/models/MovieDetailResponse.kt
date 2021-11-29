package com.aherrera.tmdb.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aherrera.tmdb.utils.Router

@Entity(tableName = "movie_detail")
data class MovieDetailResponse(
    @PrimaryKey val id: Int,
    val original_title: String,
    val overview: String,
    val vote_average: Float,
    val poster_path: String?
){
    fun getPosterUrl() = "${Router.base_image_url}$poster_path"
}

@Entity(tableName = "tvshow_detail")
data class TvShowDetailResponse(
    @PrimaryKey val id: Int,
    val original_name: String,
    val overview: String?,
    val vote_average: Float,
    val poster_path: String?
){
    fun getPosterUrl() = "${Router.base_image_url}$poster_path"
}