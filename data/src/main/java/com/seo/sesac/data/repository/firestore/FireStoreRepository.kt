package com.seo.sesac.data.repository.firestore

interface FireStoreRepository {
    fun read()
    fun create()
    fun delete()
    fun update()
}