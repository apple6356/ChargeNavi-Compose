package com.seo.sesac.firestore.common

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreCollectionFilter {
    companion object{
        private val userCollection = Firebase.firestore.collection("users")
        fun getUserFirestoreCollection() = userCollection
    }
}