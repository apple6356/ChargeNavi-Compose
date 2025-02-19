package com.seo.sesac.chargenavi.common

import android.content.Context
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.seo.sesac.data.entity.UserInfo

/**
 * Naver Login 위한 파일
 * */
object NaverOAuth {

    /**
     * 네이버 로그인
     * */
    fun login(context: Context, callback: (Result<NidProfileResponse>) -> Unit) {
        NaverIdLoginSDK.authenticate(context, object : OAuthLoginCallback {
            override fun onSuccess() {

                NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                    override fun onSuccess(result: NidProfileResponse) {
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

    /**
     * 네이버 로그인 상태 확인
     */
    fun isLoggedIn(): Boolean {
        val accessToken = NaverIdLoginSDK.getAccessToken()
        return !accessToken.isNullOrEmpty()
    }

    fun logout() {
        NaverIdLoginSDK.logout()
        showToast("로그아웃 되었습니다.")
    }

    fun getProfile(onSuccess: (NidProfileResponse) -> Unit) {
        NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(result: NidProfileResponse) {
                onSuccess(result)
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