package com.aherrera.tmdb.data.repository

import com.aherrera.tmdb.data.local.MovieDao
import com.aherrera.tmdb.data.remote.MovieRemoteDataSource
import com.aherrera.tmdb.utils.performGetOperation
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieDao
) {

    fun getMovie(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getMovieDetail(id) },
        networkCall = { remoteDataSource.getMovie(id) },
        saveCallResult = { localDataSource.insert(it) }
    )

    fun getMovies(page: Int) = performGetOperation(
        databaseQuery = { localDataSource.getAllMovies(page) },
        networkCall = { remoteDataSource.getMovies(page) },
        saveCallResult = {
            val mList = it.results
            mList.forEach { mMovie -> mMovie.page = page }
            localDataSource.insertAll(mList)
        }
    )

    fun getSimilarMovies(movieId: Int) = performGetOperation(
        databaseQuery = { localDataSource.getSimilarMovies(movieId) },
        networkCall = { remoteDataSource.getSimilarMovies(movieId) },
        saveCallResult = {
            val mList = it.results
            mList.forEach { mMovie -> mMovie.movieRoot = movieId }
            localDataSource.insertAllSimilar(mList)
        }
    )
}