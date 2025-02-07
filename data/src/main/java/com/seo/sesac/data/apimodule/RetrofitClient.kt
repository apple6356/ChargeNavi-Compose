package com.seo.sesac.data.apimodule

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val TARGET_ADDRESS = "https://api.odcloud.kr/api/"
    private const val NAVER_GEO_CODE_TARGET_ADDRESS = "https://naveropenapi.apigw.ntruss.com/"

    private lateinit var restService: EvCsService
    private lateinit var naverRestService: NaverService

    val httpLogging = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLogging)
        .build()

    fun getRetrofitInstance(): EvCsService {
        if (!this::restService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(TARGET_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            restService = retrofit.create(EvCsService::class.java)
        }
        return restService
    }

    fun getNaverGeoCodeInstance(): NaverService {
        if (!this::naverRestService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(NAVER_GEO_CODE_TARGET_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            naverRestService = retrofit.create(NaverService::class.java)
        }
        return naverRestService
    }

}