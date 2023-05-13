package com.github.romullodev.generic_expandable_adapter.entities

import androidx.annotation.ColorRes
import com.github.romullodev.generic_expandable_adapter.R

data class CardItemStyle(
    @ColorRes val backgroundColorRes: Int? = null,
    @ColorRes val titleColorRes: Int = R.color.white,
    val hasThicknessOnItem: Boolean? = null
)
