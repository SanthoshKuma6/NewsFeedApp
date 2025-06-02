package com.task.newsfeedapp.base

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.task.newsfeedapp.base.model.Resource
import com.task.newsfeedapp.base.model.ViewModelResponse
import com.task.newsfeedapp.base.rx.SchedulerProvider
import com.task.newsfeedapp.utils.state.AuthState
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
        _authState.value= AuthState.Loading
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
        _authState.value= AuthState.Loading
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
        _authState.value= AuthState.UnAuthenticated
    }

}