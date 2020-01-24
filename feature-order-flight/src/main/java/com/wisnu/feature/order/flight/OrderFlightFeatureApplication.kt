package com.wisnu.feature.order.flight

import com.wisnu.launcher.main.FeatureApplication
import com.wisnu.launcher.main.Launcher

class OrderFlightFeatureApplication : FeatureApplication {
    override fun onCreate(launcher: Launcher) {
        launcher.registerFeature(OrderFlightFeature(launcher.application))
    }
}