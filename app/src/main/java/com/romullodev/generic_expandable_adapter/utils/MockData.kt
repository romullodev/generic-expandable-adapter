package com.romullodev.generic_expandable_adapter.utils

import android.content.Context
import com.github.romullodev.generic_expandable_adapter.entities.*
import com.romullodev.generic_expandable_adapter.R

object MockData {

    const val HEADER_SWIPE_DELETE_ID = 1
    const val HEADER_SWIPE_EDIT_ID = 2
    const val ITEM_SWIPE_DELETE_ID = 3

    private fun getRockBands(): List<CardItemModel> = listOf(
        CardItemModel(
            id = 1L,
            itemTitle = "Arctic Monkeys",
            cardItemStyle = CardItemStyle(
                backgroundColorRes = R.color.green,
                thicknessColor = R.color.pop_color
            )
        ),
        CardItemModel(
            id = 2L,
            itemTitle = "Imagine Dragons",
            cardItemStyle = CardItemStyle(
                backgroundColorRes = R.color.rock_color,
                thicknessColor = R.color.reggae_color
            )
        ),
        CardItemModel(
            id = 3L,
            itemTitle = "Foo Fighters",
            cardItemStyle = CardItemStyle(
                backgroundColorRes = R.color.rock_color,
                hasThickness = false
            )
        ),
        CardItemModel(
            id = 4L,
            itemTitle = "The Black Keys"
        ),
        CardItemModel(
            id = 5L,
            itemTitle = "Kings of Leon"
        ),
        CardItemModel(
            id = 6L,
            itemTitle = "Muse"
        ),
    )

    private fun getPopBands(): List<CardItemModel> = listOf(
        CardItemModel(
            id = 7L,
            itemTitle = "Taylor Swift"
        ),
        CardItemModel(
            id = 8L,
            itemTitle = "Ed Sheeran"
        ),
        CardItemModel(
            id = 9L,
            itemTitle = "Adele"
        ),
        CardItemModel(
            id = 10L,
            itemTitle = "Bruno Mars"
        ),
        CardItemModel(
            id = 11L,
            itemTitle = "Katy Perry"
        ),
        CardItemModel(
            id = 12L,
            itemTitle = "Justin Timberlake"
        ),
        CardItemModel(
            id = 13L,
            itemTitle = "Lady Gaga"
        ),
    )

    private fun getHipHopBands(): List<CardItemModel> = listOf(
        CardItemModel(
            id = 14L,
            itemTitle = "Kendrick Lamar"
        ),
        CardItemModel(
            id = 15L,
            itemTitle = "Drake"
        ),
        CardItemModel(
            id = 16L,
            itemTitle = "J. Cole"
        ),
        CardItemModel(
            id = 17L,
            itemTitle = "Kanye West"
        ),
        CardItemModel(
            id = 18L,
            itemTitle = "Travis Scott"
        ),
        CardItemModel(
            id = 19L,
            itemTitle = "Post Malone"
        ),
        CardItemModel(
            id = 20L,
            itemTitle = "Cardi B "
        ),
    )

    private fun getJazzBands(): List<CardItemModel> = listOf(
        CardItemModel(
            id = 21L,
            itemTitle = "Kamasi Washington"
        ),
        CardItemModel(
            id = 22L,
            itemTitle = "Robert Glasper"
        ),
        CardItemModel(
            id = 23L,
            itemTitle = "Snarky Puppy"
        ),
        CardItemModel(
            id = 24L,
            itemTitle = "Gregory Porter"
        ),
        CardItemModel(
            id = 25L,
            itemTitle = "Esperanza Spalding"
        ),
        CardItemModel(
            id = 26L,
            itemTitle = "Christian Scott"
        ),
        CardItemModel(
            id = 27L,
            itemTitle = "Hiromi Uehara"
        ),
    )

    private fun getBluesBands(): List<CardItemModel> = listOf(
        CardItemModel(
            id = 28L,
            itemTitle = "Gary Clark Jr."
        ),
        CardItemModel(
            id = 29L,
            itemTitle = "Joe Bonamassa"
        ),
        CardItemModel(
            id = 30L,
            itemTitle = "Beth Hart"
        ),
        CardItemModel(
            id = 31L,
            itemTitle = "Tedeschi Trucks Band"
        ),
        CardItemModel(
            id = 32L,
            itemTitle = "John Mayer"
        ),
        CardItemModel(
            id = 33L,
            itemTitle = "Seasick Steve"
        )
    )

    private fun getReggaeBands(): List<CardItemModel> = listOf(
        CardItemModel(
            id = 34L,
            itemTitle = "Damian Marley"
        ),
        CardItemModel(
            id = 35L,
            itemTitle = "Chronixx"
        ),
        CardItemModel(
            id = 36L,
            itemTitle = "Protoje"
        ),
        CardItemModel(
            id = 37L,
            itemTitle = "Morgan Heritage"
        ),
        CardItemModel(
            id = 38L,
            itemTitle = "Rebelution"
        ),
        CardItemModel(
            id = 39L,
            itemTitle = "Kabaka Pyramid"
        ),
        CardItemModel(
            id = 40L,
            itemTitle = "Alborosie"
        )
    )

