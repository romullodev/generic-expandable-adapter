package com.github.romullodev.generic_expandable_adapter.entities

data class CardHeaderModel(
    val headerTitle: String = String(),
    val headerSubtitle: String = String(),
    val items: List<CardItemModel>,
    val cardHeaderStyle: CardHeaderStyle = CardHeaderStyle()
)