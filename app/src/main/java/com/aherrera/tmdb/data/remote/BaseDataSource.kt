package com.aherrera.tmdb.data.remote

import com.aherrera.tmdb.utils.FormatedResponse
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): FormatedResponse<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let{ safeBody ->
                    return FormatedResponse.success(safeBody)
                }
            }
            return error(" ${response.code()} ---> ${response.body()}")
        } catch (mError: Exception) {
            return error(mError.message ?: mError.toString())
        }
    }

    private fun <T> error(message: String): FormatedResponse<T> {
        return FormatedResponse.error("La petición falló por la sigueinte razon: $message")
    }

}