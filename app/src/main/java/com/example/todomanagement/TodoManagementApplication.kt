package com.example.todomanagement

import android.app.Application
import timber.log.Timber

class TodoManagementApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.tag("成功")
        }
    }
}