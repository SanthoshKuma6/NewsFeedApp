package com.task.newsfeedapp.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.task.newsfeedapp.R
import com.task.newsfeedapp.activity.NotificationsActivity
import com.task.newsfeedapp.model.fcm.CustomNotificationData
import com.task.newsfeedapp.model.fcm.NotificationModel
import com.task.newsfeedapp.mvvm.viewmodel.ArticleViewModel
import com.task.newsfeedapp.mvvm.viewmodel.MainViewModel
import com.task.newsfeedapp.utils.isAppIsInBackground


class FirebaseMessageReceiver : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendRegistrationToServer(token)
    }
    private fun sendRegistrationToServer(token: String) {
        Log.d("TAG", "sendRegistrationToServer: $token")
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.data.isNotEmpty().let {
            handleNow()
        }
        remoteMessage.data.let {
            Log.d("TAG", "onMessageReceived: ${it["title"]} ${it["body"]}")
        }
        if (isAppIsInBackground(applicationContext)) {

            if (remoteMessage.data["body"] == null && remoteMessage.data["title"] == null) {
                sendNotification(
                    remoteMessage.data["title"].toString(),
                    remoteMessage.data["body"].toString(),
                    if (remoteMessage.data.containsKey("custom_message")) remoteMessage.data["custom_message"].toString() else {
                        ""
                    }
                )

            } else {
                remoteMessage.notification?.body?.let {
                    remoteMessage.notification?.title?.let { it1 ->
                        sendNotification(
                            it,
                            it1,
                            if (remoteMessage.data.containsKey("custom_message")) remoteMessage.data["custom_message"].toString() else {
                                ""
                            }
                        )

                    }

                }
            }
        } else{
            var notificationModel: NotificationModel? =
                if (remoteMessage.data["body"] != null && remoteMessage.data["title"] != null) {
                    NotificationModel(
                        remoteMessage.data["body"].toString(),
                        remoteMessage.data["title"].toString(),
                        if (remoteMessage.data.containsKey("custom_message")) remoteMessage.data["custom_message"].toString() else {
                            ""
                        }
                    )
                }
                else {
                    remoteMessage.notification?.body?.let {
                        remoteMessage.notification?.title?.let { it1 ->
                            NotificationModel(
                                it,
                                it1,
                                if (remoteMessage.data.containsKey("custom_message")) remoteMessage.data["custom_message"].toString() else {
                                    ""
                                }
                            )
                        }
                    }
                }
            notificationModel?.let {
                sendNotificationToViewModel(it)
                Log.d("TAG", "onMessageReceived: $it")
            }

        }




    }


    private fun sendNotification(msgBody: String, msgTitle: String, customMessage: String) {
        var intent = Intent()
        if (customMessage.isNotEmpty()) {
            val customData: CustomNotificationData =
                Gson().fromJson(customMessage, CustomNotificationData::class.java)
            intent = Intent(this, NotificationsActivity::class.java)
            val bundle = Bundle()
            bundle.putString("NOTIFICATION_ALERT_FIREBASE", customData.iscms)
            intent.putExtras(bundle)
        } else {
            intent = Intent(this, NotificationsActivity::class.java)
            val bundle = Bundle()
            bundle.putString("NOTIFICATION_ALERT_FIREBASE", "")
            intent.putExtras(bundle)
        }


        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder =
            NotificationCompat
                .Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(msgTitle)
                .setContentText(msgBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    private fun handleNow() {
        Log.d("TAG", "Short lived task is done ")
    }


    private fun sendNotificationToViewModel(notification: NotificationModel) {

        val mainViewModel = MainViewModel()
        mainViewModel.setNotification(notification)
    }
}