package com.seo.domain.entity

import java.util.UUID

data class Favorite(
    val id: String = UUID.randomUUID().toString(),
    val csId: String = "-1",
    val userId: String = "default"
)
