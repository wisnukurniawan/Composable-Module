package com.wisnu.ordertiket

import android.app.Application
import com.wisnu.launcher.main.Launcher

class MainApplication : Application(), Launcher.Provider {
    override lateinit var launcher: Launcher

    override fun onCreate() {
        super.onCreate()
        launcher = MainLauncher(this)
        launcher.onCreate()
    }
}