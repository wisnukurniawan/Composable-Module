package com.wisnu.feature.orderbus

import android.app.Application
import android.content.Intent
import com.wisnu.launcher.main.FeatureLaunchable
import com.wisnu.launcher.main.FeatureType

class OrderBusFeature(private val application: Application) : FeatureLaunchable {
    override val type: Int = FeatureType.ORDER_FLIGHT

    override fun title(): Int = R.string.feature_order_bus_name

    override fun intent(): Intent? = Intent(application, OrderBusActivity::class.java)

    override fun icon(): Int = R.drawable.ic_bus
}