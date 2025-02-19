package com.seo.sesac.chargenavi.common

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import com.google.firebase.FirebaseApp
import com.naver.maps.map.NaverMapSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.seo.sesac.chargenavi.BuildConfig

class ChargeNaviApplication: Application(), SingletonImageLoader.Factory {
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

    /**
     * coil image loader
     * */
    override fun newImageLoader(context: PlatformContext): ImageLoader = ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context, COIL_MEMORY_CACHE_SIZE_PERCENT)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(filesDir.resolve(COIL_DISK_CACHE_DIR_NAME))
                .maximumMaxSizeBytes(COIL_DISK_CACHE_MAX_SIZE.toLong())
                .build()
        }
        .build()
}