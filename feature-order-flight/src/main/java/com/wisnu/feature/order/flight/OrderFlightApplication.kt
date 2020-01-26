package com.wisnu.feature.order.flight

import com.wisnu.launcher.main.Application
import com.wisnu.launcher.main.Launcher

class OrderFlightApplication : Application {
    override fun onCreate(launcher: Launcher) {
        launcher.registerFeature(OrderFlightFeature(launcher.application))
    }
}