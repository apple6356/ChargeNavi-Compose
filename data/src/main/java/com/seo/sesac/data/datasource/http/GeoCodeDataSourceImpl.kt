package com.seo.sesac.data.datasource.http

import com.seo.sesac.data.apimodule.NaverService

class GeoCodeDataSourceImpl(private val restService: NaverService) {

    suspend fun coordsToAddress(latitude: Double, longitude: Double, clientID: String, clientSecret: String) =
        restService.getNaverReverseGeoCode(
            apiKeyId = clientID, apiKey = clientSecret, coords = "$longitude,$latitude"
        )

    suspend fun addressToCoords(address: String, clientID: String, clientSecret: String) =
        restService.getNaverGeoCode(
            apiKeyId = clientID, apiKey = clientSecret, query = address
        )
}