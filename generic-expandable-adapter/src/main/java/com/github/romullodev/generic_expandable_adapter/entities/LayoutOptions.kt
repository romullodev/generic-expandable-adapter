package com.github.romullodev.generic_expandable_adapter.entities

import androidx.annotation.DimenRes
import com.github.romullodev.generic_expandable_adapter.R

data class LayoutOptions(
    @DimenRes val radius: Int,
    val hasThicknessForAll: Boolean,
    val thicknessColorForAll: Int,
    val expandAllAtFirst: Boolean
) {
    companion object {
        val DEFAULT = LayoutOptions(
            radius = R.dimen.card_radius,
            hasThicknessForAll = true,
            thicknessColorForAll = R.color.white,
            expandAllAtFirst = false
        )
    }
}
