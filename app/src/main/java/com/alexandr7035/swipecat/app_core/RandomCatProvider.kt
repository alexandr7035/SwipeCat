package com.alexandr7035.swipecat.app_core

interface RandomCatProvider {
    fun getRandomCat(): Cat

    fun getRandomCats(listSize: Int): List<Cat>
}