package com.romullodev.generic_expandable_adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.romullodev.generic_expandable_adapter.databinding.CustomHeaderBinding
import com.romullodev.generic_expandable_adapter.databinding.CustomItemBinding
import com.romullodev.generic_expandable_adapter.utils.CustomHeaderModel
import com.romullodev.generic_expandable_adapter.utils.CustomItemModel
import com.github.romullodev.generic_expandable_adapter.base.BaseExpandableAdapter
import com.github.romullodev.generic_expandable_adapter.base.HeaderBindingCallback
import com.github.romullodev.generic_expandable_adapter.base.ItemBindingCallback

class CustomExpandableAdapter(
    header: CustomHeaderModel
): BaseExpandableAdapter<CustomHeaderModel, CustomItemModel>(
    data = header,
    headerLayoutRes = R.layout.custom_header,
    itemLayoutRes = R.layout.custom_item,
    expandAllAtFirst = false
) {
    override fun getItems(headerObject: CustomHeaderModel): List<CustomItemModel>  =
        headerObject.items

    override fun onItemBinding(): ItemBindingCallback<CustomItemModel, CustomHeaderModel> =
        { item, _, itemBinding ->
            (itemBinding as? CustomItemBinding)?.run {
                textViewCustomItemTitle.text = item.customItemName
            }
        }

    override fun onHeaderBinding(): HeaderBindingCallback<CustomHeaderModel> =
        { header, headerBinding ->
            (headerBinding as? CustomHeaderBinding)?.run {
                textViewCustomTitle.text = header.customHeaderName
            }
        }

    override fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView? =
        (headerBinding as? CustomHeaderBinding)?.imageViewArrowDown

    override fun getMainHeaderLayoutView(headerBinding: ViewDataBinding): View =
        (headerBinding as CustomHeaderBinding).cardViewHeaderContainer
}
