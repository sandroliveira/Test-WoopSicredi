package br.com.ale.woopsicredi.utils

import android.app.Application

class CustomBaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ActivityApplicationInstance.setup(this)
    }
}