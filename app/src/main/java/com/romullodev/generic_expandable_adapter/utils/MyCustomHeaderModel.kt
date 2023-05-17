package com.romullodev.generic_expandable_adapter.utils

import com.github.romullodev.generic_expandable_adapter.entities.BaseHeaderModel

data class MyCustomHeaderModel(
    val myCustomHeaderId: Long,
    val myCustomHeaderName: String,
    val items: List<MyCustomItemModel>,
) : BaseHeaderModel<MyCustomHeaderModel, MyCustomItemModel> {
    override fun getCustomItems(): List<MyCustomItemModel> = items
    override fun getModelId(): Long = myCustomHeaderId
    override fun getHeaderWithUpdatedItems(newItems: List<MyCustomItemModel>): MyCustomHeaderModel =
        this.copy(items = newItems)

    override fun isEqualTo(model: Any): Boolean =
        this == (model as MyCustomHeaderModel)
}