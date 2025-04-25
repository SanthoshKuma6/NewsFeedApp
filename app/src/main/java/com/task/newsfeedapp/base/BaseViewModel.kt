package com.task.newsfeedapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    commonRepository: CommonRepository
) : ViewModel() {
    val response: MutableLiveData<ViewModelResponse> = MutableLiveData()
    val messageStringId: MutableLiveData<Resource<Int>> = MutableLiveData()
    val messageString: MutableLiveData<Resource<String>> = MutableLiveData()
    val showNetworkDialog: MutableLiveData<Boolean> = MutableLiveData()
    internal val showProgress: MutableLiveData<Boolean> = MutableLiveData()
    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}