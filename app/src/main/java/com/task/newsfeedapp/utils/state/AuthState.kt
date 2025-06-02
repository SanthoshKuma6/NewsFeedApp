package com.task.newsfeedapp.utils.state

sealed class AuthState {
    object Authenticate : AuthState()
    object UnAuthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}

