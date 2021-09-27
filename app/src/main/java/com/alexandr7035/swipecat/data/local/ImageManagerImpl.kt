package com.alexandr7035.swipecat.data.local

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import javax.inject.Inject

class ImageManagerImpl @Inject constructor(private val application: Application): ImageManager {

    override fun saveImage(urlString: String): Uri {
        val filesDir = application.getExternalFilesDir(null)

        try {
            val url = URL(urlString)
            val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())

            // FIXME better naming
            val imagePath = filesDir!!.absolutePath + File.separator + "cat-${System.currentTimeMillis()}.jpg"

            val outStream = FileOutputStream(imagePath)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)

            outStream.close()

            val savedUri = Uri.fromFile(File(imagePath))

            Timber.tag("IMAGE_MANAGER").d(savedUri.toString())

            return savedUri
        }

        catch (e: IOException) {
            throw IOException("${this.javaClass} can't save image ${e.stackTraceToString()}")
        }

    }

    override fun deleteImage(uri: Uri) {
        Timber.tag("IMAGE_MANAGER").d("delete ${uri.path}")

        if (uri.path != null) {
            File(uri.path!!).delete()
        }
    }

}