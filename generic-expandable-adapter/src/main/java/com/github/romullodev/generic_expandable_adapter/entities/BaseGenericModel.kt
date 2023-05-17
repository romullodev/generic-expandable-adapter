package com.github.romullodev.generic_expandable_adapter.entities

interface BaseGenericModel {
    fun getModelId(): Long
    fun isEqualTo(model: Any): Boolean
}