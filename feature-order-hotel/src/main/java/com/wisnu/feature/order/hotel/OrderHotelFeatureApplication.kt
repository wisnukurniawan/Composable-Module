package com.wisnu.feature.order.hotel

import com.wisnu.launcher.main.FeatureApplication
import com.wisnu.launcher.main.Launcher

class OrderHotelFeatureApplication : FeatureApplication {
    override fun onCreate(launcher: Launcher) {
        launcher.registerFeature(OrderHotelFeature(launcher.application))
    }
}