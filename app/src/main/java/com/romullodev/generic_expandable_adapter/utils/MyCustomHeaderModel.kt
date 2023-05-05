package com.romullodev.generic_expandable_adapter.utils

import com.github.romullodev.generic_expandable_adapter.entities.BaseHeaderCustomModel
import com.github.romullodev.generic_expandable_adapter.entities.CustomSwipeOption

data class MyCustomHeaderModel(
    val myCustomHeaderId: Long,
    val myCustomHeaderName: String,
    val items: List<MyCustomItemModel>,
) : BaseHeaderCustomModel<MyCustomHeaderModel, MyCustomItemModel> {
    override fun getCustomItems(): List<MyCustomItemModel> = items
    override fun getHeaderId(): Long = myCustomHeaderId
}