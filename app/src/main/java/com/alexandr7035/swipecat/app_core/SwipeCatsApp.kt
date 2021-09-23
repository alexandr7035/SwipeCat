package com.alexandr7035.swipecat.app_core

import android.app.Application
import com.alexandr7035.swipecat.BuildConfig
import timber.log.Timber


class SwipeCatsApp: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}