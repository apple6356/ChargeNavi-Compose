package com.seo.sesac.chargenavi.ui.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

/**
* 바텀 시트 충전소 정보 화면 TODO: 추후 bottom sheet 구현 후 다시 작업
* */
@Composable
fun DetailBottomSheetScreen(onExpand: () -> Unit) {
    // 충전소 정보
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "충전소 이름")

        // 즐겨찾기 버튼
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Filled.FavoriteBorder, contentDescription = null
            )
        }

    }

    Text(text = "충전소 주소")
    Text(text = "충전기 타입")
}