package com.wisnu.launcher.main

object FeatureType {
    const val SETTING = 0
    const val TRANSACTION = 1
    const val ORDER_FLIGHT = 2
    const val ORDER_TRAIN = 3
    const val ORDER_HOTEL = 4
}

object FeatureApplicationClassName {
    const val ORDER_FLIGHT = "com.wisnu.feature.order.flight.OrderFlightFeatureApplication"
    const val ORDER_HOTEL = "com.wisnu.feature.order.hotel.OrderHotelFeatureApplication"
    const val ORDER_TRAIN = "com.wisnu.feature.order.train.OrderTrainFeatureApplication"
    const val TRANSACTION = "com.wisnu.feature.transaction.TransactionFeatureApplication"
    const val SETTING = "com.wisnu.feature.setting.SettingFeatureApplication"
}