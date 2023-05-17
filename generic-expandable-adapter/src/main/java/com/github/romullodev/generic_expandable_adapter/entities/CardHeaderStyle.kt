package com.github.romullodev.generic_expandable_adapter.entities

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.github.romullodev.generic_expandable_adapter.R

data class CardHeaderStyle(
    @ColorRes val backgroundColorRes: Int = R.color.black,
    @DrawableRes val backgroundImgRes: Int? = null,
    @ColorRes val titleColorRes: Int = R.color.white,
    @ColorRes val subtitleColorRes: Int = R.color.a_40_white,
    @ColorRes val arrowDownIconColorRes: Int = R.color.white,
    val hasThicknessOnHeader: Boolean? = null,
    val backgroundColorItems: Int? = null
)
