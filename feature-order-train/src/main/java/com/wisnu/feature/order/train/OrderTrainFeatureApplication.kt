package com.wisnu.feature.order.train

import com.wisnu.launcher.main.FeatureApplication
import com.wisnu.launcher.main.Launcher

class OrderTrainFeatureApplication : FeatureApplication {
    override fun onCreate(launcher: Launcher) {
        launcher.registerFeature(OrderTrainFeature(launcher.application))
    }
}