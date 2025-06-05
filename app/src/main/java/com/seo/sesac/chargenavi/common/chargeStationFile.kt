package com.seo.sesac.chargenavi.common

import androidx.compose.ui.graphics.Color
import com.seo.sesac.chargenavi.R

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

/**
 * 충전 단자 이미지 리스트
 * DC차데모, AC3상, DC콤보, 완속
 * */
val iconList = mutableListOf(
    R.drawable.icon_dc_chademo_quick,
    R.drawable.icon_ac_3_phase_standard_quick,
    R.drawable.icon_dc_combo_quick,
    R.drawable.icon_ac_single_phase_standard
)

/**
 * 충전기 사용 가능 -> 초록
 * 사용 불가능 -> 빨강
 * 예약 -> 오렌지
 * 그 외(고장, 통신 장애 등) -> 회색
 * */
fun getStatusColor(cpStat: String): Color =
    when(cpStat) {
        "1" -> Color.Green
        "2" -> Color.Red
        "9" -> Color(0xFFFFA500) // 오렌지
        else -> Color.LightGray
    }

/**
 * DC차데모, AC3상, DC콤보, 완속
 * */
fun getChargeTypeString(icon: Int) =
    when(icon) {
        iconList[0] -> "DC차데모"
        iconList[1] -> "AC3상"
        iconList[2] -> "DC콤보"
        else -> "완속"
    }

/**
 * 충전기 단자와 호환되는 단자 아이콘 리턴
 * */
fun getChargeTypeIcon(cpTp: String): MutableList<Int> {
    val icons = mutableListOf<Int>()

    if (cpTp in listOf("5", "8", "9", "10")) {
        icons.add(iconList[0]) // DC 차데모
    }

    if (cpTp in listOf("6", "9", "10")) {
        icons.add(iconList[1]) // AC 3상
    }

    if (cpTp in listOf("7", "8", "10")) {
        icons.add(iconList[2]) // DC 콤보
    }

    if (icons.isEmpty()) {
        icons.add(iconList[3]) // AC 단상 (완속)
    }

    return icons
}