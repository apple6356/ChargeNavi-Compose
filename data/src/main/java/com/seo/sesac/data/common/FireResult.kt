package com.seo.sesac.data.common

sealed class FireResult<out T> {
    data object DummyConstructor : FireResult<Nothing>()
    data object Loading : FireResult<Nothing>()
    data class Success<out T>(
        val data: T
    ) : FireResult<T>()
    data class Failure(
        val exception: Exception
    ) : FireResult<Nothing>()
}