package com.example.nativo

import android.app.Application
import android.os.SystemClock
import android.util.Log

class NativoApplication : Application() {

    companion object {
        var appStartTime: Long = 0
    }

    override fun onCreate() {
        super.onCreate()
        appStartTime = SystemClock.uptimeMillis()
        Log.d("AppStartup", "NativoApplication.onCreate() - Start Time: $appStartTime ms")
    }
}
