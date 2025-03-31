package com.task.newsfeedapp.model.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

fun getDeviceToken() {
    FirebaseMessaging.getInstance().token
        .addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM token failed", task.exception)
                return@addOnCompleteListener
            }
            val token = task.result
            Log.d("FCM", "Device Token: $token")
        }
}
