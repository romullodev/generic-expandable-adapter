package com.romullodev.generic_expandable_adapter.utils

import android.content.Context
import com.romullodev.generic_expandable_adapter.R
import com.github.romullodev.generic_expandable_adapter.entities.CardHeaderModel
import com.github.romullodev.generic_expandable_adapter.entities.CardHeaderStyle
import com.github.romullodev.generic_expandable_adapter.entities.CardItemModel
import com.github.romullodev.generic_expandable_adapter.entities.CardItemStyle

object MockData {

    private fun getRockBands(): List<CardItemModel> = listOf(
        CardItemModel(
            itemTitle = "Arctic Monkeys",
            cardItemStyle = CardItemStyle(
                backgroundColorRes = R.color.green,
                thicknessColor = R.color.pop_color
            )
        ),
        CardItemModel(
            itemTitle = "Imagine Dragons",
            cardItemStyle = CardItemStyle(
                backgroundColorRes = R.color.rock_color,
                thicknessColor = R.color.reggae_color
            )
        ),
        CardItemModel(
            itemTitle = "Foo Fighters",
            cardItemStyle = CardItemStyle(
                backgroundColorRes = R.color.rock_color,
                hasThickness = false
            )
        ),
        CardItemModel(
            itemTitle = "The Black Keys"
        ),
        CardItemModel(
            itemTitle = "Kings of Leon"
        ),
        CardItemModel(
            itemTitle = "Muse"
        ),
    )

    private fun getPopBands(): List<CardItemModel> = listOf(
        CardItemModel(
            itemTitle = "Taylor Swift"
        ),
        CardItemModel(
            itemTitle = "Ed Sheeran"
        ),
        CardItemModel(
            itemTitle = "Adele"
        ),
        CardItemModel(
            itemTitle = "Bruno Mars"
        ),
        CardItemModel(
            itemTitle = "Katy Perry"
        ),
        CardItemModel(
            itemTitle = "Justin Timberlake"
        ),
        CardItemModel(
            itemTitle = "Lady Gaga"
        ),
    )

    private fun getHipHopBands(): List<CardItemModel> = listOf(
        CardItemModel(
            itemTitle = "Kendrick Lamar"
        ),
        CardItemModel(
            itemTitle = "Drake"
        ),
        CardItemModel(
            itemTitle = "J. Cole"
        ),
        CardItemModel(
            itemTitle = "Kanye West"
        ),
        CardItemModel(
            itemTitle = "Travis Scott"
        ),
        CardItemModel(
            itemTitle = "Post Malone"
        ),
        CardItemModel(
            itemTitle = "Cardi B "
        ),
    )

    private fun getJazzBands(): List<CardItemModel> = listOf(
        CardItemModel(
            itemTitle = "Kamasi Washington"
        ),
        CardItemModel(
            itemTitle = "Robert Glasper"
        ),
        CardItemModel(
            itemTitle = "Snarky Puppy"
        ),
        CardItemModel(
            itemTitle = "Gregory Porter"
        ),
        CardItemModel(
            itemTitle = "Esperanza Spalding"
        ),
        CardItemModel(
            itemTitle = "Christian Scott"
        ),
        CardItemModel(
            itemTitle = "Hiromi Uehara"
        ),
    )

    private fun getBluesBands(): List<CardItemModel> = listOf(
        CardItemModel(
            itemTitle = "Gary Clark Jr."
        ),
        CardItemModel(
            itemTitle = "Joe Bonamassa"
        ),
        CardItemModel(
            itemTitle = "Beth Hart"
        ),
        CardItemModel(
            itemTitle = "Tedeschi Trucks Band"
        ),
        CardItemModel(
            itemTitle = "John Mayer"
        ),
        CardItemModel(
            itemTitle = "Seasick Steve"
        )
    )

    private fun getReggaeBands(): List<CardItemModel> = listOf(
        CardItemModel(
            itemTitle = "Damian Marley"
        ),
        CardItemModel(
            itemTitle = "Chronixx"
        ),
        CardItemModel(
            itemTitle = "Protoje"
        ),
        CardItemModel(
            itemTitle = "Morgan Heritage"
        ),
        CardItemModel(
            itemTitle = "Rebelution"
        ),
        CardItemModel(
            itemTitle = "Kabaka Pyramid"
        ),
        CardItemModel(
            itemTitle = "Alborosie"
        )
    )

    private fun getElectronicBands(): List<CardItemModel> = listOf(
        CardItemModel(
            itemTitle = "Daft Punk"
        ),
        CardItemModel(
            itemTitle = "The Chainsmokers"
        ),
        CardItemModel(
            itemTitle = "Avicii"
        ),
        CardItemModel(
            itemTitle = "Calvin Harris"
        ),
        CardItemModel(
            itemTitle = "Major Lazer"
        ),
        CardItemModel(
            itemTitle = "Disclosure"
        ),
        CardItemModel(
            itemTitle = "Zedd"
        )
    )

