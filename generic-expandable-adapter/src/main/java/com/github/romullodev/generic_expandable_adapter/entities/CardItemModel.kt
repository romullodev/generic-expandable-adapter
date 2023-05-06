package com.github.romullodev.generic_expandable_adapter.entities

data class CardItemModel(
    val id: Long,
    val itemTitle: String = String(),
    val cardItemStyle: CardItemStyle = CardItemStyle()
) : BaseItemModel {
    override fun getItemId(): Long = id
}
