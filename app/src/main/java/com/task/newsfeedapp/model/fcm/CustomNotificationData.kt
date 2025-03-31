package com.task.newsfeedapp.model.fcm

import com.google.gson.annotations.SerializedName

data class CustomNotificationData(
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("iscms")
    val iscms: String = "",
    @SerializedName("title")
    val title: String?=null,
    @SerializedName("body")
    val body: String?=null

)
