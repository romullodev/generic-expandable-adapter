package com.github.romullodev.generic_expandable_adapter.entities

data class CardHeaderModel(
    val id: Long,
    val headerTitle: String = String(),
    val headerSubtitle: String = String(),
    val items: List<CardItemModel>,
    val cardHeaderStyle: CardHeaderStyle = CardHeaderStyle(),
) : BaseHeaderModel<CardHeaderModel, CardItemModel> {

    override fun getCustomItems(): List<CardItemModel> = items

    override fun getModelId(): Long = id
    override fun getHeaderWithUpdatedItems(newItems: List<CardItemModel>): CardHeaderModel =
        this.copy(items = newItems)

    override fun isEqualTo(model: Any): Boolean = this == (model as CardHeaderModel)
}