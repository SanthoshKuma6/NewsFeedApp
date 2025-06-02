package com.task.newsfeedapp.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.task.newsfeedapp.base.component.ActivityComponent
import com.task.newsfeedapp.base.component.DaggerActivityComponent
import com.task.newsfeedapp.base.module.ActivityModule
import javax.inject.Inject


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