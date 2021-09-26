package com.alexandr7035.swipecat.data.local

import android.net.Uri

interface ImageManager {
    fun saveImage(urlString: String): Uri

    fun getImage(uri: Uri)
}