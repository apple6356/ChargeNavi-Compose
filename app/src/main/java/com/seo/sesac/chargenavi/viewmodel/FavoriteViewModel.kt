package com.seo.sesac.chargenavi.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo.sesac.chargenavi.common.showToast
import com.seo.sesac.data.common.FireResult
import com.seo.firestore.datasource.firestore.FavoriteDataSourceImpl
import com.seo.firestore.repository.firestore.FavoriteRepositoryImpl
import com.seo.sesac.chargenavi.common.apiKey
import com.seo.sesac.data.apimodule.RetrofitClient
import com.seo.sesac.data.common.RestResult
import com.seo.sesac.data.datasource.http.EvCsDataSource
import com.seo.sesac.data.entity.EvCsInfo
import com.seo.sesac.data.entity.Favorite
import com.seo.sesac.data.repository.http.EvCsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 즐겨찾기와 관련된 viewmodel
 * */
@SuppressLint("StaticFieldLeak")
class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepositoryImpl = FavoriteRepositoryImpl(
        FavoriteDataSourceImpl()
    ),
    private val evCsRepository: EvCsRepository = EvCsRepository(EvCsDataSource(RetrofitClient.getEvCsApiInstance()))
) : ViewModel() {

    /** 즐겨찾기 리스트 */
    private val _favoriteList =
        MutableStateFlow<FireResult<MutableList<Favorite>>>(FireResult.DummyConstructor)
    val favoriteList get() = _favoriteList.asStateFlow()

    /** 즐겨찾기 된 충전소 정보 리스트 */
    private val _favoriteCsList =
        MutableStateFlow<RestResult<MutableList<EvCsInfo>>>(RestResult.DummyConstructor)
    val favoriteCsList get() = _favoriteCsList.asStateFlow()

    /** 즐겨찾기 상태 */
    private var _isFavorite =
        MutableStateFlow(false)
    val isFavorite get() = _isFavorite.asStateFlow()

    /**
     * 즐겨찾기 등록
     * */
    fun addFavorite(userId: String, csInfo: EvCsInfo) {
        viewModelScope.launch {
            val favorite = Favorite(
                userId = userId, csId = csInfo.csId.toString(), address = csInfo.address
            )

            Log.e("fvm addFavorite", "favorite: $favorite")

            val result = favoriteRepository.create(favorite)
            if (result is FireResult.Success) {
                Log.e("fvm addFavorite", "result: ${result}")
            } else {
                Log.e("fvm addFavorite error", "${(result as FireResult.Failure).exception}")
            }
        }
    }

    /**
     * 즐겨찾기 삭제
     * */
    fun deleteFavorite(userId: String, csId: String) {
        viewModelScope.launch {
            val result = favoriteRepository.delete(userId, csId)
            Log.e("fvm deleteFavorite", "${result}")
        }
    }

    /**
     * 즐겨찾기 목록 가져오기
     * */
    fun getFavoriteList(userId: String) {
        viewModelScope.launch {
            val result = favoriteRepository.findByUserId(userId)
            if (result is FireResult.Success) {
                _favoriteList.value = FireResult.Success(result.data)
                Log.e("FVM", "_favoriteList: ${result.data}")

                // 즐겨찾기 충전소 검색
                val csList = mutableListOf<EvCsInfo>()
                result.data.forEach { favorite ->
                    Log.e("FVM", "favorite: $favorite")
                    val csResult = evCsRepository.getEvCsList(
                        page = 1,
                        perPage = 10,
                        address = favorite.address,
                        apiKey = apiKey
                    )
                    Log.e("FVM", "csResult: $csResult")
                    csResult.data.forEach {
                        csList.add(it)
                    }
                }
                _favoriteCsList.value = RestResult.Success(csList)

                Log.e(
                    "fvm getFavoriteList",
                    "_favoriteList: ${_favoriteList.value} , result: ${result.data}"
                )
                Log.e("fvm getFavoriteList", "_favoriteCSList: ${_favoriteCsList.value}")
            } else {
                _favoriteList.value = FireResult.DummyConstructor
            }
        }
    }

    /**
     * csId 가 같은 충전소 정보를 Map 으로 그룹화
     * *//*fun findByCsId(csId: String): Map<Int, List<EvCsInfo>> =
        favoriteCsList.value.let { result ->
            Log.e("FVM findCSByCsId", "$result")
            if (result is RestResult.Success) {
                Log.e("Charging Station", "${result.data.groupBy { it.csId }}")
                result.data
                    .groupBy { it.csId }
                    .filter { it.key == csId.toInt() }
            } else {
                Log.e("FVM findCSByCsId", "오류 발생")
                emptyMap()
            }
        }*/

    /**
     * findByUserIdAndCsId 결과값이 Success true 아니면 false 리턴하는 함
     * */
    fun isFavorite(userId: String, csId: String) = viewModelScope.launch {
        _isFavorite.value = favoriteRepository.findByUserIdAndCsId(userId, csId) is FireResult.Success
    }

    /**
     * 즐겨찾기 버튼 클릭 시 실행,
     * */
    fun updateFavorite(favoriteState: Boolean) {
        _isFavorite.value = favoriteState
    }

}