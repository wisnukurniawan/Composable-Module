package com.wisnu.feature.order.flight

import android.app.Application
import android.content.Intent
import com.wisnu.launcher.main.FeatureLaunchable
import com.wisnu.launcher.main.FeatureType

class OrderFlightFeature(private val application: Application) : FeatureLaunchable {
    override val type: Int = FeatureType.ORDER_FLIGHT

    override fun title(): Int = R.string.feature_order_flight_name

    override fun intent(): Intent? = Intent(application, OrderFlightActivity::class.java)

    override fun icon(): Int = R.drawable.ic_flight
}