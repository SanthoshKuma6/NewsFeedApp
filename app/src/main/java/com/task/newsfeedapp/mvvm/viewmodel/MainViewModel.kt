package com.task.newsfeedapp.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.newsfeedapp.model.fcm.NotificationModel
import javax.inject.Inject


class MainViewModel @Inject constructor() : ViewModel() {
    private val _notification = MutableLiveData<NotificationModel?>()
    val notification: LiveData<NotificationModel?> get() = _notification


    fun setNotification(notification: NotificationModel?) {
        _notification.postValue(notification)
        Log.d("TAG", "setNotification: $notification")
    }
}
