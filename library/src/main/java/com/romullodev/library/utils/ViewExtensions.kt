package com.romullodev.library.utils

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.romullodev.library.CustomGenericExpandableAdapter
import com.romullodev.library.DefaultGenericExpandableAdapter
import com.romullodev.library.R
import com.romullodev.library.base.ExpandableAdapterAnimation
import com.romullodev.library.base.HeaderBindingCallback
import com.romullodev.library.base.ItemBindingCallback
import com.romullodev.library.entities.CardHeaderModel

fun View.visibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun RecyclerView.setupDefaultExpandableAdapter(
    dataHeaders: List<CardHeaderModel>,
    hasThickness: Boolean = true
) {
    dataHeaders.map {
        DefaultGenericExpandableAdapter(it, hasThickness)
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

fun ImageView.setupImage(drawableImgRes: Int?) {
    drawableImgRes?.let {
        background = ContextCompat.getDrawable(context, it)
    }
}

fun View.setupBackgroundColor(colorRes: Int?, hasThickness: Boolean) {
    background = (ContextCompat.getDrawable(context,
        if (hasThickness)
            R.drawable.shape_thickness_with_background
        else
            R.drawable.shape_no_thickness_with_background
    ) as GradientDrawable).run {
        setColor(
            ContextCompat.getColor(context, colorRes ?: R.color.eerie_black)
        )
        this
    }
}

fun CardView.setupShapeOnHeader(hasThickness: Boolean) {
    if (hasThickness)
        foreground = ContextCompat.getDrawable(context, R.drawable.shape_thickness_no_background)
    else
        background = ContextCompat.getDrawable(context, R.drawable.shape_no_thickness_no_background)
}

fun CardView.setupShapeOnItem(hasThickness: Boolean) {
    background = ContextCompat.getDrawable(
        context,
        if (hasThickness)
            R.drawable.shape_thickness_with_background
        else
            R.drawable.shape_no_thickness_with_background
    )
}