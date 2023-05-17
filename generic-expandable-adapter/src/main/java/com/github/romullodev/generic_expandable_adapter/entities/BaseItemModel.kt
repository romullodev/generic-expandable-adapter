package com.github.romullodev.generic_expandable_adapter.entities

interface BaseItemModel: BaseGenericModel {
    override fun getModelId(): Long
    // TODO: forcar o generic no tipo do metodo
    override fun isEqualTo(model: Any): Boolean

    fun hasThickness(): Boolean?
}