package com.task.newsfeedapp.base.utils

import android.widget.Toast
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.task.newsfeedapp.base.BaseApplication.Companion.appContext
import com.task.newsfeedapp.base.network.NetworkUtil

object ToastAppUtil {
    fun showMessage(message: String) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
    }

    fun checkInternetAndShowMessage(): Boolean {
        return if (!NetworkUtil.isConnected()) {
            showMessage("No Internet Connection")
            false
        } else {
            true
        }
    }
}


data class NetworkError(
    val status: Int = -1,
    @Expose()
    @SerializedName("statusCode")
    val statusCode: String = "-1",
    @Expose()
    @SerializedName("message")
    val message: String = "Something went wrong"
)