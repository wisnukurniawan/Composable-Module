package com.wisnu.feature.order.hotel

import com.wisnu.launcher.main.Application
import com.wisnu.launcher.main.Launcher

class OrderHotelApplication : Application {
    override fun onCreate(launcher: Launcher) {
        launcher.registerFeature(OrderHotelFeature(launcher.application))
    }
}