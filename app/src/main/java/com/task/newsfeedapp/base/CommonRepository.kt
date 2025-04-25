package com.task.newsfeedapp.base

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommonRepository
@Inject constructor(
    private val iNetworkService: INetworkService,
    private val appPreferences: AppPreferences,
) {

    fun getAppInstallationID(): String? = appPreferences.getAppInstallationID()
    fun setAppInstallationID(installationId: String) {
        appPreferences.setAppInstallationID(installationId)
    }

    fun setAccessToken(accessToken: String) {
        appPreferences.setAccessToken(accessToken)
    }

    fun setRefreshToken(refreshToken: String) {
        appPreferences.setRefreshToken(refreshToken)
    }

    fun getAccessToken(): String? = appPreferences.getAccessToken()
    fun setRefreshTokenExpire(refreshTokenExpire: Long) {
        appPreferences.setRefreshTokenExpire(refreshTokenExpire)
    }

    fun getRefreshToken(): String? = appPreferences.getRefreshToken()
    fun setAccessTokenExpire(accessToken: Long) {
        appPreferences.setAccessTokenExpire(accessToken)
    }

    fun getAccessTokenExpire(): Long = appPreferences.getAccessTokenExpire()
    fun getRefreshTokenExpire(): Long = appPreferences.getRefreshTokenExpire()



}


