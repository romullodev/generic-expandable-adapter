package com.romullodev.library.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.romullodev.library.CustomGenericExpandableAdapter
import com.romullodev.library.DefaultGenericExpandableAdapter
import com.romullodev.library.base.ExpandableAdapterAnimation
import com.romullodev.library.base.HeaderBindingCallback
import com.romullodev.library.base.ItemBindingCallback
import com.romullodev.library.entities.DefaultDataHeader

fun View.visibleOrGone(isVisible: Boolean) {
    visibility = if(isVisible) View.VISIBLE else View.GONE
}

fun RecyclerView.setupDefaultExpandableAdapter(dataHeaders: List<DefaultDataHeader>) {
    dataHeaders.map {
        DefaultGenericExpandableAdapter(it)
    }.let {
        ConcatAdapter.Config.Builder()
            .build().run {
                ConcatAdapter(this, it).also {
                    adapter = it
                    itemAnimator = ExpandableAdapterAnimation()
                }
            }
    }
}

fun <H, I, P> RecyclerView.setupCustomExpandableAdapter(
    dataHeaders: List<H>,
    getItemsCallback: (header: H) -> List<I>,
    itemBindingCallback: ItemBindingCallback<I, H>,
    headerBindingCallback: HeaderBindingCallback<H, P>,
    getLayoutParamsSetup: P,
    getExpandedIcImageView: (headerBinding: ViewDataBinding) -> ImageView?,
    headerLayout: Int,
    itemLayout: Int
) {
    dataHeaders.map {
        CustomGenericExpandableAdapter<H, I, P>(
            header = it,
            getItemsCallback = getItemsCallback,
            itemBindingCallback = itemBindingCallback,
            headerBindingCallback = headerBindingCallback,
            getLayoutParamsSetup = getLayoutParamsSetup,
            getExpandedIcImageView = getExpandedIcImageView,
            headerLayout = headerLayout,
            itemLayout = itemLayout
        )
    }.let {
        ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build().run {
                ConcatAdapter(this, it).also {
                    adapter = it
                    itemAnimator = ExpandableAdapterAnimation()
                }
            }
    }
}