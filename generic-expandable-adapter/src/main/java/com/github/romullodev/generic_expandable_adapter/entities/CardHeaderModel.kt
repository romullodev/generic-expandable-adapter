package com.github.romullodev.generic_expandable_adapter.entities

data class CardHeaderModel(
    val cardName: String,
    val items: List<CardItemModel>,
    val cardHeaderStyle: CardHeaderStyle = CardHeaderStyle()
)