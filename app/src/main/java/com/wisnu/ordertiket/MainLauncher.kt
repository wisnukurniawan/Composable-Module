package com.wisnu.ordertiket

import android.app.Application
import com.wisnu.feature.order.flight.OrderFlightFeatureApplication
import com.wisnu.feature.order.hotel.OrderHotelFeatureApplication
import com.wisnu.feature.order.train.OrderTrainFeatureApplication
import com.wisnu.feature.setting.SettingFeatureApplication
import com.wisnu.feature.transaction.TransactionFeatureApplication
import com.wisnu.launcher.main.BaseLauncher

class MainLauncher(application: Application) : BaseLauncher(application) {
    init {
        registerFeatureApplication(OrderFlightFeatureApplication())
        registerFeatureApplication(OrderHotelFeatureApplication())
        registerFeatureApplication(OrderTrainFeatureApplication())
        registerFeatureApplication(TransactionFeatureApplication())
        registerFeatureApplication(SettingFeatureApplication())
    }
}