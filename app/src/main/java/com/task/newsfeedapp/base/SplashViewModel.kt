package com.task.newsfeedapp.base

class SplashViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    var splashRepository: SplashRepository,
    commonRepository: CommonRepository,

    ) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper, commonRepository) {
    override fun onCreate() {
        // method is empty
    }
}