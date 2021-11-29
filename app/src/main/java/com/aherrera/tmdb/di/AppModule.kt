package com.aherrera.tmdb.di

import android.content.Context
import androidx.annotation.NonNull
import com.aherrera.tmdb.data.local.AppDatabase
import com.aherrera.tmdb.data.local.MovieDao
import com.aherrera.tmdb.data.remote.MovieRemoteDataSource
import com.aherrera.tmdb.data.remote.MovieService
import com.aherrera.tmdb.data.remote.TvShowRemoteDataSource
import com.aherrera.tmdb.data.remote.TvShowService
import com.aherrera.tmdb.data.repository.MovieRepository
import com.aherrera.tmdb.utils.MyRequestInterceptor
import com.aherrera.tmdb.utils.Router
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMyHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(MyRequestInterceptor())
            .addInterceptor(OkHttpProfilerInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, @NonNull okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(Router.base_api_url)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService = retrofit.create(MovieService::class.java)

    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(movieService: MovieService) = MovieRemoteDataSource(movieService)

    @Provides
    fun provideTvShowService(retrofit: Retrofit): TvShowService = retrofit.create(TvShowService::class.java)

    @Singleton
    @Provides
    fun provideTvShowRemoteDataSource(tvShowService: TvShowService) = TvShowRemoteDataSource(tvShowService)

    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)


    @Singleton
    @Provides
    fun provideMovieDao(db: AppDatabase) = db.movieDao()

    @Singleton
    @Provides
    fun provideTvShowDao(db: AppDatabase) = db.tvShowDao()

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        movieLocalDataSource: MovieDao
    ) = MovieRepository(movieRemoteDataSource, movieLocalDataSource)
}