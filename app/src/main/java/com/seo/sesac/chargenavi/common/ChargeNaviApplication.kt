package com.seo.sesac.chargenavi.common

import android.app.Application
import com.google.firebase.FirebaseApp
import com.naver.maps.map.NaverMapSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.seo.sesac.chargenavi.BuildConfig

class ChargeNaviApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        myContext = this

        // firebase 초기화
        FirebaseApp.initializeApp(this)

        // 네이버 맵 초기화
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NAVER_CLIENT_ID)

        // naver login 초기화
        NaverIdLoginSDK.initialize(
            this,
            BuildConfig.NAVER_OAUTH_CLIENT_ID,
            BuildConfig.NAVER_OAUTH_CLIENT_SCERET,
            "ChargeNavi"
        )

    }

    companion object {
        private lateinit var myContext: ChargeNaviApplication
        fun getApplication() = myContext
    }
}