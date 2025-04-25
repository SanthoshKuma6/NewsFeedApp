package com.task.newsfeedapp.mvvm.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableLiveData<AuthState>()
    val authState: MutableLiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.UnAuthenticated
        } else {
            _authState.value = AuthState.Authenticate
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()){
          _authState.value=  AuthState.Error("email and password is cant empty")
            return
        }
        _authState.value=AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _authState.value = AuthState.Authenticate
                } else {
                    _authState.value =
                        AuthState.Error(it.exception?.message ?: "Something went wrong")
                }
            }


    }

    @SuppressLint("SuspiciousIndentation")
    fun signUp(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()){
          _authState.value=  AuthState.Error("email and password is cant empty")
            return
        }
        _authState.value=AuthState.Loading
        auth.createUserWithEmailAndPassword( email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _authState.value = AuthState.Authenticate
                } else {
                    _authState.value =
                        AuthState.Error(it.exception?.message ?: "Something went wrong")
                }
            }


    }

    fun logout(){
        auth.signOut()
        _authState.value=AuthState.UnAuthenticated
    }
}

sealed class AuthState {
    object Authenticate : AuthState()
    object UnAuthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}