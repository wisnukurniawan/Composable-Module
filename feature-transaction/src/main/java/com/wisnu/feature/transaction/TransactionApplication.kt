package com.wisnu.feature.transaction

import com.wisnu.launcher.main.Application
import com.wisnu.launcher.main.Launcher

class TransactionApplication : Application {
    override fun onCreate(launcher: Launcher) {
        launcher.registerFeature(TransactionFeature(launcher.application))
    }
}