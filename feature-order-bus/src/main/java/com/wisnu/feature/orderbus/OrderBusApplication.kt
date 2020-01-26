package com.wisnu.feature.orderbus

import com.wisnu.launcher.main.Application
import com.wisnu.launcher.main.Launcher

class OrderBusApplication : Application {
    override fun onCreate(launcher: Launcher) {
        launcher.registerFeature(OrderBusFeature(launcher.application))
    }
}