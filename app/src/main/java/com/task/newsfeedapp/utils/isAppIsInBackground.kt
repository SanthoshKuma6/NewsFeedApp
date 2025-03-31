package com.task.newsfeedapp.utils

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Build.VERSION_CODES

fun isAppIsInBackground(context: Context): Boolean {
    var isInBackground = true
    val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    if (Build.VERSION.SDK_INT > VERSION_CODES.KITKAT_WATCH) {
        val runningProcesses = am.runningAppProcesses
        if (runningProcesses != null) {
            for (processInfo in runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (activeProcess in processInfo.pkgList) {
                        if (activeProcess == context.packageName) {
                            isInBackground = false
                        }
                    }
                }
            }
        } else {

        }
    } else {
        val taskInfo = am.getRunningTasks(1)
        val componentInfo = taskInfo[0].topActivity
        if (componentInfo!!.packageName == context.packageName) {
            isInBackground = false
        }
    }

    return isInBackground
}