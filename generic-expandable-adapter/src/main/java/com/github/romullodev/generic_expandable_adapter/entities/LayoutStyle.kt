package com.github.romullodev.generic_expandable_adapter.entities

import androidx.annotation.DimenRes
import com.github.romullodev.generic_expandable_adapter.R

data class LayoutStyle(
    @DimenRes val radius: Int,
) {
    companion object {
        val DEFAULT = LayoutStyle(R.dimen.card_radius)
    }
}
