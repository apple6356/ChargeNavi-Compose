package com.seo.sesac.domain.usecase

import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.UserInfo
import com.seo.sesac.domain.repository.FireStoreRepository

class UserUseCase(private val userRepository: FireStoreRepository<UserInfo>) {

    /**
     * 기존 유저는
     * */
    suspend fun  loginNaverUserClass(user: UserInfo):FireResult<UserInfo> {
        val userInfo = user.id?.let { userRepository.findById(it) }

        if (userInfo is FireResult.Success) {
            if (userInfo.data.id.equals("-1")) {
                println("신규 유저 $user")
                return userRepository.create(user)
            } else {
                println("기존 유저 $user")
                return userInfo
            }
        } else {
            return FireResult.Failure(Exception("오류 발생"))
        }
    }
}