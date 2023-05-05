package com.romullodev.generic_expandable_adapter.utils

import com.github.romullodev.generic_expandable_adapter.entities.BaseHeaderCustomModel
import com.github.romullodev.generic_expandable_adapter.entities.CustomSwipeOption

data class MyCustomHeaderModel(
    val myCustomHeaderId: Long,
    val myCustomHeaderName: String,
    val items: List<MyCustomItemModel>,
    val myCustomSwipeOptionsOnHeader: List<CustomSwipeOption<MyCustomHeaderModel>>? = null,
    val myCustomSwipeOptionsOnItem: List<CustomSwipeOption<MyCustomItemModel>>? = null
) : BaseHeaderCustomModel<MyCustomHeaderModel, MyCustomItemModel> {
    override fun getCustomHeaderSwipeOptions(): List<CustomSwipeOption<MyCustomHeaderModel>>? =
        myCustomSwipeOptionsOnHeader

    override fun getCustomItemSwipeOptions(): List<CustomSwipeOption<MyCustomItemModel>>? =
        myCustomSwipeOptionsOnItem

    override fun getCustomItems(): List<MyCustomItemModel> = items
    override fun getHeaderId(): Long = myCustomHeaderId
}