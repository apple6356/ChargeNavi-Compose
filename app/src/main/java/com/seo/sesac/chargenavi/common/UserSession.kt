package com.seo.sesac.chargenavi.common

import com.seo.domain.entity.UserInfo


/**
 * 앱 전역에서 유저 정보를 관리할 수 있는 객체
 * */
object UserSession {
    private var userInfo: UserInfo? = null

    /**
     * 유저 정보 저장
     * */
    fun setUserInfo(user: UserInfo) {
        userInfo =  user
    }

    /**
     * 유저 정보 리턴
     * */
    fun getUserInfo() = userInfo

    /**
     * 유저 정보 클리어
     * */
    fun clearUserSession() {
        userInfo = null
    }

    /**
     * logIn 상태 확인
     * */
    fun isLogIn() =
        userInfo != null

}