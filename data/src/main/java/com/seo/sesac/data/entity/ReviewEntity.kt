package com.seo.sesac.data.entity

data class Review(
    val id: Long,
    val csId: Int,
    val userId: Long,
    val content: String
)