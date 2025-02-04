package com.seo.sesac.data.entity

import com.google.gson.annotations.SerializedName

/**
 * 각 요소별 설명
 * https://api.ncloud-docs.com/docs/ai-naver-mapsreversegeocoding-gc
 * */

data class ReverseGeoCodeEntity (
    @SerializedName("status") val status: Status,
    @SerializedName("results") val results: List<ResultAddrInfo>
)

data class Status (
    @SerializedName("code") val code: Int,
    @SerializedName("name") val name: String,
    @SerializedName("message") val message: String
)

data class ResultAddrInfo (
    @SerializedName("name") val name: String,
    @SerializedName("code") val code: Code,
    @SerializedName("region") val region: Region,
    @SerializedName("land") val land: Land
)

data class Code (
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("mappingId") val mappingId: String
)

data class Region(
    @SerializedName("area0") val area0: Area,
    @SerializedName("area1") val area1: Area,
    @SerializedName("area2") val area2: Area,
    @SerializedName("area3") val area3: Area,
    @SerializedName("area4") val area4: Area,
)


data class Land(
    @SerializedName("type") val type: String,
    @SerializedName("name") val name: String,
    @SerializedName("number1") val number1: String,
    @SerializedName("number2") val number2: String,
    @SerializedName("coords") val coords: Coords
)

data class Area(
    @SerializedName("name") val name: String,
    @SerializedName("coords") val coords: Coords
)

data class Coords(
    @SerializedName("center") val center: Center
)

data class Center(
    @SerializedName("crs") val crs: String,
    @SerializedName("x") val x: Float,
    @SerializedName("y") val y: Float
)

data class Addition(
    @SerializedName("type") val type: String,
    @SerializedName("value") val value: String
)