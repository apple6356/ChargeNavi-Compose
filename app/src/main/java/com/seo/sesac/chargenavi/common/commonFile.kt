package com.seo.sesac.chargenavi.common

import android.widget.Toast

fun showToast(message: String = "",  delayTime : Int = Toast.LENGTH_SHORT){
    Toast.makeText(ChargeNaviApplication.getApplication(), message, delayTime).show()
}
