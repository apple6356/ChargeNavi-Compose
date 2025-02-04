package com.seo.sesac.data.common

sealed class RestResult<out T> {
    data object DummyConstructor : RestResult<Nothing>()
    data object Loading : RestResult<Nothing>()
    data class Success<out T>(
        val data: T
    ) : RestResult<T>()
    data class Failure(
        val exception: Exception
    ) : RestResult<Nothing>()
}