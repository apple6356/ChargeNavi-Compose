package com.seo.sesac.firestore.common

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * firestore collection
 * */
class FirestoreCollectionFilter {
    companion object{
        private val userCollection = Firebase.firestore.collection("users")
        fun getUserFirestoreCollection() = userCollection

        private val favoriteCollection = Firebase.firestore.collection("favorites")
        fun getFavoriteFirestoreCollection() = favoriteCollection

        private val searchHistoryCollection = Firebase.firestore.collection("search_history")
        fun getSearchHistoryCollection() = searchHistoryCollection

        private val reviewCollection = Firebase.firestore.collection("reviews")
        fun getReviewCollection() = reviewCollection
    }
}