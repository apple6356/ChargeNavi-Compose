package com.seo.sesac.chargenavi.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo.sesac.chargenavi.common.NaverOAuth
import com.seo.sesac.data.entity.UserInfo
import com.seo.sesac.data.common.FireResult
import com.seo.firestore.repository.firestore.UserRepositoryImpl
import com.seo.sesac.chargenavi.common.showToast
import com.seo.sesac.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 모든 화면에서 사용
 * */
class UserViewModel(
    private val userUseCase: UserUseCase,
    private val userRepository: UserRepositoryImpl
): ViewModel() {

    /** 유저 정보 */
    private val _userInfo =
        MutableStateFlow<FireResult<UserInfo>>(FireResult.DummyConstructor)
    val userInfo get() = _userInfo.asStateFlow()

    private val _isLoggedIn =
        MutableStateFlow(false)
    val isLoggedIn get() = _isLoggedIn.asStateFlow()

    /**
     * naver login 메소드, 새로 로그인한 id면 유저 정보 저장
     * */
    fun loginNaver(context: Context) {
        NaverOAuth.login(context) { result ->
            viewModelScope.launch {
                if (result.isSuccess) {
                    val user = result.getOrNull()

                    if (user != null) {
                        val loginUserInfo = UserInfo(
                            id = user.profile?.id,
                            nickname = user.profile?.nickname,
                            profileImage = user.profile?.profileImage
                        )

                        Log.e("naverLogin in viewModel", "${loginUserInfo}")

                        // firestore 저장
                        _userInfo.value = userUseCase.loginNaverUserClass(loginUserInfo)

                    }
                } else {
                    Log.e("naver login", "네이버 로그인 실패: ${result.exceptionOrNull()}")
                }
            }
        }
    }

    /**
     * 유저 정보 StateFlow 에 저장
     * */
    fun findById(userId: String) {
        viewModelScope.launch {
            _userInfo.value = userRepository.findById(userId)
        }
    }

    /**
     * 네이버 로그아웃
     * */
    fun logoutNaver() {
        _userInfo.value = FireResult.DummyConstructor
        NaverOAuth.logout()
    }

    fun isLoggedIn() {
        _isLoggedIn.value = NaverOAuth.isLoggedIn()
    }

    /**
     * 유저 업데이트
     * */
    fun userUpdate(userInfo: UserInfo) {
        viewModelScope.launch {
            val result = userRepository.update(userInfo)
            if (result is FireResult.Success) {
                showToast("유저 정보 업데이트 성공")
            }
        }
    }

}