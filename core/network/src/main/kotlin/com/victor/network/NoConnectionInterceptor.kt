package com.victor.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NoConnectionInterceptor @Inject constructor(
    private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnectionAvailable()) {
            throw NoConnectionException()
        }
        return chain.proceed(chain.request())
    }

    private fun isConnectionAvailable(): Boolean {
        val manager =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        val activeNetwork = manager.activeNetwork

        return manager.getNetworkCapabilities(activeNetwork)?.let { networkCapabilities ->
            return@let networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }
}