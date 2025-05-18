package com.task.newsfeedapp.utils

import android.util.Log
import com.task.newsfeedapp.BuildConfig

class Logger {
    companion object {
        private var enableLog: Boolean = BuildConfig.DEBUG

        @JvmStatic
        fun isEnableLog(): Boolean {
            return enableLog
        }

        @JvmStatic
        fun setEnableLog(enableLog: Boolean) {
            Logger.enableLog = enableLog
        }

        @JvmStatic
        fun d(tag: String, msg: String) {
            if (isEnableLog()) Log.d(tag, msg)
        }

        @JvmStatic
        fun e(tag: String, msg: String, tr: Throwable?) {
            if (isEnableLog()) Log.e(tag, msg, tr)
        }

        @JvmStatic
        fun i(tag: String, msg: String) {
            if (isEnableLog()) Log.i(tag, msg)
        }

        @JvmStatic
        fun v(tag: String?, msg: String) {
            if (isEnableLog()) Log.v(tag, msg)
        }

        @JvmStatic
        fun w(tag: String, msg: String, exce: Throwable) {
            if (isEnableLog()) Log.w(tag, msg, exce)
        }

        @JvmStatic
        fun e(tag: String, msg: String) {
            if (isEnableLog()) Log.e(tag, msg)
        }
    }
}
