package com.seo.sesac.chargenavi.ui.screen.common

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * HorizontalDivider Modifier,
 * padding top and bottom -> 5.dp
 * */
@SuppressLint("ModifierFactoryUnreferencedReceiver")
@Composable
fun Modifier.dividerModifier(): Modifier =
    Modifier
        .padding(
            top = 5.dp,
            bottom = 5.dp
        )