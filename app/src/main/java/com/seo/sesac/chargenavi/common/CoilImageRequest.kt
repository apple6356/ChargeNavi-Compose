package com.seo.sesac.chargenavi.common

import android.util.Log
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.size.Size
import com.seo.sesac.chargenavi.R

const val COIL_CALL_TAG = "COIL_TAG"

class CoilImageRequest {
    companion object {
        fun getImageRequest(sourceImage: String) =
            ImageRequest.Builder(ChargeNaviApplication.getApplication())
                .data(data = sourceImage)
                .size(Size.ORIGINAL)
                .error(R.drawable.error_image)
                .crossfade(true)
                .build()
    }

    /**
     * coil callback log
     * 개발 시 모니터링
     * */
    fun coilCallbackLog(imageSource: String, coilState: AsyncImagePainter.State) {
        when(coilState) {
            is AsyncImagePainter.State.Success -> {
                Log.e(COIL_CALL_TAG, "$imageSource, Success")
            }
            is AsyncImagePainter.State.Error -> {
                Log.e(COIL_CALL_TAG, "$imageSource, ${coilState.result.throwable.message}")
            }
            is AsyncImagePainter.State.Empty -> {
                Log.e(COIL_CALL_TAG, "$imageSource, $coilState")
            }
            is AsyncImagePainter.State.Loading -> {
                Log.e(COIL_CALL_TAG, "$imageSource, $coilState")
            }
            else -> {
                Log.e(COIL_CALL_TAG, "$imageSource, 알 수 없는 Coil Error : $coilState")
            }
        }
    }
}