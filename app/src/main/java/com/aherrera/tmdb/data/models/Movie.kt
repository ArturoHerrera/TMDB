package com.aherrera.tmdb.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aherrera.tmdb.utils.Router
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Float,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Int,
    var page: Int
) {
    fun getPosterUrl() = "${Router.base_image_url}$poster_path"
}

@Entity(tableName = "similar_movies")
data class SimilarMovie(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Float,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Int,
    var page: Int,
    var movieRoot: Int = 0
) {
    fun getPosterUrl() = "${Router.base_image_url}$poster_path"
}

data class MediaFavorite(
    val favoriteList : ArrayList<Int>
)
