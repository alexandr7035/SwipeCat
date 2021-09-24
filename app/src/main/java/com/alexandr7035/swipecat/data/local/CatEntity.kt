package com.alexandr7035.swipecat.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liked_cats")
data class CatEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var url: String)