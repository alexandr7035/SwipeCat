package com.alexandr7035.swipecat.core

interface Mapper<SRC, DST> {
    fun transform(data: SRC): DST
}