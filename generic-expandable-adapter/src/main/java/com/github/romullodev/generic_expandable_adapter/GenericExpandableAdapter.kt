package com.github.romullodev.generic_expandable_adapter

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.github.romullodev.generic_expandable_adapter.base.*
import com.github.romullodev.generic_expandable_adapter.entities.BaseHeaderModel
import com.github.romullodev.generic_expandable_adapter.entities.BaseItemModel
import com.github.romullodev.generic_expandable_adapter.entities.GenericSwipeOption

class GenericExpandableAdapter<AdapterH: BaseHeaderModel<AdapterH, AdapterI> , AdapterI: BaseItemModel>(
    private val onBindingItem: OnBindingItem<AdapterI, AdapterH>,
    private val onBindingHeader: OnBindingHeader<AdapterH>,
    private val getExpandedIcImageView: (headerBinding: ViewDataBinding) -> ImageView?,
    private val onSwipeOption: OnSwipeOption,
    optionsOnHeader: List<GenericSwipeOption>,
    optionsOnItem: List<GenericSwipeOption>,
    header: AdapterH,
    private val headerLayout: Int,
    private val itemLayout: Int,
    expandAllAtFirst: Boolean = false
) : BaseExpandableAdapter<AdapterH, AdapterI>(
    data = header,
    optionsOnHeader = optionsOnHeader,
    optionsOnItem = optionsOnItem,
    expandAllAtFirst
) {
    override val headerLayoutRes: Int get() = headerLayout
    override val itemLayoutRes: Int get() = itemLayout

    override fun onBindingItem(): OnBindingItem<AdapterI, AdapterH> = onBindingItem

    override fun onBindingHeader(): OnBindingHeader<AdapterH> = onBindingHeader

    override fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView? =
        getExpandedIcImageView.invoke(headerBinding)

    override fun onSwipeOption(): OnSwipeOption = onSwipeOption
}