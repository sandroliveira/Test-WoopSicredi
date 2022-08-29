package br.com.ale.woopsicredi.utils

import android.content.Context
import android.net.ConnectivityManager
import br.com.ale.woopsicredi.data.Event

class Utils {

    fun checkNetwork(): Boolean {
        val connectivityManager = ActivityApplicationInstance
            .getApplicationContext()?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }

    fun createTextDescription(event: Event): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(event.title)
        stringBuilder.appendLine()
        stringBuilder.appendLine()
        stringBuilder.append(event.description)
        stringBuilder.appendLine()
        stringBuilder.append(event.date)
        stringBuilder.appendLine()
        stringBuilder.append(event.price)
        stringBuilder.appendLine()
        stringBuilder.append(event.longitude)
        stringBuilder.appendLine()
        stringBuilder.append(event.latitude)
        return stringBuilder.toString()
    }
}