    fun getMusics(context: Context, hasBackgroundImg: Boolean): List<CardHeaderModel> = listOf(
        CardHeaderModel(
            headerTitle = "Rock",
            headerSubtitle = context.getString(
                R.string.total_bands,
                getRockBands().size.toString()
            ),
            items = getRockBands(),
            cardHeaderStyle = CardHeaderStyle(
                backgroundColorItems = R.color.rock_color
            ).run {
                if(hasBackgroundImg)
                    copy(backgroundImgRes = R.drawable.rock)
                else
                    copy(backgroundColorRes = R.color.rock_color)
            }
        ),
        CardHeaderModel(
            headerTitle = "Pop",
            headerSubtitle = context.getString(
                R.string.total_bands,
                getPopBands().size.toString()
            ),
            items = getPopBands(),
            cardHeaderStyle = CardHeaderStyle(
                backgroundColorItems = R.color.pop_color,
            ).run {
                if(hasBackgroundImg)
                    copy(backgroundImgRes = R.drawable.pop)
                else
                    copy(backgroundColorRes = R.color.pop_color)
            },
        ),
        CardHeaderModel(
            headerTitle = "Hip Hop",
            headerSubtitle = context.getString(
                R.string.total_bands,
                getHipHopBands().size.toString()
            ),
            items = getHipHopBands(),
            cardHeaderStyle = CardHeaderStyle(
                backgroundColorItems = R.color.hip_hop_color,
            ).run {
                if(hasBackgroundImg)
                    copy(backgroundImgRes = R.drawable.hip_hop)
                else
                    copy(backgroundColorRes = R.color.hip_hop_color)
            },
        ),
        CardHeaderModel(
            headerTitle = "Jazz",
            headerSubtitle = context.getString(
                R.string.total_bands,
                getJazzBands().size.toString()
            ),
            items = getJazzBands(),
            cardHeaderStyle = CardHeaderStyle(
                backgroundColorItems = R.color.jazz_color,
            ).run {
                if(hasBackgroundImg)
                    copy(backgroundImgRes = R.drawable.jazz)
                else
                    copy(backgroundColorRes = R.color.jazz_color)
            },
        ),
        CardHeaderModel(
            headerTitle = "Blues",
            headerSubtitle = context.getString(
                R.string.total_bands,
                getBluesBands().size.toString()
            ),
            items = getBluesBands(),
            cardHeaderStyle = CardHeaderStyle(
                backgroundColorItems = R.color.blues_color,
            ).run {
                if(hasBackgroundImg)
                    copy(backgroundImgRes = R.drawable.blues)
                else
                    copy(backgroundColorRes = R.color.blues_color)
            },
        ),
        CardHeaderModel(
            headerTitle = "Reggae",
            headerSubtitle = context.getString(
                R.string.total_bands,
                getReggaeBands().size.toString()
            ),
            items = getReggaeBands(),
            cardHeaderStyle = CardHeaderStyle(
                backgroundColorItems = R.color.reggae_color,
            ).run {
                if(hasBackgroundImg)
                    copy(backgroundImgRes = R.drawable.reggae)
                else
                    copy(backgroundColorRes = R.color.reggae_color)
            },
        ),
        CardHeaderModel(
            headerTitle = "Electronic",
            headerSubtitle = context.getString(
                R.string.total_bands,
                getElectronicBands().size.toString()
            ),
            items = getElectronicBands(),
            cardHeaderStyle = CardHeaderStyle(
                backgroundColorItems = R.color.electronic_color,
            ).run {
                if(hasBackgroundImg)
                    copy(backgroundImgRes = R.drawable.eletronic)
                else
                    copy(backgroundColorRes = R.color.electronic_color)
            },
        ),
    )

    private fun getCustomItem(): List<CustomItemModel> =
        listOf(
            CustomItemModel(
                customItemName = "Item 1"
            ),
            CustomItemModel(
                customItemName = "Item 2"
            ),
            CustomItemModel(
                customItemName = "Item 3"
            ),
        )
    fun getCustomHeader(): List<CustomHeaderModel> =
        listOf(
            CustomHeaderModel(
                customHeaderName = "Header 1",
                items = getCustomItem()
            ),
            CustomHeaderModel(
                customHeaderName = "Header 2",
                items = getCustomItem()
            ),
            CustomHeaderModel(
                customHeaderName = "Header 3",
                items = getCustomItem()
            ),
        )
}