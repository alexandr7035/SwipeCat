package com.alexandr7035.swipecat.cats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexandr7035.swipecat.data.AppPreferences
import com.alexandr7035.swipecat.data.Repository
import com.alexandr7035.swipecat.data.local.CatEntity
import com.alexandr7035.swipecat.data.remote.CatRemote
import com.alexandr7035.swipecat.data.remote.RandomCatProviderImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatsViewModel @Inject constructor(
    private val repository: Repository,
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val catsProvider = RandomCatProviderImpl()
    private val catsLiveData = MutableLiveData<List<CatRemote>>()

    fun fetchCats(number: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            catsLiveData.postValue(catsProvider.getRandomCats(number))
        }
    }

    fun getCatsLiveData(): LiveData<List<CatRemote>> {
        return catsLiveData
    }

    fun likeCat(cat: CatRemote) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.likeCat(cat)
        }
    }

    fun getLikedCatsLiveData(): LiveData<List<CatEntity>> {
        return repository.getLikedCatsLiveData()
    }

    fun removeLikedCat(cat: CatEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeCatLike(cat)
        }
    }

    fun getLikedRecyclerMode(): String {
        return appPreferences.likesRecyclerMode!!
    }

    fun saveLikedRecyclerMode(mode: String) {
        appPreferences.likesRecyclerMode = mode
    }
}