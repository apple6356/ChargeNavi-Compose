package com.seo.sesac.chargenavi.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo.sesac.chargenavi.common.NaverOAuth
import com.seo.sesac.data.entity.UserInfo
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.firestore.datasource.firestore.UserDataSourceImpl
import com.seo.sesac.firestore.repository.firestore.UserRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepositoryImpl = UserRepositoryImpl(UserDataSourceImpl())
): ViewModel() {
    /** 유저 정보 */
    private val _userInfo =
        MutableStateFlow<FireResult<UserInfo>>(FireResult.DummyConstructor)
    val userInfo get() = _userInfo.asStateFlow()

    fun getUserInfo(userId: String) {
        viewModelScope.launch {
            val result = userRepository.findById(userId)

            _userInfo.value = result
        }
    }

    /**
     * naver login 메소드, 새로 로그인한 id면 유저 정보 저장
     * */
    fun naverLogin(context: Context) {
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

                        _userInfo.value = userRepository.create(loginUserInfo)

                    }
                } else {
                    Log.e("naver login", "네이버 로그인 실패: ${result.exceptionOrNull()}")
                }
            }
        }
    }

}