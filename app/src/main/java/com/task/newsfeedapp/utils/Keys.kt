package com.task.newsfeedapp.utils

/**
 * SANTHOSH 22/03/25
 */

object Keys {
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    init {
        System.loadLibrary("native-lib")
    }
    external fun BASEURL(): String
    external fun APPSIGNATURE(): String

}