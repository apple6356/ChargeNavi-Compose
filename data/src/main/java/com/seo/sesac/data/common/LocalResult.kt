package com.seo.sesac.data.common

sealed class LocalResult<out T> {
    data object DummyConstructor : LocalResult<Nothing>()
    data object Loading : LocalResult<Nothing>()
    data class Success<out T>(
        val data: T
    ) : LocalResult<T>()
    data class Failure(
        val exception: Exception
    ) : LocalResult<Nothing>()
}