package com.alexandr7035.swipecat.data.remote

import kotlin.math.floor

class RandomCatProviderImpl: RandomCatProvider {

    private val URL = "https://d2ph5fj80uercy.cloudfront.net"
    private val catsListSize = 100

    override suspend fun getRandomCat(): CatRemote {
        val url = getRandomCatUrl()
        return CatRemote(url = url)
    }

    override suspend fun getRandomCats(): List<CatRemote> {

        val cats = ArrayList<CatRemote>()

        for (i in 1..catsListSize) {
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