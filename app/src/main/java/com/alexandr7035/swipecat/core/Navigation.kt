package com.alexandr7035.swipecat.core

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class Navigation(private val fragmentManager: FragmentManager, private val startDestination: Fragment, private val hostFragmentId: Int) {

    // Start destination here
    fun navigateStart() {
        replace(startDestination)
    }

    fun add(fragment: Fragment, allowGoBack : Boolean = false) {
        if (allowGoBack) {
            fragmentManager.beginTransaction()
                .add(hostFragmentId, fragment)
                .addToBackStack(null)
                .commit()
        }
        else {
            fragmentManager.beginTransaction()
                .add(hostFragmentId, fragment)
                .commit()
        }
    }

    fun replace(fragment: Fragment, allowGoBack : Boolean = false) {
        if (allowGoBack) {
            fragmentManager.beginTransaction()
                .replace(hostFragmentId, fragment)
                .addToBackStack(null)
                .commit()
        }
        else {
            fragmentManager.beginTransaction()
                .replace(hostFragmentId, fragment)
                .commit()
        }

    }

    fun navigateBack() {
        fragmentManager.popBackStack()
    }


    fun backStackNotEmpty(): Boolean {
        return fragmentManager.backStackEntryCount > 0
    }

}