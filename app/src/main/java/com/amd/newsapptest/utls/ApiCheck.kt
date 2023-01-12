package com.amd.newsapptest.utls

import com.amd.newsapptest.domain.model.Resource

suspend fun <T> Resource<T>.check(
    success: suspend (T) -> Unit,
    failure: suspend (Throwable) -> Unit,
    loading: (suspend (Boolean) -> Unit)? = null
) {
    when (val callResource = this) {
        is Resource.Success -> {
            success.invoke(callResource.value)
            loading?.invoke(false)
        }
        is Resource.Failure -> {
            failure.invoke(callResource.throwable)
            loading?.invoke(false)
        }
        Resource.Loading -> {
            loading?.invoke(true)
        }
    }
}