package com.romullodev.generic_expandable_adapter.utils

import com.github.romullodev.generic_expandable_adapter.entities.BaseItemModel

data class MyCustomItemModel(
    val myCustomItemId: Long,
    val myCustomItemName: String
) : BaseItemModel {
    override fun getItemId(): Long = myCustomItemId
}
