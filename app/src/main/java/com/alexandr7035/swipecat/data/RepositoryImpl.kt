package com.alexandr7035.swipecat.data

import androidx.lifecycle.LiveData
import com.alexandr7035.swipecat.data.local.CatEntity
import com.alexandr7035.swipecat.data.local.LikedCatsDao
import com.alexandr7035.swipecat.data.remote.CatRemote
import com.alexandr7035.swipecat.data.remote.RandomCatProvider
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val catsProvider: RandomCatProvider,
    private val dao: LikedCatsDao,
    private val catsMapper: CatRemoteToLocalMapper): Repository {

    override suspend fun getRandomCats(number: Int): List<CatRemote> {
        return catsProvider.getRandomCats(number)
    }

    override fun getLikedCatsLiveData(): LiveData<List<CatEntity>> {
        return dao.getLikedCatsLiveData()
    }

    override suspend fun likeCat(cat: CatRemote) {
        dao.likeCat(catsMapper.transform(cat))
    }

    override suspend fun removeCatLike(cat: CatEntity) {
        // TODO implement later
    }
}