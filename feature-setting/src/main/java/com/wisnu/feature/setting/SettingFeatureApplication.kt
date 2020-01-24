package com.wisnu.feature.setting

import com.wisnu.launcher.main.FeatureApplication
import com.wisnu.launcher.main.Launcher

class SettingFeatureApplication : FeatureApplication {
    override fun onCreate(launcher: Launcher) {
        launcher.registerFeature(SettingFeature(launcher.application))
    }
}