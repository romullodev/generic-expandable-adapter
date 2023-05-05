package com.github.romullodev.generic_expandable_adapter.entities

interface BaseHeaderCustomModel<H, I> {
    fun getCustomItems(): List<I>
    fun getHeaderId(): Long
}