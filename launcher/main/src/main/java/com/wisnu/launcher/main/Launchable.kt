package com.wisnu.launcher.main

import android.content.Intent

interface Launchable {
    val type: Int
    fun title(): Int
}

interface FeatureLaunchable : Launchable {
    fun intent(): Intent?
    fun icon(): Int
}