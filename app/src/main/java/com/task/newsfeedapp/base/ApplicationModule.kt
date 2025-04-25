package com.task.newsfeedapp.base

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides
import com.task.newsfeedapp.utils.Utils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: BaseApplication) {
    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Context = application

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences =
        application.getSharedPreferences(LOGGED_USER_PREFERENCES, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(application)

    @Provides
    @Singleton
    fun provideTSMService(): INetworkService =
        Networking.create(
            Utils.BASE_URL,
            application.cacheDir,
            10 * 1024 * 1024 // 10MB
        )
}