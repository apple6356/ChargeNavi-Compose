package com.seo.sesac.data.apimodule

import com.seo.sesac.data.entity.DirectionEntity
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

    /**
     * query 설명: https://api.ncloud-docs.com/docs/ai-naver-mapsdirections-driving 참고
     * start 예시 : start=127.12345,37.12345
     * goal 예시 : goal=123.45678,34.56789:124.56789,35.67890 - ':'로 구분하여 최대 10개의 목적지 입력 가능
     * */
    @GET("map-direction/v1/driving")
    suspend fun getNaverDirections(
        @Header("X-NCP-APIGW-API-KEY-ID") apiKeyId: String,
        @Header("X-NCP-APIGW-API-KEY") apiKey: String,
        @Query("start") start: String,
        @Query("goal") goal: String
    ): DirectionEntity

}