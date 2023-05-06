package com.github.romullodev.generic_expandable_adapter.utils

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.romullodev.generic_expandable_adapter.*
import com.github.romullodev.generic_expandable_adapter.base.*
import com.github.romullodev.generic_expandable_adapter.entities.*

fun View.visibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun RecyclerView.setupDefaultExpandableAdapter(
    dataHeaders: List<CardHeaderModel>,
    optionsOnHeader: List<DefaultSwipeOption> = emptyList(),
    optionsOnItem: List<DefaultSwipeOption> = emptyList(),
    expandAllAtFirst: Boolean = false,
    layoutStyle: LayoutStyle = LayoutStyle.DEFAULT,
    onSwipeOption: (optionId: Int, CardHeaderModel?, CardItemModel?) -> Unit = { _, _, _ -> },
) {
    dataHeaders.map {
        DefaultGenericExpandableAdapter(
            header = it,
            optionsOnHeader = optionsOnHeader.map { defaultOption ->
                GenericSwipeOption(
                    icon = defaultOption.icon,
                    iconColor = defaultOption.iconColor,
                    backgroundColor = defaultOption.backgroundColor,
                    optionId = defaultOption.optionId,
                    width = defaultOption.width,
                    height = R.dimen.default_header_height,
                    radius = layoutStyle.radius,
                    hasThickness = it.cardHeaderStyle.hasThickness,
                    thicknessColor = it.cardHeaderStyle.thicknessColor,
                )
            },
            optionsOnItem = optionsOnItem.map { defaultOption ->
                GenericSwipeOption(
                    icon = defaultOption.icon,
                    iconColor = defaultOption.iconColor,
                    backgroundColor = defaultOption.backgroundColor,
                    optionId = defaultOption.optionId,
                    width = defaultOption.width,
                    height = R.dimen.default_item_height,
                    radius = layoutStyle.radius
                ).run {
                    if (it.items.isNotEmpty()) {
                        copy(
                            hasThickness = it.items.first().cardItemStyle.hasThickness,
                            thicknessColor = it.items.first().cardItemStyle.thicknessColor,
                        )
                    } else
                        this
                }
            },
            expandAllAtFirst = expandAllAtFirst,
            onSwipeOption = onSwipeOption,
            layoutStyle = layoutStyle
        )
    }.let {
        ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(true)
            .build().run {
                ConcatAdapter(this, it).also {
                    adapter = it
                    itemAnimator = BaseExpandableAdapterAnimation()
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

fun RecyclerView.addNewHeaderModel(
    newHeader: CardHeaderModel,
    expandAllAtFirst: Boolean = false,
    onSwipeOption: OnSwipeOption<CardHeaderModel, CardItemModel> = { _, _, _ -> },
    optionsOnHeader: List<GenericSwipeOption>? = null,
    optionsOnItem: List<GenericSwipeOption>? = null,
    layoutStyle: LayoutStyle = LayoutStyle.DEFAULT
) {
    (adapter as ConcatAdapter).run {
        adapters.size.let {
            addAdapter(
                DefaultGenericExpandableAdapter(
                    header = newHeader,
                    expandAllAtFirst = expandAllAtFirst,
                    onSwipeOption =
                    if (it == 0)
                        onSwipeOption
                    else
                        (adapters.first() as DefaultGenericExpandableAdapter).getOnCustomSwipeOption(),
                    optionsOnHeader = optionsOnHeader
                        ?: (adapters.firstOrNull()
                            ?.let { adapter -> (adapter as DefaultGenericExpandableAdapter).getOptionsOnHeader() })
                        ?: emptyList(),
                    optionsOnItem = optionsOnItem
                        ?: (adapters.firstOrNull()
                            ?.let { adapter -> (adapter as DefaultGenericExpandableAdapter).getOptionsOnItem() })
                        ?: emptyList(),
                    layoutStyle = layoutStyle
                )
            )
            notifyItemInserted(
                (it - 1).coerceAtLeast(0)
            )
        }
    }
}

fun <H : BaseHeaderModel<H, I>, I : BaseItemModel> RecyclerView.setupGenericExpandableAdapter(
    onBindingHeader: OnBindingHeader<H>,
    onBindingItem: OnBindingItem<I, H>,
    getExpandedIcImageView: (headerBinding: ViewDataBinding) -> ImageView?,
    dataHeaders: List<H>,
    headerLayout: Int,
    itemLayout: Int,
    onSwipeOption: OnSwipeOption<H, I> = { _, _, _ -> },
    optionsOnHeader: List<GenericSwipeOption> = emptyList(),
    optionsOnItem: List<GenericSwipeOption> = emptyList(),
    expandAllAtFirst: Boolean = false,
) {
    dataHeaders.map {
        GenericExpandableAdapter(
            onBindingItem = onBindingItem,
            onBindingHeader = onBindingHeader,
            getExpandedIcImageView = getExpandedIcImageView,
            onSwipeOption = onSwipeOption,
            header = it,
            headerLayout = headerLayout,
            itemLayout = itemLayout,
            expandAllAtFirst = expandAllAtFirst,
            optionsOnHeader = optionsOnHeader,
            optionsOnItem = optionsOnItem
        )
    }.let {
        ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(true)
            .build().run {
                ConcatAdapter(this, it).also {
                    adapter = it
                    itemAnimator = BaseExpandableAdapterAnimation()
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

fun CardView.setupShapeWithNoBackground(
    hasThickness: Boolean,
    thicknessColorRes: Int,
    @DimenRes radiusDimenRes: Int
) {
    setupCardRadius(radiusDimenRes)
    foreground = (ContextCompat.getDrawable(
        context,
        R.drawable.shape_no_background
    ) as GradientDrawable).run {
        cornerRadius = context.resources.getDimension(radiusDimenRes)
        setStroke(
            if (hasThickness) context.resources.getDimension(R.dimen.thickness).toInt() else 0,
            ContextCompat.getColor(context, thicknessColorRes)
        )
        this
    }
}

fun CardView.setupCardRadius(@DimenRes radiusDimenRes: Int) {
    radius = context.resources.getDimension(radiusDimenRes)
}

fun View.setupShapeWithBackground(
    backgroundColorRes: Int?,
    hasThickness: Boolean,
    thicknessColorRes: Int,
    @DimenRes radiusDimenRes: Int
) {
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
        cornerRadius = context.resources.getDimension(radiusDimenRes)
        this
    }
}

fun TextView.setupTextColor(colorRes: Int) {
    setTextColor(
        ContextCompat.getColor(context, colorRes)
    )
}