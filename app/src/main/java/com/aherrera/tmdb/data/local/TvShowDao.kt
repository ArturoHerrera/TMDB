package com.aherrera.tmdb.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aherrera.tmdb.data.models.*

@Dao
interface TvShowDao {

    //TvShowDao
    @Query("SELECT * FROM tv_show WHERE page = :mPage")
    fun getAllTvShows(mPage: Int) : LiveData<List<TvShow>>

    @Query("SELECT * FROM tv_show WHERE id = :id")
    fun getTvShow(id: Int): LiveData<TvShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<TvShow>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tvShow: TvShow)


    //TvShowDaoDetailsDao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tvShow: TvShowDetailResponse)

    @Query("SELECT * FROM tvshow_detail WHERE id = :id")
    fun getTvShowDetail(id: Int): LiveData<TvShowDetailResponse>


    //MovieSimilarDao
    @Query("SELECT * FROM similar_tvshow WHERE tvShowRoot = :similarTvShowId")
    fun getSimilarTvShows(similarTvShowId: Int) : LiveData<List<SimilarTvShow>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSimilarTvShows(similarTvShows: List<SimilarTvShow>)
}