package com.seo.sesac.data.entity

import java.util.UUID

/**
 * 즐겨찾기 데이터
 * */
data class Favorite(
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "-1",
    val csId: String = "-1",
    val address: String = "address"
)