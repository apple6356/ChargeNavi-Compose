package com.seo.sesac.chargenavi.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seo.sesac.chargenavi.common.ChargeNaviApplication
import com.seo.sesac.chargenavi.viewmodel.UserViewModel
import com.seo.sesac.domain.usecase.UserUseCase
import com.seo.sesac.firestore.datasource.firestore.UserDataSourceImpl
import com.seo.sesac.firestore.datasource.local.LocalUserDatasource
import com.seo.sesac.firestore.repository.firestore.UserRepositoryImpl
import com.seo.sesac.firestore.repository.local.LocalUserRepository

val userViewModelFactory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(UserViewModel::class.java) ->
                    UserViewModel(
                        UserUseCase(UserRepositoryImpl(UserDataSourceImpl())),
                        LocalUserRepository(LocalUserDatasource(ChargeNaviApplication.getApplication()))
                    )
                else ->
                    throw IllegalArgumentException("뷰모델을 찾을 수 없습니다")
            }
        } as T
}