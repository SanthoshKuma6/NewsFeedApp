package com.task.newsfeedapp.base.model

data class ViewModelResponse(
    val type: LoginResponseType ,
    val data: String? = null,
    val msg: String? = null
)

enum class LoginResponseType {
    LOGIN_SUCCESS,
    ERROR
}
object ResponseType {
    const val THROWABLE = "THROWABLE"
    const val LOGIN_SUCCESS = "success"
}