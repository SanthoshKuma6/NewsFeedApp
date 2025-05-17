package com.task.newsfeedapp.base

import android.content.SharedPreferences
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit


@Singleton
class AppPreferences @Inject constructor(private val prefs: SharedPreferences) {

    companion object {

        const val APP_INSTALLATION_ID = "APP_INSTALLATION_ID"

        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
        const val REFRESH_TOKEN_EXPIRE = "REFRESH_TOKEN_EXPIRE"
        const val ACCESS_TOKEN_EXPIRE = "ACCESS_TOKEN_EXPIRE"
    }


    fun isValidString(key:String,value:String): Any {
        return try {
            return prefs.edit(commit = true) { putString(key, value) }
        } catch (e: IOException) {
            false
        } catch (e: Exception) {
            false
        }
    }
    fun isValidBol(key:String,value:Boolean): Boolean {
        return try {
            return prefs.edit().putBoolean(key,value).commit()
        } catch (e: IOException) {
            false
        } catch (e: Exception) {
            false
        }
    }

    fun isValidLong(key:String,value:Long): Boolean {
        return try {
            return prefs.edit().putLong(key,value).commit()
        } catch (e: IOException) {
            false
        } catch (e: Exception) {
            false
        }
    }
    fun isValidInt(key:String, value:Int): Boolean {
        return try {
            return prefs.edit().putInt(key,value).commit()
        } catch (e: IOException) {
            false
        } catch (e: Exception) {
            false
        }
    }

    fun isValidRemove(key:String): Boolean {
        return try {
            return prefs.edit().remove(key).commit()
        } catch (e: IOException) {
            false
        } catch (e: Exception) {
            false
        }
    }


    fun getAppInstallationID(): String? {
        return prefs.getString(APP_INSTALLATION_ID, "")
    }

    fun setAppInstallationID(installationId: String) {
        prefs.edit() { putString(APP_INSTALLATION_ID, installationId) }
    }


    fun setAccessToken(accessToken: String) {
        prefs.edit() { putString(ACCESS_TOKEN, accessToken) }
    }

    fun getAccessToken(): String? {
        return prefs.getString(ACCESS_TOKEN, "")
    }

    fun setRefreshToken(refreshToken: String) {
        prefs.edit() { putString(REFRESH_TOKEN, refreshToken) }
    }

    fun getRefreshToken(): String? {
        return prefs.getString(REFRESH_TOKEN, "")
    }

    fun setRefreshTokenExpire(refreshTokenExpire: Long) {
        prefs.edit() { putLong(REFRESH_TOKEN_EXPIRE, refreshTokenExpire) }
    }

    fun getRefreshTokenExpire(): Long {
        return prefs.getLong(REFRESH_TOKEN_EXPIRE, 0)
    }

    fun setAccessTokenExpire(accessTokenExpire: Long) {
        prefs.edit() { putLong(ACCESS_TOKEN_EXPIRE, accessTokenExpire) }
    }

    fun getAccessTokenExpire(): Long {
        return prefs.getLong(ACCESS_TOKEN_EXPIRE, 0)
    }
}