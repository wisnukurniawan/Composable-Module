package com.wisnu.ordertiket.recyclerview

import android.content.Intent

data class FeatureUiModel(
    val type: Int,
    val title: String,
    val icon: Int,
    val intent: Intent?
)