package com.alexandr7035.swipecat.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.alexandr7035.swipecat.R
import com.alexandr7035.swipecat.core.Navigation
import com.alexandr7035.swipecat.ui.cat_swiper.CatsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navigation: Navigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.navigationBarColor = Color.TRANSPARENT
        window.statusBarColor = Color.TRANSPARENT
        window.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.background_primary))

        navigation = Navigation(
            fragmentManager = supportFragmentManager,
            startDestination = CatsFragment(),
            hostFragmentId = R.id.fragmentsHost
        )

        navigation.navigateStart()
    }

    fun getNavigation(): Navigation {
        return navigation
    }

    override fun onBackPressed() {
        if (navigation.backStackNotEmpty()) {
            navigation.navigateBack()
        }
        else {
            super.onBackPressed()
        }
    }



}