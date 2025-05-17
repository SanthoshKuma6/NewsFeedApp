package com.task.newsfeedapp.base.module

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.newsfeedapp.base.CommonRepository
import com.task.newsfeedapp.base.ComposeBaseActivity
import com.task.newsfeedapp.base.NetworkHelper
import com.task.newsfeedapp.base.SchedulerProvider
import com.task.newsfeedapp.base.SplashRepository
import com.task.newsfeedapp.base.SplashViewModel
import com.task.newsfeedapp.base.dialodge.LoadingDialog
import com.task.newsfeedapp.base.factory.ViewModelProviderFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable


@Module
class ActivityModule(private val activity: ComposeBaseActivity<*>) {

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(activity)

    @Provides
    fun provideLoadingDialog() = LoadingDialog(activity)


    @Provides
    fun domSplashViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        splashRepository: SplashRepository,
        commonRepository: CommonRepository
    ): SplashViewModel = ViewModelProvider(
        activity, ViewModelProviderFactory(SplashViewModel::class) {
            SplashViewModel(
                schedulerProvider,
                compositeDisposable,
                networkHelper,
                splashRepository, commonRepository
            )
        })[SplashViewModel::class.java]


}