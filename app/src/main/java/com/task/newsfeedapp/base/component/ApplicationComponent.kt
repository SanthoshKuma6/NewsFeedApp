package com.task.newsfeedapp.base.component

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.task.newsfeedapp.base.ApplicationModule
import com.task.newsfeedapp.base.BaseApplication
import com.task.newsfeedapp.base.CommonRepository
import com.task.newsfeedapp.base.NetworkHelper
import com.task.newsfeedapp.base.SplashRepository
import com.task.newsfeedapp.base.di.ApplicationContext
import com.task.newsfeedapp.base.rx.SchedulerProvider
import com.task.newsfeedapp.mvvm.repository.ChatRepository
import com.task.newsfeedapp.mvvm.repository.ProfileRepository
import com.task.newsfeedapp.screens.agora.AgoraChatManager
import com.task.newsfeedapp.screens.agora.AgoraRTCManager
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: BaseApplication)

    fun getApplication(): Application
    @ApplicationContext
    fun getContext(): Context
    fun getSharedPreferences(): SharedPreferences

    fun getNetworkHelper(): NetworkHelper

    fun getCommonRepository(): CommonRepository

    fun getSchedulerProvider(): SchedulerProvider

    fun getCompositeDisposable(): CompositeDisposable

    fun getLoginRepository(): SplashRepository

    fun getAgoraChatManager(): AgoraChatManager
    
    fun getAgoraRTCManager(): AgoraRTCManager

    fun getChatRepository(): ChatRepository

    fun getProfileRepository(): ProfileRepository
}