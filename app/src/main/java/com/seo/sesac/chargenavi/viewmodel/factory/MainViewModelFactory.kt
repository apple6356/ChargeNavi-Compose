package com.seo.sesac.chargenavi.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seo.sesac.chargenavi.viewmodel.MainViewModel
import com.seo.sesac.data.repository.http.EvCsRepository
import com.seo.sesac.data.repository.http.GeoCodeRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val evCsRepository: EvCsRepository,
    private val geoCodeRepository: GeoCodeRepository
//    private val locationRepository: LocationRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(evCsRepository, geoCodeRepository/*, locationRepository*/) as T
        }
        throw IllegalArgumentException("해당 뷰모델을 찾을수 없습니다")
    }
}