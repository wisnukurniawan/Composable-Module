package com.wisnu.launcher.main

object FeatureType {
    const val SETTING = 0
    const val TRANSACTION = 1
    const val ORDER_FLIGHT = 2
    const val ORDER_TRAIN = 3
    const val ORDER_HOTEL = 4
}

object ApplicationClassName {
    const val ORDER_FLIGHT = "com.wisnu.feature.order.flight.OrderFlightApplication"
    const val ORDER_HOTEL = "com.wisnu.feature.order.hotel.OrderHotelApplication"
    const val ORDER_TRAIN = "com.wisnu.feature.order.train.OrderTrainApplication"
    const val ORDER_BUS = "com.wisnu.feature.orderbus.OrderBusApplication"
    const val TRANSACTION = "com.wisnu.feature.transaction.TransactionApplication"
    const val SETTING = "com.wisnu.feature.setting.SettingApplication"
}