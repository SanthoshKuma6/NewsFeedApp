package com.task.newsfeedapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.newsfeedapp.base.model.Resource
import com.task.newsfeedapp.base.model.ViewModelResponse
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(
    protected val schedulerProvider: SchedulerProvider,
    protected val compositeDisposable: CompositeDisposable,
    protected val networkHelper: NetworkHelper,
    val commonRepository: CommonRepository,
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
    abstract fun onCreate()
}