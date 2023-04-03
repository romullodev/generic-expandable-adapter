package com.romullodev.library.entities

data class CardHeaderModel(
    val cardName: String,
    val items: List<CardItemModel>,
    val backgroundImgRes: Int? = null,
    val backgroundColorRes: Int? = null,
    val backgroundColorItems: Int? = null
)