package com.wisnu.feature.order.train

import com.wisnu.launcher.main.Application
import com.wisnu.launcher.main.Launcher

class OrderTrainApplication : Application {
    override fun onCreate(launcher: Launcher) {
        launcher.registerFeature(OrderTrainFeature(launcher.application))
    }
}