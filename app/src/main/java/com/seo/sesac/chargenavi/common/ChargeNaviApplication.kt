package com.seo.sesac.chargenavi.common

import android.app.Application
import com.naver.maps.map.NaverMapSdk
import com.seo.sesac.chargenavi.BuildConfig

class ChargeNaviApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        myContext = this

        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NAVER_CLIENT_ID)
    }

    companion object {
        private lateinit var myContext: ChargeNaviApplication
        fun getApplication() = myContext
    }
}