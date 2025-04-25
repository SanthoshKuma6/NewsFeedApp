package com.task.newsfeedapp.base

import androidx.activity.ComponentActivity
import javax.inject.Inject
import android.os.Bundle
import dagger.hilt.android.components.ActivityComponent

abstract class ComposeBaseActivity<VIEWMODEL:BaseViewModel>:ComponentActivity() {

    @Inject
    lateinit var viewModel: VIEWMODEL
    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
        setupUI()
        setupObservers()
    }


    open fun setupUI() {
        // Common UI setup (if needed)
    }

    open fun setupObservers() {

    }

    private fun buildActivityComponent() = DaggerActivityComponent.builder()
        .applicationComponent((application as BaseApplication).applicationComponent)
        .activityModule(ActivityModule(this)).build()

    protected abstract fun injectDependencies(activityComponent: ActivityComponent)
}