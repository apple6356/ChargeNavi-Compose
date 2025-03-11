package com.seo.sesac.chargenavi.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.seo.sesac.chargenavi.R

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.notification?.title ?: "알림"
        val message = remoteMessage.notification?.body ?: "새로운 메시지가 도착했습니다."

        Log.d("FCM", "Push Received: $title - $message")

        sendNotification(title, message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "새로운 FCM 토큰: $token")
    }

    private fun sendNotification(title: String, message: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "FCM_CHANNEL"

        val channel = NotificationChannel(
            channelId,
            "FCM 알림",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.marker_cs_image)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(0, notification)
    }
}