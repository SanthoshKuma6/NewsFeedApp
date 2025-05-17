package com.task.newsfeedapp.base

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.task.newsfeedapp.base.component.ApplicationComponent

open class BaseApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
        appContext = applicationContext
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    companion object {
        lateinit var appContext: Context
            private set
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

}