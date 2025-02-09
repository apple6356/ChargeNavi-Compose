package com.seo.sesac.chargenavi.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.Favorite
import com.seo.sesac.firestore.datasource.FavoriteDataSourceImpl
import com.seo.sesac.firestore.repository.FavoriteRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val favoriteRepositoryImpl: FavoriteRepositoryImpl = FavoriteRepositoryImpl(FavoriteDataSourceImpl())
): ViewModel() {

    private val _favoriteList =
        MutableStateFlow<FireResult<MutableList<Favorite>>>(FireResult.DummyConstructor)
    val favoriteList get() = _favoriteList

    private val _favorite =
        MutableStateFlow<FireResult<Favorite>>(FireResult.DummyConstructor)
    val favorite get() = _favorite

     fun addFavorite(userId: String, csId: Int) {
        viewModelScope.launch {
            val favorite = Favorite(csId = csId, userId = userId)
            Log.e("fvm addFavorite", "favorite: $favorite")

            val result = favoriteRepositoryImpl.create(favorite)
            _favorite.value = FireResult.Success(result.data)
            Log.e("fvm addFavorite", "_favorite: $_favorite , result: ${result.data}")
        }
    }

    fun getFavorite(userId: String, csId: Int) {
        viewModelScope.launch {
            val result = favoriteRepositoryImpl.findByUserIdAndCsId(userId, csId)
            _favorite.value = FireResult.Success(result.data)
            Log.e("fvm getFavorite", "_favorite: $_favorite , result: ${result.data}")
        }
    }

    fun getFavoriteList(userId: String) {
        viewModelScope.launch {
            val result = favoriteRepositoryImpl.findByUserId(userId)
            _favoriteList.value = FireResult.Success(result.data)
            Log.e("fvm getFavoriteList", "_favoriteList: $_favoriteList , result: ${result.data}")
        }
    }
}