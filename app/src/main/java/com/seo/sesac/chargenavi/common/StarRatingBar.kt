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


private val starSize = 20.dp
private val writeStarSize = 50.dp
private val starSpacing = 4.dp

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
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Filled.Star else Icons.Outlined.Star
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
                    .size(writeStarSize)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
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
                modifier = Modifier.size(starSize)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}