package com.wisnu.ordertiket.recyclerview

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_feature_item.view.*

class FeatureViewHolder(
    itemView: View,
    private val onClick: (Intent) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    fun bindData(data: FeatureUiModel) {
        itemView.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION && data.intent != null) {
                onClick(data.intent)
            }
        }
        itemView.tv_title.text = data.title
        itemView.iv_feature.setImageResource(data.icon)
    }
}