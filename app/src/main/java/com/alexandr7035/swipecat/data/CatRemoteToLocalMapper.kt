package com.alexandr7035.swipecat.data

import com.alexandr7035.swipecat.app_core.Mapper
import com.alexandr7035.swipecat.data.local.CatEntity
import com.alexandr7035.swipecat.data.remote.CatRemote

class CatRemoteToLocalMapper: Mapper<CatRemote, CatEntity> {
    override fun transform(data: CatRemote): CatEntity {
        return CatEntity(imageLocalUri = data.url)
    }
}