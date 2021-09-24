package com.alexandr7035.swipecat.app_core

import android.content.Context
import android.util.AttributeSet
import android.view.View.MeasureSpec
import androidx.appcompat.widget.AppCompatImageView

class SquareImageView: AppCompatImageView {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, widthSize);
        }
        else if(heightMode == MeasureSpec.EXACTLY && widthMode != MeasureSpec.EXACTLY) {
            setMeasuredDimension(heightSize, heightSize);
        }
        else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}