package com.github.romullodev.generic_expandable_adapter.entities

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes

data class DefaultSwipeOption(
    @DrawableRes val icon: Int,
    @ColorRes val iconColor: Int,
    @ColorRes val backgroundColor: Int,
    val optionId: Int,
    @DimenRes val width: Int
)
