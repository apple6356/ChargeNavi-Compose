package com.seo.sesac.chargenavi.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seo.sesac.chargenavi.viewmodel.MainViewModel
import com.seo.sesac.data.apimodule.RetrofitClient
import com.seo.sesac.data.datasource.http.EvCsDataSource
import com.seo.sesac.data.datasource.http.GeoCodeDataSource
import com.seo.sesac.data.repository.http.EvCsRepository
import com.seo.sesac.data.repository.http.GeoCodeRepository

/**
 * MainViewModelFactory 작성
 * 수정(25-02-12): ViewModel의 반복적인 초기화 현상을 막기위해 ViewModel과 Repository를 by lazy로 처음 한번만 초기화하도록 변경
 * */
@Suppress("UNCHECKED_CAST")
val mainViewModelFactory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    mainViewModel

                else ->
                    throw IllegalArgumentException("해당 뷰모델을 찾을수 없습니다")
            }
        } as T
}

private val mainViewModel by lazy {
    MainViewModel(
        evCsRepository, geoCodeRepository
    )
}

private val evCsRepository by lazy {
    EvCsRepository(EvCsDataSource(RetrofitClient.getEvCsApiInstance()))
}

private val geoCodeRepository by lazy {
    GeoCodeRepository(GeoCodeDataSource(RetrofitClient.getNaverGeoCodeInstance()))
}