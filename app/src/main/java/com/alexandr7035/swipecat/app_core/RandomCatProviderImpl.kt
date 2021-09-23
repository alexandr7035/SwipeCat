package com.alexandr7035.swipecat.app_core

import kotlin.math.floor

class RandomCatProviderImpl: RandomCatProvider {

    private val URL = "https://d2ph5fj80uercy.cloudfront.net"

    override fun getRandomCat(): Cat {
        val url = getRandomCatUrl()
        return Cat(url = url)
    }

    override fun getRandomCats(listSize: Int): List<Cat> {

        val cats = ArrayList<Cat>()

        for (i in 1..listSize) {
            cats.add(getRandomCat())
        }

        return cats
    }

    private fun getRandomCatUrl(): String {

        val folderNumber = "0${(floor(Math.random() + 1) * 6).toInt()}"
        val catNumber = "${floor((Math.random() * 5000) + 1).toInt()}"

        return "$URL/$folderNumber/cat${catNumber}.jpg"
    }
}