package com.seo.sesac.data.entity

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class EvCsResponse(
    @SerializedName("currentCount") val currentCount: Int,
    @SerializedName("data") val data: List<EvCsInfo>,
    @SerializedName("matchCount") val matchCount: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("perPage") val perPage: Int,
    @SerializedName("totalCount") val totalCount: Int
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
