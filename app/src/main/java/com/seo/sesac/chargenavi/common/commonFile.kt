package com.seo.sesac.chargenavi.common

import android.widget.Toast
import com.seo.sesac.chargenavi.BuildConfig

fun showToast(message: String = "",  delayTime : Int = Toast.LENGTH_SHORT){
    Toast.makeText(ChargeNaviApplication.getApplication(), message, delayTime).show()
}

val apiKey = BuildConfig.API_KEY
val naverClientId = BuildConfig.NAVER_CLIENT_ID
val naverClientSecret = BuildConfig.NAVER_CLIENT_SCERET

/** 충전 단자 타입 */
val cpTpMap = mapOf(
    "1" to "B타입(5핀)",
    "2" to "C타입(5핀)",
    "3" to "BC타입(5핀)",
    "4" to "BC타입(7핀)",
    "5" to "DC차 데모",
    "6" to "AC 3상",
    "7" to "DC콤보",
    "8" to "DC차데모+DC콤보",
    "9" to "DC차데모+AC3상",
    "10" to "DC차데모+DC콤보, AC3상"
)

/** 충전 속도 */
val chargeTpMap = mapOf(
    "1" to "완속",
    "2" to "급속"
)

/** 충전기 상태 */
val cpStatMap = mapOf(
    "0" to "상태확인불가",
    "1" to "충전 가능",
    "2" to "충전 중",
    "3" to "고장/점검",
    "4" to "통신 장애",
    "5" to "통신미연결",
    "9" to "충전 예약",
)
