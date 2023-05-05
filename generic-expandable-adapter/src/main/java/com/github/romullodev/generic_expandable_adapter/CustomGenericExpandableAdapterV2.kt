package com.github.romullodev.generic_expandable_adapter

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.github.romullodev.generic_expandable_adapter.base.*
import com.github.romullodev.generic_expandable_adapter.entities.BaseHeaderCustomModel
import com.github.romullodev.generic_expandable_adapter.entities.BaseItemCustomModel

class CustomGenericExpandableAdapterV2<AdapterH: BaseHeaderCustomModel<AdapterH, AdapterI> , AdapterI: BaseItemCustomModel>(
    private val onBindingItem: OnBindingItemCustom<AdapterI, AdapterH>,
    private val onBindingHeader: HeaderBindingCallback<AdapterH>,
    private val getExpandedIcImageView: (headerBinding: ViewDataBinding) -> ImageView?,
    private val onCustomSwipeOption: OnCustomSwipeOption<AdapterH, AdapterI>,
    header: AdapterH,
    headerLayout: Int,
    itemLayout: Int,
    expandAllAtFirst: Boolean = false
) : BaseCustomExpandableAdapter<AdapterH, AdapterI>(
    data = header,
    headerLayoutRes = headerLayout,
    itemLayoutRes = itemLayout,
    expandAllAtFirst
) {
    override fun onBindingItem(): OnBindingItemCustom<AdapterI, AdapterH> = onBindingItem

    override fun onBindingHeader(): OnBindingHeaderCustom<AdapterH> = onBindingHeader

    override fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView? =
        getExpandedIcImageView.invoke(headerBinding)

    override fun onCustomSwipeOption(): OnCustomSwipeOption<AdapterH, AdapterI> = onCustomSwipeOption
}