package com.alexandr7035.swipecat.data

import androidx.lifecycle.LiveData
import com.alexandr7035.swipecat.data.local.CatEntity
import com.alexandr7035.swipecat.data.remote.CatRemote

interface Repository {
    suspend fun getRandomCats(number: Int): List<CatRemote>

    fun getLikedCatsLiveData(): LiveData<List<CatEntity>>

    suspend fun likeCat(cat: CatRemote)

    suspend fun removeCatLike(cat: CatEntity)
}