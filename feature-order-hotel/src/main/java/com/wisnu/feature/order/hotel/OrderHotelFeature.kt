package com.wisnu.feature.order.hotel

import android.app.Application
import android.content.Intent
import com.wisnu.launcher.main.FeatureLaunchable
import com.wisnu.launcher.main.FeatureType

class OrderHotelFeature(private val application: Application) : FeatureLaunchable {
    override val type: Int = FeatureType.ORDER_HOTEL

    override fun title(): Int = R.string.feature_order_hotel_name

    override fun intent(): Intent? = Intent(application, OrderHotelActivity::class.java)

    override fun icon(): Int = R.drawable.ic_hotel
}