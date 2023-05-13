package com.github.romullodev.generic_expandable_adapter.entities

data class CardItemModel(
    val id: Long,
    val itemTitle: String = String(),
    val cardItemStyle: CardItemStyle = CardItemStyle()
) : BaseItemModel {
    override fun getModelId(): Long = id
    override fun isEqualTo(model: Any): Boolean = this == (model as CardItemModel)
    override fun hasThickness(): Boolean? = cardItemStyle.hasThicknessOnItem
}
