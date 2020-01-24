package com.wisnu.feature.transaction

import com.wisnu.launcher.main.FeatureApplication
import com.wisnu.launcher.main.Launcher

class TransactionFeatureApplication : FeatureApplication {
    override fun onCreate(launcher: Launcher) {
        launcher.registerFeature(TransactionFeature(launcher.application))
    }
}