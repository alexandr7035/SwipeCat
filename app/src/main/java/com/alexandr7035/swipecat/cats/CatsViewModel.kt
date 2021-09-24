package com.alexandr7035.swipecat.cats

import androidx.lifecycle.ViewModel
import com.alexandr7035.swipecat.app_core.Cat
import com.alexandr7035.swipecat.app_core.RandomCatProviderImpl
import timber.log.Timber

class CatsViewModel : ViewModel() {

    private val catsProvider = RandomCatProviderImpl()

    fun getCat(): Cat {
        return catsProvider.getRandomCat()
    }

    fun getCats(number: Int): List<Cat> {
        return catsProvider.getRandomCats(number)
    }
}