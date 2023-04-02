package com.romullodev.generic_expandable_adapter.utils

import android.widget.ImageView
import androidx.core.content.ContextCompat

fun ImageView.setupImage(drawableImgRes: Int?) {
    drawableImgRes?.let {
        background = ContextCompat.getDrawable(context, it)
    }
}

fun ImageView.setupBackgroundColor(colorRes: Int) {
    setBackgroundColor(
        ContextCompat.getColor(context, colorRes)
    )
}