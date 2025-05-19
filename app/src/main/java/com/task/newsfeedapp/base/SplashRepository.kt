package com.task.newsfeedapp.base

import com.task.newsfeedapp.base.network.INetworkService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashRepository
@Inject
constructor(
    private val iNetworkService: INetworkService,
    private val appPreferences: AppPreferences
) {

}