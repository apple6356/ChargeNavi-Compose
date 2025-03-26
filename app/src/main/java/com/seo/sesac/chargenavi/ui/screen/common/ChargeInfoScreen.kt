package com.seo.sesac.chargenavi.ui.screen.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.seo.sesac.chargenavi.common.cpStatMap
import com.seo.sesac.chargenavi.common.getChargeTypeIcon
import com.seo.sesac.chargenavi.common.getChargeTypeString
import com.seo.sesac.chargenavi.common.getStatusColor
import com.seo.sesac.chargenavi.common.iconList
import com.seo.sesac.data.entity.EvCsInfo

/**
 * 충전기 정보 아이템
 * */
@Composable
fun ChargeInfoScreen(
    csInfo: EvCsInfo
) {

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
                        if (icon in csIconList) {

                            Icon( // 충전기 타입 이미지
                                painter = painterResource(icon),
                                contentDescription = null,
                                tint = statusColor
                            )

                            Text( // 충전기 타입 문자
                                text = getChargeTypeString(icon),
                                color = statusColor,
                                fontSize = 12.sp
                            )

                        } else {
                            Icon( // 충전기 타입 이미지
                                painter = painterResource(icon),
                                contentDescription = null,
                                tint = Color.LightGray
                            )

                            Text( // 충전기 타입 문자
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

