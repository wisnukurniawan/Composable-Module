package com.wisnu.ordertiket

import android.app.Application
import com.wisnu.launcher.annotations.RegisterFeature
import com.wisnu.launcher.main.BaseLauncher
import com.wisnu.launcher.main.FeatureApplicationClassName

@RegisterFeature(
    FeatureApplicationClassName.ORDER_FLIGHT,
    FeatureApplicationClassName.ORDER_HOTEL,
    FeatureApplicationClassName.ORDER_TRAIN,
    FeatureApplicationClassName.TRANSACTION,
    FeatureApplicationClassName.SETTING
)
class MainLauncher(application: Application) : BaseLauncher(application) {
    init {
        MainLauncherUtils.buildFeatures()
            .forEach { registerFeatureApplication(it) }
    }
}