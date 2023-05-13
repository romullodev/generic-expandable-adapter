package com.github.romullodev.generic_expandable_adapter.entities

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import com.github.romullodev.generic_expandable_adapter.R

data class GenericSwipeOption(
    @DrawableRes val icon: Int,
    @ColorRes val iconColor: Int,
    @ColorRes val backgroundColor: Int,
    val optionId: Int,
    @DimenRes val width: Int,
    @DimenRes val height: Int,
    @DimenRes val radius: Int,
    val hasThickness: Boolean = true,
    @ColorRes val thicknessColor: Int = R.color.white
)
