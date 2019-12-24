package com.interventure.hackathon.numbers.scratch

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.interventure.hackathon.numbers.R

class ScratchActivity: AppCompatActivity() {

    private var scratchView: ScratchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scratch)
        scratchView = findViewById<ScratchView?>(R.id.scratchView)
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        scratchView?.init(metrics)
    }
}