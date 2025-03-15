package com.task.newsfeedapp.resource

sealed class RoomResource<T> {
    data class Success<T>(val data: T) : RoomResource<T>()
    data class Error<T>(val message: String) : RoomResource<T>()
    class Loading<T> : RoomResource<T>()
}