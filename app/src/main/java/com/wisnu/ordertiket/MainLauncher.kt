package com.wisnu.ordertiket

import android.app.Application
import com.wisnu.launcher.annotations.RegisterFeature
import com.wisnu.launcher.main.BaseLauncher
import com.wisnu.launcher.main.ApplicationClassName

@RegisterFeature(
    ApplicationClassName.ORDER_FLIGHT,
    ApplicationClassName.ORDER_HOTEL,
    ApplicationClassName.ORDER_TRAIN,
    ApplicationClassName.ORDER_BUS,
    ApplicationClassName.TRANSACTION,
    ApplicationClassName.SETTING
)
class MainLauncher(application: Application) : BaseLauncher(application) {
    init {
        MainLauncherUtils.buildFeatures()
            .forEach { registerApplication(it) }
    }
}