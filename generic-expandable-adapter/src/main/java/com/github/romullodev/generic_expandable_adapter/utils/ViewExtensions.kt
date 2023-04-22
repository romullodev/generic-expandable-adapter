package com.github.romullodev.generic_expandable_adapter.utils

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.romullodev.generic_expandable_adapter.CustomGenericExpandableAdapter
import com.github.romullodev.generic_expandable_adapter.DefaultGenericExpandableAdapter
import com.github.romullodev.generic_expandable_adapter.R
import com.github.romullodev.generic_expandable_adapter.base.ExpandableAdapterAnimation
import com.github.romullodev.generic_expandable_adapter.base.HeaderBindingCallback
import com.github.romullodev.generic_expandable_adapter.base.ItemBindingCallback
import com.github.romullodev.generic_expandable_adapter.entities.CardHeaderModel

fun View.visibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun RecyclerView.setupDefaultExpandableAdapter(
    dataHeaders: List<CardHeaderModel>,
    expandAllAtFirst: Boolean = false
) {
    dataHeaders.map {
        DefaultGenericExpandableAdapter(it, expandAllAtFirst)
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

fun RecyclerView.updateDefaultExpandableAdapterHeaderAt(position: Int, header: CardHeaderModel) {
    (adapter as ConcatAdapter).run {
        (adapters[position] as DefaultGenericExpandableAdapter).updateData(header)
        notifyItemChanged(position)
    }
}

fun <H, I> RecyclerView.setupCustomExpandableAdapter(
    dataHeaders: List<H>,
    getItemsCallback: (header: H) -> List<I>,
    itemBindingCallback: ItemBindingCallback<I, H>,
    headerBindingCallback: HeaderBindingCallback<H>,
    getExpandedIcImageView: (headerBinding: ViewDataBinding) -> ImageView?,
    headerLayout: Int,
    itemLayout: Int
) {
    dataHeaders.map {
        CustomGenericExpandableAdapter<H, I>(
            header = it,
            getItemsCallback = getItemsCallback,
            itemBindingCallback = itemBindingCallback,
            headerBindingCallback = headerBindingCallback,
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

fun ImageView.setupTintColor(colorRes: Int) {
    ImageViewCompat.setImageTintList(
        this,
        ColorStateList.valueOf(
            ContextCompat.getColor(context, colorRes)
        )
    )
}

fun CardView.setupShapeWithNoBackground(hasThickness: Boolean, thicknessColorRes: Int) {
    foreground = (ContextCompat.getDrawable(
        context,
        R.drawable.shape_no_background
    ) as GradientDrawable).run {
        setStroke(
            if (hasThickness) context.resources.getDimension(R.dimen.thickness).toInt() else 0,
            ContextCompat.getColor(context, thicknessColorRes)
        )
        this
    }
}

fun ConstraintLayout.setupShapeWithBackground(backgroundColorRes: Int?, hasThickness: Boolean, thicknessColorRes: Int) {
    background = (ContextCompat.getDrawable(
        context,
        R.drawable.shape_with_background
    ) as GradientDrawable).run {
        setColor(
            ContextCompat.getColor(context, backgroundColorRes ?: R.color.eerie_black)
        )
        setStroke(
            if (hasThickness) context.resources.getDimension(R.dimen.thickness).toInt() else 0,
            ContextCompat.getColor(context, thicknessColorRes)
        )
        this
    }
}


fun TextView.setupTextColor(colorRes: Int) {
    setTextColor(
        ContextCompat.getColor(context, colorRes)
    )
}