    private fun getElectronicBands(): List<CardItemModel> = listOf(
        CardItemModel(
            id = 41L,
            itemTitle = "Daft Punk"
        ),
        CardItemModel(
            id = 42L,
            itemTitle = "The Chainsmokers"
        ),
        CardItemModel(
            id = 43L,
            itemTitle = "Avicii"
        ),
        CardItemModel(
            id = 44L,
            itemTitle = "Calvin Harris"
        ),
        CardItemModel(
            id = 45L,
            itemTitle = "Major Lazer"
        ),
        CardItemModel(
            id = 46L,
            itemTitle = "Disclosure"
        ),
        CardItemModel(
            id = 47L,
            itemTitle = "Zedd"
        )
    )

    fun getMusics(context: Context, hasBackgroundImg: Boolean): List<CardHeaderModel> = listOf(
        CardHeaderModel(
            id = 48L,
            headerTitle = "Rock",
            headerSubtitle = context.getString(
                R.string.total_bands,
                getRockBands().size.toString()
            ),
            items = getRockBands(),
            cardHeaderStyle = CardHeaderStyle(
                backgroundColorItems = R.color.rock_color
            ).run {
                if (hasBackgroundImg)
                    copy(backgroundImgRes = R.drawable.rock)
                else
                    copy(backgroundColorRes = R.color.rock_color)
            },
        ),
        CardHeaderModel(
            id = 49L,
            headerTitle = "Pop",
            headerSubtitle = context.getString(
                R.string.total_bands,
                getPopBands().size.toString()
            ),
            items = getPopBands(),
            cardHeaderStyle = CardHeaderStyle(
                backgroundColorItems = R.color.pop_color,
            ).run {
                if (hasBackgroundImg)
                    copy(backgroundImgRes = R.drawable.pop)
                else
                    copy(backgroundColorRes = R.color.pop_color)
            },
        ),
        CardHeaderModel(
            id = 50L,
            headerTitle = "Hip Hop",
            headerSubtitle = context.getString(
                R.string.total_bands,
                getHipHopBands().size.toString()
            ),
            items = getHipHopBands(),
            cardHeaderStyle = CardHeaderStyle(
                backgroundColorItems = R.color.hip_hop_color,
            ).run {
                if (hasBackgroundImg)
                    copy(backgroundImgRes = R.drawable.hip_hop)
                else
                    copy(backgroundColorRes = R.color.hip_hop_color)
            },
        ),
        CardHeaderModel(
            id = 51L,
            headerTitle = "Jazz",
            headerSubtitle = context.getString(
                R.string.total_bands,
                getJazzBands().size.toString()
            ),
            items = getJazzBands(),
            cardHeaderStyle = CardHeaderStyle(
                backgroundColorItems = R.color.jazz_color,
            ).run {
                if (hasBackgroundImg)
                    copy(backgroundImgRes = R.drawable.jazz)
                else
                    copy(backgroundColorRes = R.color.jazz_color)
            },
        ),
        CardHeaderModel(
            id = 52L,
            headerTitle = "Blues",
            headerSubtitle = context.getString(
                R.string.total_bands,
                getBluesBands().size.toString()
            ),
            items = getBluesBands(),
            cardHeaderStyle = CardHeaderStyle(
                backgroundColorItems = R.color.blues_color,
            ).run {
                if (hasBackgroundImg)
                    copy(backgroundImgRes = R.drawable.blues)
                else
                    copy(backgroundColorRes = R.color.blues_color)
            },
        ),
        CardHeaderModel(
            id = 53L,
            headerTitle = "Reggae",
            headerSubtitle = context.getString(
                R.string.total_bands,
                getReggaeBands().size.toString()
            ),
            items = getReggaeBands(),
            cardHeaderStyle = CardHeaderStyle(
                backgroundColorItems = R.color.reggae_color,
            ).run {
                if (hasBackgroundImg)
                    copy(backgroundImgRes = R.drawable.reggae)
                else
                    copy(backgroundColorRes = R.color.reggae_color)
            },
        ),
        CardHeaderModel(
            id = 54L,
            headerTitle = "Electronic",
            headerSubtitle = context.getString(
                R.string.total_bands,
                getElectronicBands().size.toString()
            ),
            items = getElectronicBands(),
            cardHeaderStyle = CardHeaderStyle(
                backgroundColorItems = R.color.electronic_color,
            ).run {
                if (hasBackgroundImg)
                    copy(backgroundImgRes = R.drawable.eletronic)
                else
                    copy(backgroundColorRes = R.color.electronic_color)
            },
        ),
    )

    private fun getCustomItem(): List<MyCustomItemModel> =
        listOf(
            MyCustomItemModel(
                myCustomItemId = 1L,
                myCustomItemName = "Item 1"
            ),
            MyCustomItemModel(
                myCustomItemId = 2L,
                myCustomItemName = "Item 2"
            ),
            MyCustomItemModel(
                myCustomItemId = 3L,
                myCustomItemName = "Item 3"
            ),
        )

    fun getCustomHeader(): List<MyCustomHeaderModel> =
        listOf(
            MyCustomHeaderModel(
                myCustomHeaderId = 4L,
                myCustomHeaderName = "Header 1",
                items = getCustomItem(),
            ),
            MyCustomHeaderModel(
                myCustomHeaderId = 5L,
                myCustomHeaderName = "Header 2",
                items = getCustomItem(),
            ),
            MyCustomHeaderModel(
                myCustomHeaderId = 6L,
                myCustomHeaderName = "Header 3",
                items = getCustomItem(),
            ),
        )
}