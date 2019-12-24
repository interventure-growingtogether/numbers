package com.interventure.hackathon.numbers.scratch

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.gridlayout.widget.GridLayout
import com.interventure.hackathon.numbers.R

class ScratchActivity : AppCompatActivity() {

    private var scratchView: ScratchView? = null
    private var gridLayout: GridLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scratch)
        scratchView = findViewById<ScratchView?>(R.id.scratchView)
        gridLayout = findViewById<GridLayout?>(R.id.present_grid)
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        scratchView?.init(metrics)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        setupGrid(9, width)
    }

    private fun setupGrid(number: Int, width: Int) {
        val imageWidth: Int = when (number) {
            1 -> {
                gridLayout?.columnCount = 1
                width / 2
            }
            2, 3, 4 -> {
                gridLayout?.columnCount = 2
                width / 2
            }
            5, 6, 7, 8, 9 -> {
                gridLayout?.columnCount = 3
                width / 3
            }
            else -> width
        }
        for (i in 0 until number) {
            gridLayout?.addView(getImageView(imageWidth))
        }
    }

    private fun getImageView(width: Int): View {
        val imageView = ImageView(this)
        imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_present))
        val params = LinearLayout.LayoutParams(width, width)
        params.gravity = Gravity.CENTER
        imageView.layoutParams = params
        return imageView
    }
}