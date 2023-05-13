package com.romullodev.generic_expandable_adapter.utils

import com.github.romullodev.generic_expandable_adapter.entities.BaseItemModel

data class MyCustomItemModel(
    val myCustomItemId: Long,
    val myCustomItemName: String
) : BaseItemModel {
    override fun getModelId(): Long = myCustomItemId
    override fun isEqualTo(model: Any): Boolean = this == (model as MyCustomItemModel)
    override fun hasThickness(): Boolean? = null
}
