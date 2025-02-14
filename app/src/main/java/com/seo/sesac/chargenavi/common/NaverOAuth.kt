package com.seo.sesac.chargenavi.common

import android.content.Context
import android.util.Log
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfile
import com.navercorp.nid.profile.data.NidProfileResponse
import com.seo.domain.entity.UserInfo

/**
 * Naver Login 위한 파일
 * */
object NaverOAuth {

    /**
     * 네이버 로그인
     * */
    fun naverLogin(context: Context) {

        NaverIdLoginSDK.authenticate(context, object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
//                Log.e("naver login", "naver login success")
//                Log.e("naver login", "getAccessToken:${NaverIdLoginSDK.getAccessToken()}")
//                Log.e("naver login", "getAccessToken:${NaverIdLoginSDK.getRefreshToken()}")
//                Log.e("naver login", "getExpiresAt:${NaverIdLoginSDK.getExpiresAt()}")
//                Log.e("naver login", "getExpiresAt:${NaverIdLoginSDK.getTokenType()}")
//                Log.e("naver login", "getState:${NaverIdLoginSDK.getState()}")

            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                showToast("errorCode:$errorCode, errorDesc:$errorDescription")
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        })
    }

    fun login(context: Context, callback: (Result<NidProfileResponse>) -> Unit) {
        NaverIdLoginSDK.authenticate(context, object : OAuthLoginCallback {
            override fun onSuccess() {
                /*Log.e("naver login", "naver login success")
                Log.e("naver login", "getAccessToken:${NaverIdLoginSDK.getAccessToken()}")
                Log.e("naver login", "getAccessToken:${NaverIdLoginSDK.getRefreshToken()}")
                Log.e("naver login", "getExpiresAt:${NaverIdLoginSDK.getExpiresAt()}")
                Log.e("naver login", "getExpiresAt:${NaverIdLoginSDK.getTokenType()}")
                Log.e("naver login", "getState:${NaverIdLoginSDK.getState()}")*/

                NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                    override fun onSuccess(result: NidProfileResponse) {
//                        Log.e("callProfile", "$result")
                        callback(Result.success(result))
                    }

                    override fun onFailure(httpStatus: Int, message: String) {
                        callback(Result.failure(Exception("네이버 로그인 실패: $message")))
                    }

                    override fun onError(errorCode: Int, message: String) {
                        callback(Result.failure(Exception("네이버 로그인 오류: $message")))
                    }
                })
            }

            override fun onFailure(httpStatus: Int, message: String) {
                callback(Result.failure(Exception("네이버 로그인 실패: $message")))
            }

            override fun onError(errorCode: Int, message: String) {
                callback(Result.failure(Exception("네이버 로그인 오류: $message")))
            }
        })
    }

    fun logout() {
        NaverIdLoginSDK.logout()
    }

    fun getProfile(onSuccess: (UserInfo?) -> Unit) {
        NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(result: NidProfileResponse) {
                val profile = result.profile

                var userInfo = UserInfo()

                if (profile != null) {
                     userInfo = UserInfo(
                        id = profile.id,
                        nickname = profile.nickname,
                        profileImage = profile.profileImage
                    )
                }
                onSuccess(userInfo)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                showToast("Nid errorCode:$errorCode, errorDescription: $errorDescription")
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        })
    }


}