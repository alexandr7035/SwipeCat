package com.alexandr7035.swipecat.data.remote

import com.alexandr7035.swipecat.data.remote.CatRemote

interface RandomCatProvider {
    suspend fun getRandomCat(): CatRemote

    suspend fun getRandomCats(listSize: Int): List<CatRemote>
}