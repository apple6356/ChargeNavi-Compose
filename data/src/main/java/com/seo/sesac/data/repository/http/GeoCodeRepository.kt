package com.seo.sesac.data.repository.http

import com.seo.sesac.data.datasource.http.GeoCodeDataSource

class GeoCodeRepository(private val geoCodeDataSource: GeoCodeDataSource) {

    suspend fun coordsToAddress(latitude: Double, longitude: Double, clientID: String, clientSecret: String) =
        geoCodeDataSource.coordsToAddress(latitude, longitude, clientID, clientSecret)

    suspend fun addressToCoords(address: String, clientID: String, clientSecret: String) =
        geoCodeDataSource.addressToCoords(address, clientID, clientSecret)

}