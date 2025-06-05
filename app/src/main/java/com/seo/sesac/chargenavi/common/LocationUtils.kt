package com.seo.sesac.chargenavi.common

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

/**
* 위치 관련 작업 처리 클래스
* */
class LocationUtils(context: Context) {

    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    /**
    * 기기의 마지막으로 알려진 위치를 가져온다
    * */
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(callback: (Location?) -> Unit) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                callback(location)
            }
            .addOnFailureListener {
                callback(null)
            }
    }
}