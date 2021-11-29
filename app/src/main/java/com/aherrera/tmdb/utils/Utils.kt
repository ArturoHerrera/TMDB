package com.aherrera.tmdb.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aherrera.tmdb.data.models.MediaFavorite
import com.google.gson.Gson

object Utils {

    private var pref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private const val preferenceName: String = "userPreferences"
    const val favoriteList: String = "favouriteList"

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }

    fun isLastItemDisplaying(recyclerView: RecyclerView): Boolean {
        if (recyclerView.adapter!!.itemCount != 0) {
            val lastVisibleItemPosition =
                (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.adapter!!
                    .itemCount - 1
            ) return true
        }
        return false
    }

    private fun sharedPreferences(context: Context): SharedPreferences {
        if (pref == null) {
            pref = context.getSharedPreferences(
                preferenceName,
                Context.MODE_PRIVATE
            )
        }
        return pref!!
    }

    @SuppressLint("CommitPrefEdits")
    fun getEditor(context: Context): SharedPreferences.Editor {
        if (editor == null) {
            editor = sharedPreferences(context).edit()
        }
        return editor!!
    }

    fun getString(context: Context, key: String?, default: String?): String? {
        return sharedPreferences(context).getString(key, default)
    }

    fun setString(context: Context, key: String, value: String) {
        getEditor(context).putString(key, value)
        getEditor(context).apply()
    }

    fun jsonToIntArray(jsonString: String): MediaFavorite{
        val mArray = Gson().fromJson(
            jsonString,
            MediaFavorite::class.java
        )
        return mArray
    }

}