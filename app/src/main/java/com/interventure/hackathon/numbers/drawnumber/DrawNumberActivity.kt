package com.interventure.hackathon.numbers.drawnumber

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.interventure.hackathon.numbers.R

class DrawNumberActivity : AppCompatActivity() {

    private var drawNumberView: DrawNumberView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_number)
        drawNumberView = findViewById<DrawNumberView?>(R.id.draw_number_view)
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val num = intent?.getIntExtra(NUMBER, -1) ?: -1
        val color = intent?.getIntExtra(COLOR, -1) ?: -1
        drawNumberView?.init(metrics, num, color)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//            R.id.normal -> {
//                paintView?.normal()
//                return true
//            }
//            R.id.emboss -> {
//                paintView?.emboss()
//                return true
//            }
//            R.id.blur -> {
//                paintView?.blur()
//                return true
//            }
            R.id.clear -> {
                drawNumberView?.clear()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val NUMBER = "number"
        const val COLOR = "color"
    }
}
