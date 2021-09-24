package com.alexandr7035.swipecat.cats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexandr7035.swipecat.app_core.Cat
import com.alexandr7035.swipecat.app_core.RandomCatProviderImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class CatsViewModel : ViewModel() {

    private val catsProvider = RandomCatProviderImpl()
    private val catsLiveData = MutableLiveData<List<Cat>>()

//    fun getCat(): Cat {
//        return catsProvider.getRandomCat()
//    }

    fun fetchCats(number: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            catsLiveData.postValue(catsProvider.getRandomCats(number))
        }
    }

    fun getCatsLiveData(): LiveData<List<Cat>> {
        return catsLiveData
    }
}