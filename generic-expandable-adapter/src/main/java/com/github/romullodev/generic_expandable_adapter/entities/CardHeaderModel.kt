package com.github.romullodev.generic_expandable_adapter.entities

data class CardHeaderModel(
    val id: Long,
    val headerTitle: String = String(),
    val headerSubtitle: String = String(),
    val items: List<CardItemModel>,
    val cardHeaderStyle: CardHeaderStyle = CardHeaderStyle(),
    val swipeOptionsOnHeader: List<SwipeOption<CardHeaderModel>>? = null,
    val swipeOptionsOnItem: List<SwipeOption<CardItemModel>>? = null
)