package com.romullodev.library.entities

import com.romullodev.library.R

data class CardItemStyle(
    val backgroundColorRes: Int? = null,
    val titleColorRes: Int = R.color.white,
    val hasThickness: Boolean = true,
    val thicknessColor: Int = R.color.white,
    val backgroundImgRes: Int? = null,
)
