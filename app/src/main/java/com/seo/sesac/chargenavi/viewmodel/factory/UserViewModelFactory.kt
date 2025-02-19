package com.seo.sesac.chargenavi.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seo.firestore.datasource.firestore.UserDataSourceImpl
import com.seo.firestore.repository.firestore.UserRepositoryImpl
import com.seo.sesac.chargenavi.viewmodel.UserViewModel
import com.seo.sesac.domain.usecase.UserUseCase

@Suppress("UNCHECKED_CAST")
val userViewModelFactory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(UserViewModel::class.java) ->
                    userViewModel
                else ->
                    throw IllegalArgumentException("뷰모델을 찾을 수 없습니다")
            }
        } as T
}

private val userViewModel by lazy {
    UserViewModel(
        userUserCase, userRepository
    )
}

private val userUserCase by lazy {
    UserUseCase(UserRepositoryImpl(UserDataSourceImpl()))
}

private val userRepository by lazy {
    UserRepositoryImpl(UserDataSourceImpl())
}