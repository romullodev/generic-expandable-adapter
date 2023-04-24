package com.github.romullodev.generic_expandable_adapter.entities

import androidx.annotation.DimenRes
import com.github.romullodev.generic_expandable_adapter.R

data class CardHeaderStyle(
    val backgroundColorRes: Int = R.color.black,
    val backgroundImgRes: Int? = null,
    val titleColorRes: Int = R.color.white,
    val subtitleColorRes: Int = R.color.a_40_white,
    val arrowDownIconColorRes: Int = R.color.white,
    val hasThickness: Boolean = true,
    val thicknessColor: Int = R.color.white,
    val backgroundColorItems: Int? = null,
    @DimenRes val radiusHeaderContainer: Int = R.dimen.card_radius
)
