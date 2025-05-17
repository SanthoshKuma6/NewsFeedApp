package com.task.newsfeedapp.base.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

class NetworkMonitoringUtil(val context: Context) : ConnectivityManager.NetworkCallback() {
    private val mNetworkRequest: NetworkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        .build()
    private val mConnectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val mNetworkStateManager: NetworkStateManager = NetworkStateManager.instance

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        val networkCapabilities = mConnectivityManager.getNetworkCapabilities(network)
        val hasInternetCapability = networkCapabilities?.hasCapability(NET_CAPABILITY_INTERNET)

        if (hasInternetCapability == true) {
            // Check if this network actually has internet
            CoroutineScope(Dispatchers.IO).launch {
                val hasInternet = DoesNetworkHaveInternet.execute(network.socketFactory)
                if (hasInternet) {
                    withContext(Dispatchers.Main) {
                        mNetworkStateManager.setNetworkConnectivityStatus(true)
                    }
                }else{
                    mNetworkStateManager.setNetworkConnectivityStatus(false)
                }
            }
        }
        // mNetworkStateManager.setNetworkConnectivityStatus(true)
    }

    override fun onLost( network: Network) {
        super.onLost(network)
        mNetworkStateManager.setNetworkConnectivityStatus(false)
    }

    /**
     * Registers the Network-Request callback
     * (Note: Register only once to prevent duplicate callbacks)
     */
    fun registerNetworkCallbackEvents() {
        mConnectivityManager.registerNetworkCallback(mNetworkRequest, this)
    }

    /**
     * Check current Network state
     */
    fun checkNetworkState() {
        try {
            val networkInfo = mConnectivityManager.activeNetwork
            mNetworkStateManager.setNetworkConnectivityStatus(
                networkInfo != null
            )

        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }


    companion object {
        val TAG: String = NetworkMonitoringUtil::class.java.simpleName
    }

    object DoesNetworkHaveInternet {
        fun execute(socketFactory: SocketFactory): Boolean {
            // Make sure to execute this on a background thread.
            return try {
                val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
                socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
                socket.close()
                true
            } catch (e: IOException) {
                false
            } catch (e:Exception){
                false
            }

        }
    }
}
object Constants {
    const val LOGGED_USER_PREFERENCES="pref_hns"

}