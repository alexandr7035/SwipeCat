package com.alexandr7035.swipecat.app_core

interface RandomCatProvider {
    suspend fun getRandomCat(): Cat

    suspend fun getRandomCats(listSize: Int): List<Cat>
}