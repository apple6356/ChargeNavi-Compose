package com.seo.domain.entity

data class UserInfo(
    val id: String? = "-1",
    val nickname: String? = "nickname",
    val profileImage: String? = ""
) {
    fun toMap(): Map<String, String?> {
        return mapOf(
            "id" to id,
            "nickname" to nickname,
            "profileImage" to profileImage
        )
    }
}