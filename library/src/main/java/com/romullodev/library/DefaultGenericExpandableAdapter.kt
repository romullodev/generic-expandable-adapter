package com.romullodev.library

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.romullodev.library.base.BaseExpandableAdapter
import com.romullodev.library.base.HeaderBindingCallback
import com.romullodev.library.base.ItemBindingCallback
import com.romullodev.library.databinding.DefaultHeaderLayoutBinding
import com.romullodev.library.databinding.DefaultItemLayoutBinding
import com.romullodev.library.entities.DefaultDataHeader
import com.romullodev.library.entities.DefaultDataItem

class DefaultGenericExpandableAdapter(
    private val header: DefaultDataHeader
) : BaseExpandableAdapter<DefaultDataHeader, DefaultDataItem, Unit>(
    headerObject = header,
    headerLayoutRes = R.layout.default_header_layout,
    itemLayoutRes = R.layout.default_item_layout
) {
    override fun getItems(headerObject: DefaultDataHeader): List<DefaultDataItem> =
        headerObject.items

    override fun getItemBindingCallback(): ItemBindingCallback<DefaultDataItem, DefaultDataHeader> =
        { item, _, binding ->
            (binding as? DefaultItemLayoutBinding)?.run {
                textViewDefaultItemTitle.text = item.itemName
            }
        }

    override fun getHeaderBindingCallback(): HeaderBindingCallback<DefaultDataHeader, Unit> =
        { header, _, binding ->
            (binding as? DefaultHeaderLayoutBinding)?.run {
                textViewDefaultHeaderTitle.text = header.headerName
            }
        }

    override fun getLayoutParamsSetup() = Unit

    override fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView =
        (headerBinding as? DefaultHeaderLayoutBinding)?.run {
            imageViewDefaultArrowDown
        } ?: throw Exception("error on perform cast do header binding")
}