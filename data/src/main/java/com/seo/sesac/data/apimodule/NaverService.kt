package com.seo.sesac.data.apimodule

import com.seo.sesac.data.entity.GeoCodeEntity
import com.seo.sesac.data.entity.ReverseGeoCodeEntity
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverService {

    @GET("map-reversegeocode/v2/gc")
    suspend fun getNaverReverseGeoCode(
        @Header("X-NCP-APIGW-API-KEY-ID") apiKeyId: String,
        @Header("X-NCP-APIGW-API-KEY") apiKey: String,
        @Query("coords") coords: String,
        @Query("output") output: String = "json"
    ): ReverseGeoCodeEntity

    @GET("map-geocode/v2/geocode")
    suspend fun getNaverGeoCode(
        @Header("X-NCP-APIGW-API-KEY-ID") apiKeyId: String,
        @Header("X-NCP-APIGW-API-KEY") apiKey: String,
        @Query("query") query: String
    ): GeoCodeEntity

}