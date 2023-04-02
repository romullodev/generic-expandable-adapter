package com.romullodev.library.entities

data class CardStyle1(
    val cardName: String,
    val backgroundImgRes: Int? = null,
    val colorRes: Int? = null,
    val items: List<CardItemStyle1>
)