package com.seo.domain.entity

data class SearchHistory(
    val id: Long,
    val userId: String,
    val content: String
)