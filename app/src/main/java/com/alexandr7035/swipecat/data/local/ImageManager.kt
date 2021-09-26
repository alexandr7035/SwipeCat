package com.alexandr7035.swipecat.data.local

import android.net.Uri


// Responsible only for saving images and deleting from memory
// Do not expand to populate views
interface ImageManager {
    fun saveImage(urlString: String): Uri

    fun deleteImage(uri: Uri)
}