package com.github.romullodev.generic_expandable_adapter.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.romullodev.generic_expandable_adapter.entities.CardHeaderModel
import com.github.romullodev.generic_expandable_adapter.utils.addNewHeaderModel
import com.github.romullodev.generic_expandable_adapter.utils.updateDefaultExpandableAdapterHeaderAt

fun RecyclerView.updateMeasure() {
    val screenSize = 2000
    measure(
        View.MeasureSpec.UNSPECIFIED,
        View.MeasureSpec.UNSPECIFIED
    )
    layout(0, 0, screenSize, screenSize)
}

fun RecyclerView.updateHeaderAt(position: Int, data: CardHeaderModel) {
    updateDefaultExpandableAdapterHeaderAt(position, data)
    updateMeasure()
}

fun RecyclerView.addHeader(data: CardHeaderModel) {
    addNewHeaderModel(data, true)
    updateMeasure()
}