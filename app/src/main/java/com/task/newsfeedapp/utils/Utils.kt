package com.task.newsfeedapp.utils

import androidx.compose.ui.input.key.Key
import com.task.newsfeedapp.BuildConfig

object Utils {
//    const val BASE_URL = BuildConfig.BASE_URL
     val BASE_URL = Keys.BASEURL()
    const val api_key = "pf6FgeMTQXi38BAFb9voVvHtrEQlwUlp"
//    const val Saved_Signature = BuildConfig.Saved_Signature
     val Saved_Signature = Keys.APPSIGNATURE()

    fun sanitizeEmail(email: String?): String {
        return email?.replace(".", "_")?.replace("@", "_") ?: "unknown"
    }

}