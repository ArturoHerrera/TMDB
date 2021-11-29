package com.aherrera.tmdb.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers

data class FormatedResponse<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        LOADING,
        SUCCESS,
        ERROR
    }

    companion object {
        fun <T> loading(data: T? = null): FormatedResponse<T> {
            return FormatedResponse(Status.LOADING, data, null)
        }

        fun <T> success(data: T): FormatedResponse<T> {
            return FormatedResponse(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): FormatedResponse<T> {
            return FormatedResponse(Status.ERROR, data, message)
        }
    }
}


fun <T, A> performGetOperation(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> FormatedResponse<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<FormatedResponse<T>> =
    liveData(Dispatchers.IO) {
        emit(FormatedResponse.loading())
        val source = databaseQuery.invoke().map { FormatedResponse.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == FormatedResponse.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)
        } else if (responseStatus.status == FormatedResponse.Status.ERROR) {
            emit(FormatedResponse.error(responseStatus.message!!))
            emitSource(source)
        }
    }