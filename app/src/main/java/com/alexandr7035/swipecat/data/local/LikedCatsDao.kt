package com.alexandr7035.swipecat.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LikedCatsDao {

    @Insert
    fun likeCat(cat: CatEntity)

    @Delete
    fun deleteCat(cat: CatEntity)

    @Query("select * from liked_cats")
    fun getLikedCatsLiveData(): LiveData<List<CatEntity>>
}