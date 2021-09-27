package com.alexandr7035.swipecat.data.remote

interface RandomCatProvider {
    suspend fun getRandomCat(): CatRemote

    suspend fun getRandomCats(): List<CatRemote>
}