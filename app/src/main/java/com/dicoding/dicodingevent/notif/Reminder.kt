package com.dicoding.dicodingevent.notif

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dicoding.dicodingevent.R
import com.dicoding.dicodingevent.data.retrofit.ApiConfig

class Reminder(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val response = ApiConfig.getApiService().eventGet(active = "-1", limit = "1")
            val event = response.listEvents?.firstOrNull()

            if (event != null) {
                showNotification(event.name ?: "Event", event.beginTime ?: "Time not available")
            }

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }


    @SuppressLint("MissingPermission", "NewApi")
    private fun showNotification(title: String, content: String) {
        val channelId = "event_reminder_channel"
        val notificationId = 1

        val name = "Event Reminder"
        val descriptionText = "Channel for event reminders"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.notif)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(notificationId, builder.build())
        }
    }
}