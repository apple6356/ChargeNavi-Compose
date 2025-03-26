package com.seo.sesac.chargenavi.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


/**
 * 별점을 매길 수 있는 바
 * */
@Composable
fun ChangedStarRatingBar(
    maxStars: Int = 5,
    rating: Int,
    onRatingChanged: (Int) -> Unit
) {

    Row(
        modifier = Modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            // 현재 별이 선택되었는지
            val isSelected = i <= rating

            // 별점이 선택되지 않았다면 빈 별
            val icon = if (isSelected) Icons.Filled.Star else Icons.Outlined.Star

            // 별 선택 여부에 따라 색 변경
            val iconTintColor = if (isSelected) Color(0xFFFFC700) else Color(0xFFD3D3D3)

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = { onRatingChanged(i) }
                    )
                    .size(WRITE_STAR_SIZE.dp)
            )

            // 마지막 별이 아니면 별 사이 간격
            if (i < maxStars) {
                Spacer(modifier = Modifier.width(STAR_SPACING.dp))
            }
        }
    }
}

/**
 * 별점을 매길 수 없는 읽기 전용 바
 * */
@Composable
fun ReadOnlyStarRatingBar(
    maxStars: Int = 5,
    rating: Int
) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Filled.Star else Icons.Outlined.Star
            val iconTintColor = if (isSelected) Color(0xFFFFC700) else Color(0xFFD3D3D3)

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier.size(STAR_SIZE.dp)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(STAR_SPACING.dp))
            }
        }
    }
}