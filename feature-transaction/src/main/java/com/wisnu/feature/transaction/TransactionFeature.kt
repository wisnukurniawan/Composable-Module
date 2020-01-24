package com.wisnu.feature.transaction

import android.app.Application
import android.content.Intent
import com.wisnu.launcher.main.FeatureLaunchable
import com.wisnu.launcher.main.FeatureType

class TransactionFeature(private val application: Application) : FeatureLaunchable {
    override val type: Int = FeatureType.TRANSACTION

    override fun title(): Int = R.string.feature_transaction_name

    override fun intent(): Intent? = Intent(application, TransactionActivity::class.java)

    override fun icon(): Int = R.drawable.ic_transaction
}