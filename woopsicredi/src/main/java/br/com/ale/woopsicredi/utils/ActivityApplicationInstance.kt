package br.com.ale.woopsicredi.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.widget.Toast
import androidx.annotation.WorkerThread

object ActivityApplicationInstance {

    private var applicationContext: Application? = null
    @SuppressLint("StaticFieldLeak")
    private lateinit var applicationActivityLifecycleCallbacks: ApplicationActivityLifecycleCallbacks

    fun setup (application: Application?) {
        if (applicationContext == null) {
            applicationContext = application
            applicationActivityLifecycleCallbacks = ApplicationActivityLifecycleCallbacks()
            applicationContext?.registerActivityLifecycleCallbacks(applicationActivityLifecycleCallbacks)
        }

    }

    fun getCurrentActivity(): Activity? {
        return applicationActivityLifecycleCallbacks.currentActivity
    }

    fun getApplicationContext(): Application? {
        return applicationContext
    }

    @WorkerThread
    fun setToast(msg: String) {
        getCurrentActivity()?.runOnUiThread {
            Toast.makeText(getCurrentActivity(), msg, Toast.LENGTH_LONG).show()
        }
    }
}