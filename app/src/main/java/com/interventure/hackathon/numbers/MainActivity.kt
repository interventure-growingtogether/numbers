package com.interventure.hackathon.numbers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.interventure.hackathon.numbers.pictures.PicturesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, NumbersGridFragment.newInstance()).commitAllowingStateLoss()
    }
}
