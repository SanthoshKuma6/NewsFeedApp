package com.task.newsfeedapp.base

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresExtension
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.task.newsfeedapp.base.utils.NetworkError
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import javax.inject.Singleton


@Singleton
class NetworkHelper(private val context: Context) {
    companion object {
        private const val TAG = "NetworkHelper"
    }

    @SuppressLint("ServiceCast")
    fun isNetworkConnected(): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val capabilities = manager?.getNetworkCapabilities(manager.activeNetwork) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun castToNetworkError(throwable: Throwable): NetworkError {
        val defaultNetworkError = NetworkError()
        try {
            if (throwable is ConnectException) return NetworkError(0, "0")
            if (throwable !is HttpException) return defaultNetworkError
            val error = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .fromJson(throwable.response()?.errorBody()?.string(), NetworkError::class.java)
            return NetworkError(throwable.code(), error.statusCode, error.message)
        } catch (e: IOException) {
            // Logger.e(TAG, e.toString())
        } catch (e: JsonSyntaxException) {
            //Logger.e(TAG, e.toString())
        } catch (e: NullPointerException) {
            //Logger.e(TAG, e.toString())
        }
        return defaultNetworkError
    }
}