package com.romullodev.library

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.romullodev.library.base.BaseExpandableAdapter
import com.romullodev.library.base.HeaderBindingCallback
import com.romullodev.library.base.ItemBindingCallback

class CustomGenericExpandableAdapter<H, I>(
    header: H,
    private val getItemsCallback: (header: H) -> List<I>,
    private val itemBindingCallback: ItemBindingCallback<I, H>,
    private val headerBindingCallback: HeaderBindingCallback<H>,
    private val getExpandedIcImageView: (headerBinding: ViewDataBinding) -> ImageView?,
    headerLayout: Int,
    itemLayout: Int
) : BaseExpandableAdapter<H, I>(
    headerObject = header,
    headerLayoutRes = headerLayout,
    itemLayoutRes = itemLayout
) {
    override fun getItems(headerObject: H): List<I> = getItemsCallback.invoke(headerObject)

    override fun getItemBindingCallback(): ItemBindingCallback<I, H> = itemBindingCallback

    override fun getHeaderBindingCallback(): HeaderBindingCallback<H> = headerBindingCallback

    override fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView? =
        getExpandedIcImageView.invoke(headerBinding)
}