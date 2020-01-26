package com.wisnu.feature.setting

import com.wisnu.launcher.main.Application
import com.wisnu.launcher.main.Launcher

class SettingApplication : Application {
    override fun onCreate(launcher: Launcher) {
        launcher.registerFeature(SettingFeature(launcher.application))
    }
}