package com.seo.sesac.chargenavi.ui.screen.common

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter

@Composable
fun ProfileImageItem(imageUrl: String) {
    val asyncImagePainter = rememberAsyncImagePainter(
        model = imageUrl,
        contentScale = ContentScale.Crop
    )

    Image(
        modifier = Modifier
            .clip(CircleShape)
            .size(
                70.dp
            ),
        painter = asyncImagePainter,
        contentDescription = "프로필 이미지",
        contentScale = ContentScale.Crop
    )

    when (val coilStateValue = asyncImagePainter.state.collectAsStateWithLifecycle().value) {
        is AsyncImagePainter.State.Loading -> {
            CoilLoadingUI()
        }

        is AsyncImagePainter.State.Error -> {
            CoilLoadingErrorUI()
        }

        is AsyncImagePainter.State.Success -> {
            Log.e("Coil-Log", "$coilStateValue")
        }

        else -> {
            Log.e("Coil-Log-ETC", "$coilStateValue")
        }
    }
}