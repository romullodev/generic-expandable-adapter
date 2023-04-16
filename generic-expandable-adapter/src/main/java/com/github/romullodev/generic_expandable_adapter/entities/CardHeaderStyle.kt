package com.github.romullodev.generic_expandable_adapter.entities

import com.github.romullodev.generic_expandable_adapter.R

data class CardHeaderStyle(
    val backgroundColorRes: Int = R.color.black,
    val titleColorRes: Int = R.color.white,
    val subtitleColorRes: Int = R.color.a_40_white,
    val arrowDownIconColorRes: Int = R.color.white,
    val hasThickness: Boolean = true,
    val thicknessColor: Int = R.color.white,
    val backgroundImgRes: Int? = null,
    val backgroundColorItems: Int? = null
)
