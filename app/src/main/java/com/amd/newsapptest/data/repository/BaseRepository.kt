package com.amd.newsapptest.data.repository

import com.amd.newsapptest.domain.exception.ServerFailed
import com.amd.newsapptest.domain.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

open class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = withContext(Dispatchers.IO) { apiCall.invoke() }
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        Resource.Success(responseBody)
                    } ?: throw ServerFailed("Coba beberapa saat lagi")
                } else {
                    throw ServerFailed("Coba beberapa saat lagi")
                }
            } catch (throwable: Throwable) {
                Resource.Failure(throwable)
            }
        }
    }

}