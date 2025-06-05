package com.seo.sesac.data.repository.http

import com.seo.sesac.data.datasource.http.NaverMapsDataSource

class NaverMapsRepository(private val naverMapsDataSource: NaverMapsDataSource) {

    suspend fun coordsToAddress(latitude: Double, longitude: Double, clientID: String, clientSecret: String) =
        naverMapsDataSource.coordsToAddress(latitude, longitude, clientID, clientSecret)

    suspend fun getDirections(start: String, goal: String, clientID: String, clientSecret: String) =
        naverMapsDataSource.getDirections(start, goal, clientID, clientSecret)

}