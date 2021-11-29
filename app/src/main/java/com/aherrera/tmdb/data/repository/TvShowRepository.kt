package com.aherrera.tmdb.data.repository

import com.aherrera.tmdb.data.local.TvShowDao
import com.aherrera.tmdb.data.remote.TvShowRemoteDataSource
import com.aherrera.tmdb.utils.performGetOperation
import javax.inject.Inject

class TvShowRepository @Inject constructor(
    private val remoteDataSource: TvShowRemoteDataSource,
    private val localDataSource: TvShowDao
) {

    fun getTvShow(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getTvShowDetail(id) },
        networkCall = { remoteDataSource.getTvShow(id) },
        saveCallResult = { localDataSource.insert(it) }
    )

    fun getTvShows(page: Int) = performGetOperation(
        databaseQuery = { localDataSource.getAllTvShows(page) },
        networkCall = { remoteDataSource.getAllTvShows(page) },
        saveCallResult = {
            val mList = it.results
            mList.forEach { mTvShow -> mTvShow.page = page }
            localDataSource.insertAll(mList)
        }
    )

    fun getSimilarMovies(similarTvShowId: Int) = performGetOperation(
        databaseQuery = { localDataSource.getSimilarTvShows(similarTvShowId) },
        networkCall = { remoteDataSource.getSimilarTvShows(similarTvShowId) },
        saveCallResult = {
            val mList = it.results
            mList.forEach { mMovie -> mMovie.tvShowRoot = similarTvShowId }
            localDataSource.insertAllSimilarTvShows(mList)
        }
    )
}