package com.github.romullodev.generic_expandable_adapter.entities

import androidx.annotation.DimenRes
import com.github.romullodev.generic_expandable_adapter.R

data class CardItemStyle(
    val backgroundColorRes: Int? = null,
    val titleColorRes: Int = R.color.white,
    val hasThickness: Boolean = true,
    val thicknessColor: Int = R.color.white,
    @DimenRes val radiusItemContainer: Int = R.dimen.card_radius
)
