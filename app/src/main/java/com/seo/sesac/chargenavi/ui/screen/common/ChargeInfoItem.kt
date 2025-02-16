package com.seo.sesac.chargenavi.ui.screen.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seo.sesac.chargenavi.R
import com.seo.sesac.chargenavi.common.cpStatMap
import com.seo.sesac.data.entity.EvCsInfo

/**
 * 충전기 정보 아이템
 * */
@Composable
fun ChargeInfoItem(csInfo: EvCsInfo) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 8.dp, horizontal = 8.dp
            )
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(5.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) { // 충전기 이름
            Text(
                text = csInfo.cpNm,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )

            // 충전기 상태에 따라 색 변경
            val statusColor = getStatusColor(csInfo.cpStat)

            // 충전 단자 이미지 리스트
            val iconList = mutableListOf(
                R.drawable.icon_dc_chademo_quick,
                R.drawable.icon_ac_3_phase_standard_quick,
                R.drawable.icon_dc_combo_quick,
                R.drawable.icon_ac_single_phase_standard
            )

            // 해당 충전기에서 호환되는 충전 단자 이미지 리스트
            val csIconList = getChargeTypeIcon(csInfo.cpTp)

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // 충전기 상태
                cpStatMap[csInfo.cpStat]?.let {
                    Text(
                        text = it,
                        color = statusColor,
                        fontSize = 12.sp
                    )
                }

                iconList.forEach { icon -> // 충전 단자 이미지 나열
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (icon in csIconList) { // 충전기 타입 이미지
                            Icon(
                                painter = painterResource(icon),
                                contentDescription = "충전 단자 이미지",
                                tint = statusColor
                            ) // 충전기 타입 문자
                            Text(
                                text = getChargeTypeString(icon),
                                color = statusColor,
                                fontSize = 12.sp
                            )

                        } else { // 충전기 타입 이미지
                            Icon(
                                painter = painterResource(icon),
                                contentDescription = "충전 단자 이미지",
                                tint = Color.LightGray
                            ) // 충전기 타입 문자
                            Text(
                                text = getChargeTypeString(icon),
                                color = Color.LightGray,
                                fontSize = 12.sp
                            )

                        }
                    }
                }
            }

        }
    }


}

fun getStatusColor(cpStat: String): Color =
    when(cpStat) {
        "1" -> Color.Green
        "2" -> Color.Red
        "9" -> Color(0xFFFFA500) // 오렌지
        else -> Color.LightGray
    }

fun getChargeTypeString(icon: Int) =
    when(icon) {
        R.drawable.icon_dc_chademo_quick -> "DC차데모"
        R.drawable.icon_ac_3_phase_standard_quick -> "AC3상"
        R.drawable.icon_dc_combo_quick -> "DC콤보"
        else -> "완속"
    }

fun getChargeTypeIcon(cpTp: String): MutableList<Int> {
    val icons = mutableListOf<Int>()

    if (cpTp in listOf("5", "8", "9", "10")) {
        icons.add(R.drawable.icon_dc_chademo_quick) // DC 차데모
    }

    if (cpTp in listOf("6", "9", "10")) {
        icons.add(R.drawable.icon_ac_3_phase_standard_quick) // AC 3상
    }

    if (cpTp in listOf("7", "8", "10")) {
        icons.add(R.drawable.icon_dc_combo_quick) // DC 콤보
    }

    if (icons.isEmpty()) {
        icons.add(R.drawable.icon_ac_single_phase_standard) // AC 단상 (완속)
    }

    return icons
}