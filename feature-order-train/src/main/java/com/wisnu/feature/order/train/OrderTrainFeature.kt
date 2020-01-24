package com.wisnu.feature.order.train

import android.app.Application
import android.content.Intent
import com.wisnu.launcher.main.FeatureLaunchable
import com.wisnu.launcher.main.FeatureType

class OrderTrainFeature(private val application: Application) : FeatureLaunchable {
    override val type: Int = FeatureType.ORDER_TRAIN

    override fun title(): Int = R.string.feature_order_train_name

    override fun intent(): Intent? = Intent(application, OrderTrainActivity::class.java)

    override fun icon(): Int = R.drawable.ic_train
}