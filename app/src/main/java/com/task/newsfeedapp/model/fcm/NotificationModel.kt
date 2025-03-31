package com.task.newsfeedapp.model.fcm

import com.google.gson.annotations.SerializedName

data class NotificationModel(
    @SerializedName("NotificationMessage")
    var notificationMessage: String,
    @SerializedName("NotificationTitle")
    var notificationTitle: String,
    @SerializedName("custom_Message")
    var customMessage: String
)
