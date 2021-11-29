package com.aherrera.tmdb.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aherrera.tmdb.data.models.*
import com.aherrera.tmdb.utils.converters.IntArrayListConverter
import com.aherrera.tmdb.utils.converters.StringArrayListConverter

@Database(
    entities = [Movie::class, TvShow::class, MovieDetailResponse::class, TvShowDetailResponse::class, SimilarMovie::class, SimilarTvShow::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [(IntArrayListConverter::class), (StringArrayListConverter::class)])
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(
                    context
                    //mDataBase
                ).also { instance = it }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, DatabaseEnum.movies.name)
                .fallbackToDestructiveMigration()
                .build()
    }
}

enum class DatabaseEnum {
    movies, tv_shows
}