package com.romullodev.library.utils

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
    dataHeaders: List<CardHeaderModel>
) {
    dataHeaders.map {
        DefaultGenericExpandableAdapter(it)
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

//fun CardView.setupShapeWithNoBackground(hasThickness: Boolean, thicknessColorRes: Int) {
//    (ContextCompat.getDrawable(
//        context,
//        R.drawable.shape_no_background
//    ) as GradientDrawable).run {
//        setStroke(
//            if (hasThickness) context.resources.getDimension(R.dimen.thickness).toInt() else 0,
//            ContextCompat.getColor(context, thicknessColorRes)
//        )
//        this
//    }.also {
//        if (hasThickness)
//            foreground = it
//        else
//            background = it
//    }
//}

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