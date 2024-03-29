package com.alexandr7035.swipecat.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
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
    private val imageManager: ImageManager): Repository {

    override suspend fun getRandomCats(): List<CatRemote> {
        return catsProvider.getRandomCats()
    }

    override fun getLikedCatsLiveData(): LiveData<List<CatEntity>> {
        return dao.getLikedCatsLiveData()
    }

    override suspend fun likeCat(cat: CatRemote) {
        val uri = imageManager.saveImage(cat.url)
        val catEntity = CatEntity(imageLocalUri = uri.toString())
        dao.likeCat(catEntity)
    }

    override suspend fun removeCatLike(cat: CatEntity) {
        dao.deleteCat(cat)
        imageManager.deleteImage(Uri.parse(cat.imageLocalUri))
    }
}