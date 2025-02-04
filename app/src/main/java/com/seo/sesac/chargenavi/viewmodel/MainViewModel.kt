package com.seo.sesac.chargenavi.viewmodel

import android.util.Log
import androidx.compose.runtime.internal.illegalDecoyCallException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.seo.sesac.chargenavi.common.apiKey
import com.seo.sesac.chargenavi.common.naverClientId
import com.seo.sesac.chargenavi.common.naverClientSecret
import com.seo.sesac.data.entity.CoodrsAddress
import com.seo.sesac.data.entity.EvCsInfo
import com.seo.sesac.data.common.RestResult
import com.seo.sesac.data.entity.ResultAddrInfo
import com.seo.sesac.data.repository.http.EvCsRepository
import com.seo.sesac.data.repository.http.GeoCodeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** Main 화면과 연관된 ViewModel */
class MainViewModel(
    private val evCsRepository: EvCsRepository,
    private val geoCodeRepository: GeoCodeRepository
) : ViewModel() {

    /** 충전소 정보 리스트 */
    private val _evCsList =
        MutableStateFlow<RestResult<MutableList<EvCsInfo>>>(RestResult.DummyConstructor)
    val evCsList get() = _evCsList.asStateFlow()


    /** csId 별 충전소 정보 리스트 */
//    private val _csList =
//        MutableStateFlow<RestResult<MutableList<EvCsInfo>>>(RestResult.DummyConstructor)
//    val csList get() = _csList.asStateFlow()

    /** 주소 정보 */
    private val _address =
        MutableStateFlow<RestResult<MutableList<ResultAddrInfo>>>(RestResult.DummyConstructor)
    val address get() = _address.asStateFlow()

    /** 좌표 정보 */
    private val _coords =
        MutableStateFlow<RestResult<MutableList<CoodrsAddress>>>(RestResult.DummyConstructor)
    val coords get() = _coords.asStateFlow()

    /** 주소로 충전소 검색 후 충전소 목록 가져오는 함수 */
    fun getEvCsList(page: Int = 1, perPage: Int = 10, addr: String = "서울") {
        viewModelScope.launch {
            val response = evCsRepository.getEvCsList(page, perPage, addr, apiKey)
            _evCsList.value = RestResult.Success(response.data.toMutableList())
            Log.e("evCsList", "${evCsList.value}")
        }
    }

    fun csList() {
    }

    /** 좌표 -> 주소 변환 */
    fun convertCoordsToAddress(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val response = geoCodeRepository.coordsToAddress(latitude, longitude, naverClientId, naverClientSecret)
            _address.value = RestResult.Success(response.results.toMutableList())
        }
    }

    /** 주소 -> 좌표 변환 */
    fun convertAddressToCoords(address: String) {
        viewModelScope.launch {
            val response = geoCodeRepository.addressToCoords(address, naverClientId, naverClientSecret)
            _coords.value = RestResult.Success(response.addresses.toMutableList())
        }
    }

    /** 해당 좌표의 충전소 정보를 가져오는 함수 */
    fun getEvCsInfo(latitude: String, longitude: String) =
        (evCsList.value as RestResult.Success).data.find {
            it.latitude == latitude && it.longitude == longitude
        }

    /** 해당 좌표의 csId 찾기 */
    fun findCsIdByCoords(position: LatLng) =
        evCsList.value.let { result ->
            if (result is RestResult.Success) {
                result.data.find { it.latitude.toDouble() == position.latitude && it.longitude.toDouble() == position.longitude }?.csId
            } else {
                null
            }
        }

    /** csId 가 같은 충전소 정보를 Map 으로 그룹화 */
    fun findCSByCsId(csId: String): Map<Int, List<EvCsInfo>> =
        evCsList.value.let { result ->
            if (result is RestResult.Success) {
                Log.e("Charging Station", "${result.data.groupBy { it.csId }}")
                result.data
                    .groupBy { it.csId }
                    .filter { it.key == csId.toInt() }
            } else {
                emptyMap()
            }
        }

}

