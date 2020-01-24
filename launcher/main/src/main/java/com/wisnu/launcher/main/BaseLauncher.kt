package com.wisnu.launcher.main

import android.app.Application

abstract class BaseLauncher(final override val application: Application) : Launcher {
    private val _featureApplications: MutableList<FeatureApplication> = mutableListOf()
    private val _features: MutableList<FeatureLaunchable> = mutableListOf()

    override val featureApplications: List<FeatureApplication>
        get() = _featureApplications
    override val features: List<FeatureLaunchable>
        get() = _features

    override fun registerFeatureApplication(featureApplication: FeatureApplication?) {
        if (featureApplication != null && isNotAdded(_featureApplications, featureApplication)) {
            _featureApplications.add(featureApplication)
        }
    }

    override fun registerFeature(featureLaunchable: FeatureLaunchable?) {
        if (featureLaunchable != null && isNotAdded(_features, featureLaunchable)) {
            _features.add(featureLaunchable)
        }
    }

    private fun <T> isNotAdded(list: List<T>, element: T): Boolean {
        return !list.contains(element)
    }

    override fun onCreate() {
        featureApplications.forEach { it.onCreate(this) }
    }
}
