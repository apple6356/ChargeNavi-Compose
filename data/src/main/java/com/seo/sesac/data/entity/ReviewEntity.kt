package com.seo.sesac.data.entity

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class Review(
    val id: String = UUID.randomUUID().toString(),
    val csId: String = "-1",
    val userId: String = "-1",
    val nickName: String = "게스트",
    val content: String = "review content",
    val rating: Int = -1,
    val likeCount: Long = 0,
    val createDate: String = LocalDate.now().toString(),
    val createTime: String = LocalDateTime.now().toString()
)