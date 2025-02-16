package com.seo.sesac.chargenavi.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo.sesac.chargenavi.common.NaverOAuth
import com.seo.sesac.data.entity.UserInfo
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.domain.usecase.UserUseCase
import com.seo.sesac.firestore.repository.local.LocalUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 모든 화면에서 사용
 * */
class UserViewModel(
    private val userUseCase: UserUseCase,
    private val localUserRepository: LocalUserRepository
): ViewModel() {

    /** 유저 정보 */
    private val _userInfo =
        MutableStateFlow<FireResult<UserInfo>>(FireResult.DummyConstructor)
    val userInfo get() = _userInfo.asStateFlow()

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

                        // datastore 저장
                        loginUserInfo.run {
                            if (id != null && nickname != null) {
                                localUserRepository.saveUserInfo(id!!, nickname!!)
                            }
                        }

                    }
                } else {
                    Log.e("naver login", "네이버 로그인 실패: ${result.exceptionOrNull()}")
                }
            }
        }
    }

    /**
     * DataStore 에 저장된 유저 정보를 가져옴
     * */
    fun getLocalUserInfo() =
        localUserRepository.getUserInfoFromDatastore()

}