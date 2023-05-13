package com.github.romullodev.generic_expandable_adapter.util

import com.github.romullodev.generic_expandable_adapter.R
import com.github.romullodev.generic_expandable_adapter.entities.*
import com.github.romullodev.generic_expandable_adapter.test.R as testR

class TestUtils {
    companion object {
        const val HEADER_TITLE = "header.title"
        const val HEADER_SUBTITLE = "header.subtitle"
        val HEADER_IMG_BACKGROUND = R.drawable.pop_low_resolution
        val HEADER_BACKGROUND_COLOR = R.color.color_1
        val HEADER_TITLE_COLOR = R.color.color_3
        val HEADER_SUBTITLE_COLOR = R.color.color_4
        val HEADER_BACKGROUND_ITEMS = R.color.color_3
        val HEADER_EXPANDABLE_IC_COLOR = R.color.color_1
        val HEADER_OPTION_ICON = testR.drawable.delete_icon
        val HEADER_OPTION_ICON_COLOR = R.color.color_3
        const val HEADER_OPTION_ID = 2
        const val HEADER_OPTION_WITH = testR.dimen.header_width

        const val ITEM_TITLE = "item.title"
        val ITEM_BACKGROUND_COLOR = R.color.color_2
        val ITEM_TITLE_COLOR = R.color.color_1
        val ITEM_OPTION_ICON = testR.drawable.edit_icon
        val ITEM_OPTION_ICON_COLOR = R.color.color_2
        const val ITEM_OPTION_ID = 3
        const val ITEM_OPTION_WITH = testR.dimen.item_width

        val LAYOUT_THICKNESS_COLOR = R.color.color_2

        fun getHeaderModelFilled() = CardHeaderModel(
            id = 1L,
            headerTitle = HEADER_TITLE,
            headerSubtitle = HEADER_SUBTITLE,
            items = listOf(
                getItemModelFilled()
            ),
            cardHeaderStyle = CardHeaderStyle(
                backgroundImgRes = HEADER_IMG_BACKGROUND,
                backgroundColorRes = HEADER_BACKGROUND_COLOR,
                titleColorRes = HEADER_TITLE_COLOR,
                subtitleColorRes = HEADER_SUBTITLE_COLOR,
                arrowDownIconColorRes = HEADER_EXPANDABLE_IC_COLOR
            )
        )

        fun getItemModelFilled() = CardItemModel(
            id = 2L,
            itemTitle = ITEM_TITLE,
            cardItemStyle = CardItemStyle(
                backgroundColorRes = ITEM_BACKGROUND_COLOR,
                titleColorRes = ITEM_TITLE_COLOR
            )
        )

        fun getOptionOnHeader(): DefaultSwipeOption =
            DefaultSwipeOption(
                icon = HEADER_OPTION_ICON,
                iconColor = HEADER_OPTION_ICON_COLOR,
                backgroundColor = HEADER_BACKGROUND_COLOR,
                optionId = HEADER_OPTION_ID,
                width = HEADER_OPTION_WITH
            )

        fun getOptionOnItem(): DefaultSwipeOption =
            DefaultSwipeOption(
                icon = ITEM_OPTION_ICON,
                iconColor = ITEM_OPTION_ICON_COLOR,
                backgroundColor = ITEM_BACKGROUND_COLOR,
                optionId = ITEM_OPTION_ID,
                width = ITEM_OPTION_WITH
            )

        fun getLayoutOptions(): LayoutOptions =
            LayoutOptions.DEFAULT.copy(
                thicknessColorForAll = LAYOUT_THICKNESS_COLOR
            )
    }
}