package com.task.newsfeedapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.newsfeedapp.mvvm.RoomRepository
import com.task.newsfeedapp.mvvm.RoomViewModel


class RoomViewModelFactory(private val movieRepository: RoomRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            return RoomViewModel(movieRepository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}