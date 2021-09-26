package com.alexandr7035.swipecat.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import com.alexandr7035.swipecat.data.local.CatEntity
import com.alexandr7035.swipecat.data.local.ImageManager
import com.alexandr7035.swipecat.data.local.LikedCatsDao
import com.alexandr7035.swipecat.data.remote.CatRemote
import com.alexandr7035.swipecat.data.remote.RandomCatProvider
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val catsProvider: RandomCatProvider,
    private val dao: LikedCatsDao,
    private val imageManager: ImageManager,
    private val catsMapper: CatRemoteToLocalMapper): Repository {

    override suspend fun getRandomCats(number: Int): List<CatRemote> {
        return catsProvider.getRandomCats(number)
    }

    override fun getLikedCatsLiveData(): LiveData<List<CatEntity>> {
        return dao.getLikedCatsLiveData()
    }

    override suspend fun likeCat(cat: CatRemote) {
        val uri = imageManager.saveImage(cat.url)
        // Fixme: use mapper
        val catEntity = CatEntity(url = uri.toString())
        dao.likeCat(catEntity)
    }

    override suspend fun removeCatLike(cat: CatEntity) {
        dao.deleteCat(cat)
    }
}