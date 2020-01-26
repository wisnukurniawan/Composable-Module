package com.wisnu.launcher.main

import android.app.Application as AndroidApplication

abstract class BaseLauncher(final override val application: AndroidApplication) : Launcher {
    private val _applications: MutableList<Application> = mutableListOf()
    private val _features: MutableList<FeatureLaunchable> = mutableListOf()

    override val applications: List<Application>
        get() = _applications
    override val features: List<FeatureLaunchable>
        get() = _features

    override fun registerApplication(application: Application?) {
        if (application != null && isNotAdded(_applications, application)) {
            _applications.add(application)
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
        applications.forEach { it.onCreate(this) }
    }
}
