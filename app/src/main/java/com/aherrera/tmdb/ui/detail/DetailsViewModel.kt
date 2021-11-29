package com.aherrera.tmdb.ui.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aherrera.tmdb.data.models.*
import com.aherrera.tmdb.data.repository.MovieRepository
import com.aherrera.tmdb.data.repository.TvShowRepository
import com.aherrera.tmdb.utils.FormatedResponse
import com.aherrera.tmdb.utils.Utils
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvShowRepository: TvShowRepository
) : ViewModel() {

    var mediaType: String = ""
    var mediaId: Int = 0
    var isFav: MutableLiveData<Boolean> = MutableLiveData(false)
    private var favoriteArray: MediaFavorite? = null

    var movie: LiveData<FormatedResponse<MovieDetailResponse>> = MutableLiveData()
    var tvShow: LiveData<FormatedResponse<TvShowDetailResponse>> = MutableLiveData()

    var similarMovies: LiveData<FormatedResponse<List<SimilarMovie>>> = MutableLiveData()
    var similarTvShows: LiveData<FormatedResponse<List<SimilarTvShow>>> = MutableLiveData()

    fun requestMediaInfo() {
        when (mediaType) {
            "movie" -> {
                movie = movieRepository.getMovie(mediaId)
                similarMovies = movieRepository.getSimilarMovies(mediaId)
            }
            "tvShow" -> {
                tvShow = tvShowRepository.getTvShow(mediaId)
                similarTvShows = tvShowRepository.getSimilarMovies(mediaId)
            }
        }
    }

    fun checkFavorite(context: Context) {
        Utils.getString(context, Utils.favoriteList, null)?.let { safeJsonFav ->
            favoriteArray = Utils.jsonToIntArray(safeJsonFav)
            favoriteArray!!.favoriteList.forEach { favId ->

                if (mediaId == favId) isFav.value = true
            }
        }
    }

    fun doFavorite(context: Context) {
        when (isFav.value) {
            true -> {
                favoriteArray?.let{
                    //it.favoriteList.remove(mediaId)
                    //isFav.value = false
                    /*it.favoriteList.forEachIndexed { index, i ->
                        favoriteArray!!.favoriteList.removeAt(index)
                        isFav.postValue(false)
                    }
                    val tmpJson = Gson().toJson(it).toString()
                    Utils.setString(context, Utils.favoriteList, tmpJson)*/
                }
                isFav.postValue(false)
            }
            false -> {
                if (mediaId != 0){
                    //favoriteArray = MediaFavorite(ArrayList())
                    /*favoriteArray?.let {
                        it.favoriteList.add(mediaId)
                        val tmpJson = Gson().toJson(it).toString()
                        Utils.setString(context, Utils.favoriteList, tmpJson)
                        isFav.postValue(true)
                    }*/
                    isFav.postValue(true)
                }
            }
            else -> Unit
        }

    }
}