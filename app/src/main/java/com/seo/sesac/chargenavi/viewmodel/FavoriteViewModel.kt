package com.seo.sesac.chargenavi.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo.sesac.chargenavi.common.showToast
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.Favorite
import com.seo.sesac.firestore.datasource.firestore.FavoriteDataSourceImpl
import com.seo.sesac.firestore.repository.firestore.FavoriteRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * 즐겨찾기와 관련된 viewmodel
 * */
class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepositoryImpl = FavoriteRepositoryImpl(FavoriteDataSourceImpl())
): ViewModel() {

    private val _favoriteList =
        MutableStateFlow<FireResult<MutableList<Favorite>>>(FireResult.DummyConstructor)
    val favoriteList get() = _favoriteList

    private val _favorite =
        MutableStateFlow<FireResult<Favorite>>(FireResult.DummyConstructor)
    val favorite get() = _favorite

    /**
     * 즐겨찾기 등록
     * */
     fun addFavorite(userId: String, csId: String) {
        viewModelScope.launch {
            val favorite = Favorite(csId = csId, userId = userId)
            Log.e("fvm addFavorite", "favorite: $favorite")

            val result = favoriteRepository.create(favorite)
            if (result is FireResult.Success) {
                _favorite.value = result
                Log.e("fvm addFavorite", "_favorite: $_favorite , result: ${result}")
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
            // _favorite 초기화
            _favorite.value = FireResult.DummyConstructor
        }
    }

    fun getFavorite(userId: String, csId: String) {
        viewModelScope.launch {
            val result = favoriteRepository.findByUserIdAndCsId(userId, csId)
            _favorite.value = result
            Log.e("fvm getFavorite", "_favorite: $_favorite , result: ${result}")
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
                Log.e("fvm getFavoriteList", "_favoriteList: $_favoriteList , result: ${result.data}")
            } else {
                _favoriteList.value = FireResult.DummyConstructor
                showToast("불러오기 실패")
            }
        }
    }

    /**
     * findByUserIdAndCsId 결과값이 Success이면 true 아니면 false를 리턴하는 메서드
     * */
    suspend fun isFavorite(userId: String, csId: String): Boolean =
        favoriteRepository.findByUserIdAndCsId(userId, csId) is FireResult.Success

}