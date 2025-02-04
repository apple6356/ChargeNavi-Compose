package com.seo.sesac.data.entity

import com.google.gson.annotations.SerializedName

/**
 * 각 요소별 설명
 * https://api.ncloud-docs.com/docs/ai-naver-mapsgeocoding-geocode
 * */

data class GeoCodeEntity (
    @SerializedName("status") val status: String,
    @SerializedName("meta") val meta: Meta,
    @SerializedName("addresses") val addresses: List<CoodrsAddress>,
    @SerializedName("errorMessage") val errorMessage: String
)

data class Meta(
    @SerializedName("totalCount") val totalCount: Number,
    @SerializedName("page") val page: Number,
    @SerializedName("count") val count: Number
)

data class CoodrsAddress(
    @SerializedName("roadAddress") val roadAddress: String,
    @SerializedName("jibunAddress") val jibunAddress: String,
    @SerializedName("englishAddress") val englishAddress: String,
    @SerializedName("addressElements") val addressElements: List<AddressElement>,
    // 경도
    @SerializedName("x") val x: String,
    // 위도
    @SerializedName("y") val y: String,
    @SerializedName("distance") val distance: Double
)

data class AddressElement(
    @SerializedName("type") val type: List<String>,
    @SerializedName("longName") val longName: String,
    @SerializedName("shortName") val shortName: String,
    @SerializedName("code") val code: String
)