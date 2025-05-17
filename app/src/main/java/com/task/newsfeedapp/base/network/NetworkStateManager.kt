package com.task.newsfeedapp.base.network

import android.os.Looper
import androidx.lifecycle.MutableLiveData

class NetworkStateManager private constructor() {
    /**
     * Updates the active network status live-data
     */
    fun setNetworkConnectivityStatus(connectivityStatus: Boolean) {
// Logger.i(
// TAG,
// "setNetworkConnectivityStatus() called with: connectivityStatus = [$connectivityStatus]"
// )
        if (Looper.myLooper() == Looper.getMainLooper()) {
            activeNetworkStatusMLD.setValue(connectivityStatus)
        } else {
            activeNetworkStatusMLD.postValue(connectivityStatus)
        }
    }

    /**
     * Returns the current network status
     */
    val networkConnectivityStatus: MutableLiveData<Boolean>
        get() {
            return activeNetworkStatusMLD
        }

    companion object {
        val TAG = NetworkStateManager::class.java.simpleName
        private var INSTANCE: NetworkStateManager? = null
        private val activeNetworkStatusMLD = MutableLiveData<Boolean>()

        @get:Synchronized
        val instance: NetworkStateManager
            get() {
                if (INSTANCE == null) {
                    INSTANCE = NetworkStateManager()
                }
                return INSTANCE as NetworkStateManager
            }
    }

    fun getNetWork(): Boolean {
        if (activeNetworkStatusMLD.value?.equals(true) == true){
            return true
        }
        return false
    }
}