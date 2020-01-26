package com.wisnu.launcher.main

import android.app.Application as AndroidApplication

interface Launcher : ApplicationRegistry, FeatureRegistry {
    val application: AndroidApplication

    interface Provider {
        val launcher: Launcher
    }
}

interface ApplicationRegistry {
    val applications: List<Application>
    fun registerApplication(application: Application?)
    fun onCreate()
}

interface FeatureRegistry {
    val features: List<FeatureLaunchable>
    fun registerFeature(featureLaunchable: FeatureLaunchable?)
}