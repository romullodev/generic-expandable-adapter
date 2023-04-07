package com.romullodev.generic_expandable_adapter.utils

import com.romullodev.generic_expandable_adapter.R
import com.romullodev.library.entities.CardHeaderModel
import com.romullodev.library.entities.CardHeaderStyle
import com.romullodev.library.entities.CardItemModel

object MockData {

    private fun getRockBands(): List<CardItemModel> = listOf(
        CardItemModel(
            itemName = "Arctic Monkeys",
        ),
//        CardItemModel(
//            itemName = "Imagine Dragons"
//        ),
//        CardItemModel(
//            itemName = "Foo Fighters"
//        ),
//        CardItemModel(
//            itemName = "The Black Keys"
//        ),
//        CardItemModel(
//            itemName = "Kings of Leon"
//        ),
//        CardItemModel(
//            itemName = "Muse"
//        ),
    )

    private fun getPopBands(): List<CardItemModel> = listOf(
        CardItemModel(
            itemName = "Taylor Swift"
        ),
        CardItemModel(
            itemName = "Ed Sheeran"
        ),
        CardItemModel(
            itemName = "Adele"
        ),
        CardItemModel(
            itemName = "Bruno Mars"
        ),
        CardItemModel(
            itemName = "Katy Perry"
        ),
        CardItemModel(
            itemName = "Justin Timberlake"
        ),
        CardItemModel(
            itemName = "Lady Gaga"
        ),
    )

    private fun getHipHopBands(): List<CardItemModel> = listOf(
        CardItemModel(
            itemName = "Kendrick Lamar"
        ),
        CardItemModel(
            itemName = "Drake"
        ),
        CardItemModel(
            itemName = "J. Cole"
        ),
        CardItemModel(
            itemName = "Kanye West"
        ),
        CardItemModel(
            itemName = "Travis Scott"
        ),
        CardItemModel(
            itemName = "Post Malone"
        ),
        CardItemModel(
            itemName = "Cardi B "
        ),
    )

    private fun getJazzBands(): List<CardItemModel> = listOf(
        CardItemModel(
            itemName = "Kamasi Washington"
        ),
        CardItemModel(
            itemName = "Robert Glasper"
        ),
        CardItemModel(
            itemName = "Snarky Puppy"
        ),
        CardItemModel(
            itemName = "Gregory Porter"
        ),
        CardItemModel(
            itemName = "Esperanza Spalding"
        ),
        CardItemModel(
            itemName = "Christian Scott"
        ),
        CardItemModel(
            itemName = "Hiromi Uehara"
        ),
    )

    private fun getBluesBands(): List<CardItemModel> = listOf(
        CardItemModel(
            itemName = "Gary Clark Jr."
        ),
        CardItemModel(
            itemName = "Joe Bonamassa"
        ),
        CardItemModel(
            itemName = "Beth Hart"
        ),
        CardItemModel(
            itemName = "Tedeschi Trucks Band"
        ),
        CardItemModel(
            itemName = "John Mayer"
        ),
        CardItemModel(
            itemName = "Seasick Steve"
        )
    )

    private fun getReggaeBands(): List<CardItemModel> = listOf(
        CardItemModel(
            itemName = "Damian Marley"
        ),
        CardItemModel(
            itemName = "Chronixx"
        ),
        CardItemModel(
            itemName = "Protoje"
        ),
        CardItemModel(
            itemName = "Morgan Heritage"
        ),
        CardItemModel(
            itemName = "Rebelution"
        ),
        CardItemModel(
            itemName = "Kabaka Pyramid"
        ),
        CardItemModel(
            itemName = "Alborosie"
        )
    )

    private fun getElectronicBands(): List<CardItemModel> = listOf(
        CardItemModel(
            itemName = "Daft Punk"
        ),
        CardItemModel(
            itemName = "The Chainsmokers"
        ),
        CardItemModel(
            itemName = "Avicii"
        ),
        CardItemModel(
            itemName = "Calvin Harris"
        ),
        CardItemModel(
            itemName = "Major Lazer"
        ),
        CardItemModel(
            itemName = "Disclosure"
        ),
        CardItemModel(
            itemName = "Zedd"
        )
    )

    fun getMusics(): List<CardHeaderModel> = listOf(
        CardHeaderModel(
            cardName = "Rock",
            items = getRockBands(),
            cardHeaderStyle = CardHeaderStyle(
                backgroundImgRes = R.drawable.rock,
                backgroundColorItems = R.color.rock_color,
                hasThickness = false,
            )
        ),
//        CardHeaderModel(
//            cardName = "Pop",
//            items = getPopBands(),
//            cardHeaderStyle = CardHeaderStyle(
//                backgroundImgRes = R.drawable.pop,
//                backgroundColorItems = R.color.pop_color,
//            ),
//        ),
//        CardHeaderModel(
//            cardName = "Hip Hop",
//            items = getHipHopBands(),
//            cardHeaderStyle = CardHeaderStyle(
//                backgroundImgRes = R.drawable.hip_hop,
//                backgroundColorItems = R.color.hip_hop_color,
//            ),
//        ),
//        CardHeaderModel(
//            cardName = "Jazz",
//            items = getJazzBands(),
//            cardHeaderStyle = CardHeaderStyle(
//                backgroundImgRes = R.drawable.jazz,
//                backgroundColorItems = R.color.jazz_color,
//            ),
//        ),
//        CardHeaderModel(
//            cardName = "Blues",
//            items = getBluesBands(),
//            cardHeaderStyle = CardHeaderStyle(
//                backgroundImgRes = R.drawable.blues,
//                backgroundColorItems = R.color.blues_color,
//            ),
//        ),
//        CardHeaderModel(
//            cardName = "Reggae",
//            items = getReggaeBands(),
//            cardHeaderStyle = CardHeaderStyle(
//                backgroundImgRes = R.drawable.reggae,
//                backgroundColorItems = R.color.reggae_color,
//            ),
//        ),
//        CardHeaderModel(
//            cardName = "Electronic",
//            items = getElectronicBands(),
//            cardHeaderStyle = CardHeaderStyle(
//                backgroundImgRes = R.drawable.eletronic,
//                backgroundColorItems = R.color.electronic_color,
//            ),
//        ),
    )
}