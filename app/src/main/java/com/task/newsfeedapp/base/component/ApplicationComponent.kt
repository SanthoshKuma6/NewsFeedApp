package com.task.newsfeedapp.base.component

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.task.newsfeedapp.base.ApplicationModule
import com.task.newsfeedapp.base.BaseApplication
import com.task.newsfeedapp.base.CommonRepository
import com.task.newsfeedapp.base.NetworkHelper
import com.task.newsfeedapp.base.SchedulerProvider
import com.task.newsfeedapp.base.SplashRepository
import com.task.newsfeedapp.base.di.ApplicationContext
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

    /**
     * These methods are written in ApplicationComponent because the instance of
     * NetworkService is singleton and is maintained in the ApplicationComponent's implementation by Dagger
     * For NetworkService singleton instance to be accessible to say DummyActivity's DummyViewModel
     * this ApplicationComponent must expose a method that returns NetworkService instance
     * This method will be called when NetworkService is injected in DummyViewModel.
     * Also, in ActivityComponent you can find dependencies = [ApplicationComponent::class] to link this relationship
     */


    fun getSharedPreferences(): SharedPreferences

    fun getNetworkHelper(): NetworkHelper

    /**---------------------------------------------------------------------------
     * Dagger will internally create UserRepository instance using constructor injection.
     * Dependency through constructor
     * UserRepository ->
     * [NetworkService -> Nothing is required],
     * [DatabaseService -> Nothing is required],
     * [UserPreferences -> [SharedPreferences -> provided by the function provideSharedPreferences in ApplicationModule class]]
     * So, Dagger will be able to create an instance of UserRepository by its own using constructor injection
     *---------------------------------------------------------------------------------
     */

    fun getCommonRepository(): CommonRepository

    fun getSchedulerProvider(): SchedulerProvider

    fun getCompositeDisposable(): CompositeDisposable

    fun getLoginRepository(): SplashRepository



}