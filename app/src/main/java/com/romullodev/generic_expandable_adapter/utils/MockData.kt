package com.romullodev.generic_expandable_adapter.utils

import com.romullodev.generic_expandable_adapter.R
import com.romullodev.library.entities.CardItemStyle1
import com.romullodev.library.entities.CardStyle1

object MockData {

    private fun getRockBands(): List<CardItemStyle1> = listOf(
        CardItemStyle1(
            itemName = "Arctic Monkeys"
        ),
        CardItemStyle1(
            itemName = "Imagine Dragons"
        ),
        CardItemStyle1(
            itemName = "Foo Fighters"
        ),
        CardItemStyle1(
            itemName = "The Black Keys"
        ),
        CardItemStyle1(
            itemName = "Kings of Leon"
        ),
        CardItemStyle1(
            itemName = "Muse"
        ),
    )

    private fun getPopBands(): List<CardItemStyle1> = listOf(
        CardItemStyle1(
            itemName = "Taylor Swift"
        ),
        CardItemStyle1(
            itemName = "Ed Sheeran"
        ),
        CardItemStyle1(
            itemName = "Adele"
        ),
        CardItemStyle1(
            itemName = "Bruno Mars"
        ),
        CardItemStyle1(
            itemName = "Katy Perry"
        ),
        CardItemStyle1(
            itemName = "Justin Timberlake"
        ),
        CardItemStyle1(
            itemName = "Lady Gaga"
        ),
    )

    private fun getHipHopBands(): List<CardItemStyle1> = listOf(
        CardItemStyle1(
            itemName = "Kendrick Lamar"
        ),
        CardItemStyle1(
            itemName = "Drake"
        ),
        CardItemStyle1(
            itemName = "J. Cole"
        ),
        CardItemStyle1(
            itemName = "Kanye West"
        ),
        CardItemStyle1(
            itemName = "Travis Scott"
        ),
        CardItemStyle1(
            itemName = "Post Malone"
        ),
        CardItemStyle1(
            itemName = "Cardi B "
        ),
    )

    private fun getJazzBands(): List<CardItemStyle1> = listOf(
        CardItemStyle1(
            itemName = "Kamasi Washington"
        ),
        CardItemStyle1(
            itemName = "Robert Glasper"
        ),
        CardItemStyle1(
            itemName = "Snarky Puppy"
        ),
        CardItemStyle1(
            itemName = "Gregory Porter"
        ),
        CardItemStyle1(
            itemName = "Esperanza Spalding"
        ),
        CardItemStyle1(
            itemName = "Christian Scott"
        ),
        CardItemStyle1(
            itemName = "Hiromi Uehara"
        ),
    )

    private fun getBluesBands(): List<CardItemStyle1> = listOf(
        CardItemStyle1(
            itemName = "Gary Clark Jr."
        ),
        CardItemStyle1(
            itemName = "Joe Bonamassa"
        ),
        CardItemStyle1(
            itemName = "Beth Hart"
        ),
        CardItemStyle1(
            itemName = "Tedeschi Trucks Band"
        ),
        CardItemStyle1(
            itemName = "John Mayer"
        ),
        CardItemStyle1(
            itemName = "Seasick Steve"
        )
    )

    private fun getReggaeBands(): List<CardItemStyle1> = listOf(
        CardItemStyle1(
            itemName = "Damian Marley"
        ),
        CardItemStyle1(
            itemName = "Chronixx"
        ),
        CardItemStyle1(
            itemName = "Protoje"
        ),
        CardItemStyle1(
            itemName = "Morgan Heritage"
        ),
        CardItemStyle1(
            itemName = "Rebelution"
        ),
        CardItemStyle1(
            itemName = "Kabaka Pyramid"
        ),
        CardItemStyle1(
            itemName = "Alborosie"
        )
    )

    private fun getElectronicBands(): List<CardItemStyle1> = listOf(
        CardItemStyle1(
            itemName = "Daft Punk"
        ),
        CardItemStyle1(
            itemName = "The Chainsmokers"
        ),
        CardItemStyle1(
            itemName = "Avicii"
        ),
        CardItemStyle1(
            itemName = "Calvin Harris"
        ),
        CardItemStyle1(
            itemName = "Major Lazer"
        ),
        CardItemStyle1(
            itemName = "Disclosure"
        ),
        CardItemStyle1(
            itemName = "Zedd"
        )
    )

    fun getMusicStyles(): List<CardStyle1> = listOf(
        CardStyle1(
            cardName = "Rock",
            backgroundImgRes = R.drawable.rock,
            items = getRockBands(),
        ),
        CardStyle1(
            cardName = "Pop",
            backgroundImgRes = R.drawable.pop,
            items = getPopBands(),
        ),
        CardStyle1(
            cardName = "Hip Hop",
            backgroundImgRes = R.drawable.hip_hop,
            items = getHipHopBands(),
        ),
        CardStyle1(
            cardName = "Jazz",
            backgroundImgRes = R.drawable.jazz,
            items = getJazzBands(),
        ),
        CardStyle1(
            cardName = "Blues",
            backgroundImgRes = R.drawable.blues,
            items = getBluesBands(),
        ),
        CardStyle1(
            cardName = "Reggae",
            backgroundImgRes = R.drawable.reggae,
            items = getReggaeBands(),
        ),
        CardStyle1(
            cardName = "Electronic",
            backgroundImgRes = R.drawable.eletronic,
            items = getElectronicBands(),
        ),
    )
}