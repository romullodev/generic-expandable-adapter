package com.romullodev.library

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.romullodev.library.base.BaseExpandableAdapter
import com.romullodev.library.base.HeaderBindingCallback
import com.romullodev.library.base.ItemBindingCallback

class CustomGenericExpandableAdapter<H, I, P>(
    header: H,
    private val getItemsCallback: (header: H) -> List<I>,
    private val itemBindingCallback: ItemBindingCallback<I, H>,
    private val headerBindingCallback: HeaderBindingCallback<H, P>,
    private val getLayoutParamsSetup: P,
    private val getExpandedIcImageView: (headerBinding: ViewDataBinding) -> ImageView?,
    headerLayout: Int,
    itemLayout: Int
) : BaseExpandableAdapter<H, I, P>(
    headerObject = header,
    headerLayoutRes = headerLayout,
    itemLayoutRes = itemLayout
) {
    override fun getItems(headerObject: H): List<I> = getItemsCallback.invoke(headerObject)

    override fun getItemBindingCallback(): ItemBindingCallback<I, H> = itemBindingCallback

    override fun getHeaderBindingCallback(): HeaderBindingCallback<H, P> = headerBindingCallback

    override fun getLayoutParamsSetup(): P = getLayoutParamsSetup

    override fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView? =
        getExpandedIcImageView.invoke(headerBinding)
}