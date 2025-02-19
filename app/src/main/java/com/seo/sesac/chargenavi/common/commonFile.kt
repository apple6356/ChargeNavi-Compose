package com.seo.sesac.chargenavi.common

import android.widget.Toast
import androidx.compose.ui.graphics.Color
import com.seo.sesac.chargenavi.BuildConfig
import com.seo.sesac.chargenavi.R

fun showToast(message: String = "",  delayTime : Int = Toast.LENGTH_SHORT){
    Toast.makeText(ChargeNaviApplication.getApplication(), message, delayTime).show()
}

val apiKey = BuildConfig.API_KEY
val naverClientId = BuildConfig.NAVER_CLIENT_ID
val naverClientSecret = BuildConfig.NAVER_CLIENT_SCERET

