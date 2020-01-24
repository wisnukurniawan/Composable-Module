package com.wisnu.launcher.main

import android.app.Application

interface Launcher : FeatureApplicationRegistry, FeatureRegistry {
    val application: Application

    interface Provider {
        val launcher: Launcher
    }
}

interface FeatureApplicationRegistry {
    val featureApplications: List<FeatureApplication>
    fun registerFeatureApplication(featureApplication: FeatureApplication?)
    fun onCreate()
}

interface FeatureRegistry {
    val features: List<FeatureLaunchable>
    fun registerFeature(featureLaunchable: FeatureLaunchable?)
}