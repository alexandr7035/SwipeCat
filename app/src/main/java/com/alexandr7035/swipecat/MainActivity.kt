package com.alexandr7035.swipecat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexandr7035.swipecat.cats.CatsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentsHost, CatsFragment.newInstance())
            .commit()
    }
}