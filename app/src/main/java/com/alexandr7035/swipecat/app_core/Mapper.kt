package com.alexandr7035.swipecat.app_core

interface Mapper<SRC, DST> {
    fun transform(data: SRC): DST
}