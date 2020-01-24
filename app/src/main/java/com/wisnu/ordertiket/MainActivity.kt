package com.wisnu.ordertiket

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.wisnu.launcher.main.Launcher
import com.wisnu.ordertiket.recyclerview.FeatureAdapter
import com.wisnu.ordertiket.recyclerview.FeatureUiModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val features = (applicationContext as? Launcher.Provider)?.launcher?.features
        val featureUiModels = features?.map { FeatureUiModel(it.type, getString(it.title()), it.icon(), it.intent()) } ?: listOf()
        val adapter = FeatureAdapter(featureUiModels) {
            startActivity(it)
        }
        val layoutManager = GridLayoutManager(this, 3)
        rv_feature.layoutManager = layoutManager
        rv_feature.adapter = adapter
        rv_feature.setHasFixedSize(true)
    }
}
