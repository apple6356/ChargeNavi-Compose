package com.seo.sesac.data.datasource.firestore

interface FireStoreDataSource<T> {
    fun read(data: T)
    fun create(data: T)
    fun delete(data: T)
    fun update(data: T)
}