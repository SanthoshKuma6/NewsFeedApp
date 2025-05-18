package com.task.newsfeedapp.utils

import com.scottyab.rootbeer.BuildConfig

object Utils {
//    const val BASE_URL = BuildConfig.BUILD_TYPE
     val BASE_URL = Keys.BASEURL()
    const val api_key = "pf6FgeMTQXi38BAFb9voVvHtrEQlwUlp"
//    const val Saved_Signature = BuildConfig.Saved_Signature
     val Saved_Signature = Keys.APPSIGNATURE()


}