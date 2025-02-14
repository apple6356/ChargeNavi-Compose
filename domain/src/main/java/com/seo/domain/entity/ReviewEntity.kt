package com.seo.domain.entity

import com.google.firebase.Timestamp

data class Review(
    val id: Long,
    val csId: Int,
    val userId: Long,
    val content: String,
    val createTime: Timestamp = Timestamp.now()
)