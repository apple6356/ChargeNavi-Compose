package com.seo.sesac.data.entity

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class EvCsResponse(
    val currentCount: Int,
    val data: List<EvCsInfo>,
    val matchCount: Int,
    val page: Int,
    val perPage: Int,
    val totalCount: Int
)

@Serializable
data class EvCsInfo(
    @SerializedName("addr") val address: String,
    @SerializedName("chargeTp") val chargeTp: String,
    @SerializedName("cpId") val cpId: Int,
    @SerializedName("cpNm") val cpNm: String,
    @SerializedName("cpStat") val cpStat: String,
    @SerializedName("cpTp") val cpTp: String,
    @SerializedName("csId") val csId: Int,
    @SerializedName("csNm") val csNm: String,
    @SerializedName("lat") val latitude: String,
    @SerializedName("longi") val longitude: String,
    @SerializedName("stateUpdatetime") val stateUpdatetime: String
)
