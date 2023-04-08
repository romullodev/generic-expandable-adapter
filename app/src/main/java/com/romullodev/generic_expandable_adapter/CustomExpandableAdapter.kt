package com.romullodev.generic_expandable_adapter

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.romullodev.generic_expandable_adapter.databinding.CustomHeaderBinding
import com.romullodev.generic_expandable_adapter.databinding.CustomItemBinding
import com.romullodev.generic_expandable_adapter.utils.CustomHeaderModel
import com.romullodev.generic_expandable_adapter.utils.CustomItemModel
import com.romullodev.library.base.BaseExpandableAdapter
import com.romullodev.library.base.HeaderBindingCallback
import com.romullodev.library.base.ItemBindingCallback

class CustomExpandableAdapter(
    header: CustomHeaderModel
): BaseExpandableAdapter<CustomHeaderModel, CustomItemModel>(
    headerObject = header,
    headerLayoutRes = R.layout.custom_header,
    itemLayoutRes = R.layout.custom_item
) {
    override fun getItems(headerObject: CustomHeaderModel): List<CustomItemModel>  =
        headerObject.items

    override fun getItemBindingCallback(): ItemBindingCallback<CustomItemModel, CustomHeaderModel> =
        { item, _, itemBinding ->
            (itemBinding as? CustomItemBinding)?.run {
                textViewCustomItemTitle.text = item.customItemName
            }
        }

    override fun getHeaderBindingCallback(): HeaderBindingCallback<CustomHeaderModel> =
        { header, headerBinding ->
            (headerBinding as? CustomHeaderBinding)?.run {
                textViewCustomTitle.text = header.customHeaderName
            }
        }

    override fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView? =
        (headerBinding as? CustomHeaderBinding)?.imageViewArrowDown
}
