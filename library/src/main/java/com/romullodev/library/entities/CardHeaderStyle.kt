package com.romullodev.library.entities

import com.romullodev.library.R

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
