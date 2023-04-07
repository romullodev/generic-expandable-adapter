package com.romullodev.library.entities

data class CardHeaderModel(
    val cardName: String,
    val items: List<CardItemModel>,
    val cardHeaderStyle: CardHeaderStyle = CardHeaderStyle()
)