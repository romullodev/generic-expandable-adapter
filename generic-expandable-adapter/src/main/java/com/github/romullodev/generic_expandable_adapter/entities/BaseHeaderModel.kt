package com.github.romullodev.generic_expandable_adapter.entities

interface BaseHeaderModel<H, I>: BaseGenericModel {
    fun getCustomItems(): List<I>
    override fun getModelId(): Long
    override fun isEqualTo(model: Any): Boolean
    fun getHeaderWithUpdatedItems(newItems: List<I>): H
}