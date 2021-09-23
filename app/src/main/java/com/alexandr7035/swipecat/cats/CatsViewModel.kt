package com.alexandr7035.swipecat.cats

import androidx.lifecycle.ViewModel
import timber.log.Timber

class CatsViewModel : ViewModel() {
    fun testMethod() {
        Timber.d("test viewModel method called")
    }

}