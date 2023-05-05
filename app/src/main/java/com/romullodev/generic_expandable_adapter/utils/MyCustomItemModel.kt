package com.romullodev.generic_expandable_adapter.utils

import com.github.romullodev.generic_expandable_adapter.entities.BaseItemCustomModel

data class MyCustomItemModel(
    val myCustomItemId: Long,
    val myCustomItemName: String
) : BaseItemCustomModel {
    override fun getItemId(): Long = myCustomItemId
}
