package com.alexandr7035.swipecat.data

import android.app.Application
import android.content.Context
import com.alexandr7035.swipecat.R
import javax.inject.Inject

class AppPreferences @Inject constructor(private val application: Application) {
    private val PREFS_NAME = application.getString(R.string.app_name)
    private val prefs = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var likesRecyclerMode: String?
        get() = prefs.getString(application.getString(R.string.shared_pref_likes_recycler_mode), application.getString(R.string.shared_pref_likes_recycler_mode_linear))
        set(value) = prefs.edit().putString(application.getString(R.string.shared_pref_likes_recycler_mode), value).apply()

}