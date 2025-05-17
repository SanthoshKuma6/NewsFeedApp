package com.task.newsfeedapp.base

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