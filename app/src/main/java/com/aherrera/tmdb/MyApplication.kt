package com.aherrera.tmdb

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.google.gson.Gson
import com.heka.compose_utils_kt.LogUtils
import com.heka.web_helper_kt.WebClientHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        LogUtils.logsEnabled = BuildConfig.DEBUG
        WebClientHelper.bodyInterceptorEnabled = BuildConfig.DEBUG
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

}