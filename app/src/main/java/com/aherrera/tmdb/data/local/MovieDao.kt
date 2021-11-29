package com.aherrera.tmdb.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aherrera.tmdb.data.models.Movie
import com.aherrera.tmdb.data.models.MovieDetailResponse
import com.aherrera.tmdb.data.models.SimilarMovie

@Dao
interface MovieDao {

    //MovieDao
    @Query("SELECT * FROM movies WHERE page = :mPage")
    fun getAllMovies(mPage: Int) : LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE id = :mId")
    fun getMovie(mId: Int): LiveData<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)


    //MovieDetailsDao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieDetailResponse)

    @Query("SELECT * FROM movie_detail WHERE id = :id")
    fun getMovieDetail(id: Int): LiveData<MovieDetailResponse>


    //MovieSimilarDao
    @Query("SELECT * FROM similar_movies WHERE movieRoot = :movieId")
    fun getSimilarMovies(movieId: Int) : LiveData<List<SimilarMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSimilar(movies: List<SimilarMovie>)


}