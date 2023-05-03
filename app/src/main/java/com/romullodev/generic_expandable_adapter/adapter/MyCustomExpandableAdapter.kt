package com.romullodev.generic_expandable_adapter.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.romullodev.generic_expandable_adapter.utils.MyCustomHeaderModel
import com.romullodev.generic_expandable_adapter.utils.MyCustomItemModel
import com.github.romullodev.generic_expandable_adapter.base.BaseExpandableAdapter
import com.github.romullodev.generic_expandable_adapter.base.HeaderBindingCallback
import com.github.romullodev.generic_expandable_adapter.base.ItemBindingCallback
import com.romullodev.generic_expandable_adapter.R
import com.romullodev.generic_expandable_adapter.databinding.MyCustomHeaderBinding
import com.romullodev.generic_expandable_adapter.databinding.MyCustomItemBinding

class MyCustomExpandableAdapter(
    header: MyCustomHeaderModel
): BaseExpandableAdapter<MyCustomHeaderModel, MyCustomItemModel>(
    data = header,
    headerLayoutRes = R.layout.my_custom_header,
    itemLayoutRes = R.layout.my_custom_item,
    expandAllAtFirst = false
) {
    override fun getItems(headerObject: MyCustomHeaderModel): List<MyCustomItemModel>  =
        headerObject.items

    override fun onItemBinding(): ItemBindingCallback<MyCustomItemModel, MyCustomHeaderModel> =
        { item, _, itemBinding ->
            (itemBinding as? MyCustomItemBinding)?.run {
                textViewCustomItemTitle.text = item.myCustomItemName
            }
        }

    override fun onHeaderBinding(): HeaderBindingCallback<MyCustomHeaderModel> =
        { header, headerBinding ->
            (headerBinding as? MyCustomHeaderBinding)?.run {
                textViewCustomTitle.text = header.myCustomHeaderName
            }
        }

    override fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView? =
        (headerBinding as? MyCustomHeaderBinding)?.imageViewArrowDown

    override fun getMainHeaderLayoutView(headerBinding: ViewDataBinding): View =
        (headerBinding as MyCustomHeaderBinding).cardViewHeaderContainer
}
