package com.wisnu.ordertiket.recyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wisnu.ordertiket.R

class FeatureAdapter(
    private val items: List<FeatureUiModel>,
    private val onClick: (Intent) -> Unit
) : RecyclerView.Adapter<FeatureViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_feature_item, parent, false)
        return FeatureViewHolder(itemView, onClick)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        holder.bindData(items[position])
    }
}