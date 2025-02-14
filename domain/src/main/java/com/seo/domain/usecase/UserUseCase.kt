package com.seo.sesac.domain.usecase

import com.seo.domain.entity.UserInfo
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.domain.repository.UserRepository

class UserUseCase(private val userRepository: UserRepository<UserInfo>) {

    /**
     * 기존 유저는 다시 저장하지 않고 반환 신규 유저는 firestore 에 저장 후 반환
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

//    suspend fun findById(userId: String)
}