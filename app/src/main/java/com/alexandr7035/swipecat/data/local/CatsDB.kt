package com.alexandr7035.swipecat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CatEntity::class], version = 2)
abstract class CatsDB: RoomDatabase() {
    abstract fun getLikedCatsDao(): LikedCatsDao
}