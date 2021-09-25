package com.alexandr7035.swipecat

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.alexandr7035.swipecat.cats.CatsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.navigationBarColor = Color.TRANSPARENT
        window.statusBarColor = Color.TRANSPARENT
        window.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.background_primary))

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentsHost, CatsFragment.newInstance())
            .commit()
    }
}