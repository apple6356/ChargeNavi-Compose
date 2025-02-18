package com.seo.sesac.chargenavi.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.seo.sesac.chargenavi.common.apiKey
import com.seo.sesac.chargenavi.common.naverClientId
import com.seo.sesac.chargenavi.common.naverClientSecret
import com.seo.sesac.data.entity.EvCsInfo
import com.seo.sesac.data.common.RestResult
import com.seo.sesac.data.entity.ResultAddrInfo
import com.seo.sesac.data.repository.http.EvCsRepository
import com.seo.sesac.data.repository.http.GeoCodeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Main 화면과 연관된 ViewModel
 * viewmodel 분리 해야 하는지?
 * */
class MainViewModel(
    private val evCsRepository: EvCsRepository,
    private val geoCodeRepository: GeoCodeRepository
) : ViewModel() {

    /** 충전소 정보 리스트 */
    private val _evCsList =
        MutableStateFlow<RestResult<MutableList<EvCsInfo>>>(RestResult.DummyConstructor)
    val evCsList get() = _evCsList.asStateFlow()

    /** 주소 정보 */
    private val _address =
        MutableStateFlow<RestResult<MutableList<ResultAddrInfo>>>(RestResult.DummyConstructor)
    val address get() = _address.asStateFlow()

    /** 주소로 충전소 검색 후 충전소 목록 가져오는 함수 */
    fun getEvCsList(page: Int = 1, perPage: Int = 10, addr: String = "서울") {
        viewModelScope.launch {
            if(_evCsList.value is RestResult.DummyConstructor) {
                val response = evCsRepository.getEvCsList(page, perPage, addr, apiKey)
                _evCsList.value = RestResult.Success(response.data.toMutableList())
                Log.e("MVM getEvCsList", "${evCsList.value}")
            }
            Log.e("MVM evCsList", "${evCsList.value}")
        }
    }

    /** 좌표 -> 주소 변환 */
    fun convertCoordsToAddress(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val response = geoCodeRepository.coordsToAddress(latitude, longitude, naverClientId, naverClientSecret)
            _address.value = RestResult.Success(response.results.toMutableList())
        }
    }

    /** 해당 좌표의 csId 찾기 */
    fun findByCoords(position: LatLng) =
        evCsList.value.let { result ->
            if (result is RestResult.Success) {
                result.data.find { it.latitude.toDouble() == position.latitude && it.longitude.toDouble() == position.longitude }?.csId
            } else {
                null
            }
        }

    /** csId 가 같은 충전소 정보를 Map 으로 그룹화 */
    fun findByCsId(csId: String): Map<Int, List<EvCsInfo>> =
        evCsList.value.let { result ->
            Log.e("MVM findCSByCsId", "$result")
            if (result is RestResult.Success) {
                Log.e("Charging Station", "${result.data.groupBy { it.csId }}")
                result.data
                    .groupBy { it.csId }
                    .filter { it.key == csId.toInt() }
            } else {
                Log.e("MVM findCSByCsId", "오류 발생")
                emptyMap()
            }
        }

}

