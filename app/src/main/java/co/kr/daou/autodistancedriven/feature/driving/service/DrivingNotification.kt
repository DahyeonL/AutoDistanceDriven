package co.kr.daou.autodistancedriven.feature.driving.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import co.kr.daou.autodistancedriven.R
import co.kr.daou.autodistancedriven.feature.MainActivity
import co.kr.daou.autodistancedriven.util.ActionName

object DrivingNotification {
    const val CHANNEL_ID = "foreground_service_channel"

    fun createNotification(context: Context): Notification {
        val notificationIntent = Intent(context, MainActivity::class.java).apply{
            action = ActionName.MAIN
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity( context,0,notificationIntent,0)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("DrivingNotification")
                .setContentText("현재 거리를 측정 중 입니다.")
                .setSmallIcon(R.drawable.car)
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .build()
        return notification
    }
}
