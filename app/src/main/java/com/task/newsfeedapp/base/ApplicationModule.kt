package com.task.newsfeedapp.base

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides
import com.task.newsfeedapp.base.di.ApplicationContext
import com.task.newsfeedapp.base.network.Constants.LOGGED_USER_PREFERENCES
import com.task.newsfeedapp.base.network.INetworkService
import com.task.newsfeedapp.base.network.Networking
import com.task.newsfeedapp.base.rx.RxSchedulerProvider
import com.task.newsfeedapp.utils.Utils
import io.reactivex.disposables.CompositeDisposable
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
    fun provideSchedulerProvider(): RxSchedulerProvider = RxSchedulerProvider()

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