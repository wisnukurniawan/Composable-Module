package com.wisnu.feature.setting

import android.app.Application
import android.content.Intent
import com.wisnu.launcher.main.FeatureLaunchable
import com.wisnu.launcher.main.FeatureType

class SettingFeature(private val application: Application) : FeatureLaunchable {
    override val type: Int = FeatureType.SETTING

    override fun title(): Int = R.string.feature_setting_name

    override fun intent(): Intent? = Intent(application, SettingActivity::class.java)

    override fun icon(): Int = R.drawable.ic_settings
}