package com.wisnu.feature.orderbus

import com.wisnu.launcher.main.FeatureApplication
import com.wisnu.launcher.main.Launcher

class OrderBusFeatureApplication : FeatureApplication {
    override fun onCreate(launcher: Launcher) {
        launcher.registerFeature(OrderBusFeature(launcher.application))
    }
}