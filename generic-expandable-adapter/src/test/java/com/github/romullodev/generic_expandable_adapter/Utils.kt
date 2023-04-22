package com.github.romullodev.generic_expandable_adapter

import com.github.romullodev.generic_expandable_adapter.entities.CardHeaderModel
import com.github.romullodev.generic_expandable_adapter.entities.CardHeaderStyle
import com.github.romullodev.generic_expandable_adapter.entities.CardItemModel
import com.github.romullodev.generic_expandable_adapter.entities.CardItemStyle

class Utils {
    companion object {
        const val HEADER_TITLE = "header.title"
        const val HEADER_SUBTITLE = "header.subtitle"
        val HEADER_IMG_BACKGROUND = R.drawable.pop_low_resolution
        val HEADER_BACKGROUND_COLOR = R.color.color_1
        val HEADER_TITLE_COLOR = R.color.color_3
        val HEADER_SUBTITLE_COLOR = R.color.color_4
        val HEADER_THICKNESS_COLOR = R.color.color_2

        const val ITEM_TITLE = "item.title"
        val ITEM_BACKGROUND_COLOR = R.color.color_2
        val ITEM_TITLE_COLOR = R.color.color_1
        val ITEM_THICKNESS_COLOR = R.color.color_3

        fun getHeaderModel() = CardHeaderModel(
            items = listOf(
                getItemModel()
            ),
            cardHeaderStyle = CardHeaderStyle()
        )
        fun getItemModel() = CardItemModel(
            cardItemStyle = CardItemStyle()
        )
    }
}