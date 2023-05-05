package com.github.romullodev.generic_expandable_adapter.entities

import androidx.annotation.DimenRes

data class SwipeOptionDefault<T>(
    val icon: Int,
    val iconColor: Int,
    val backgroundColor: Int,
    val optionId: Int,
    @DimenRes
    val width: Int
)
