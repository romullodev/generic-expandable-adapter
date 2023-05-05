package com.github.romullodev.generic_expandable_adapter.utils

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
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

//fun RecyclerView.setupDefaultExpandableAdapter(
//    dataHeaders: List<CardHeaderModel>,
//    expandAllAtFirst: Boolean = false,
//    onSwipeOptionSelected: (optionId: Int, CardHeaderModel?, CardItemModel?) -> Unit = { _, _, _ -> }
//) {
//    dataHeaders.map {
//        DefaultGenericExpandableAdapter(
//            header = it,
//            expandAllAtFirst = expandAllAtFirst,
//            onSwipeOptionListener = onSwipeOptionSelected
//        )
//    }.let {
//        ConcatAdapter.Config.Builder()
//            .setIsolateViewTypes(true)
//            .build().run {
//                ConcatAdapter(this, it).also {
//                    adapter = it
//                    itemAnimator = GenericExpandableAdapterAnimation()
//                }
//            }
//    }
//}

fun RecyclerView.setupDefaultExpandableAdapterV2(
    dataHeaders: List<CardHeaderModel>,
    customSwipeOptionsOnHeader: List<SwipeOptionDefault<CardHeaderModel>> = emptyList(),
    customSwipeOptionsOnItem: List<SwipeOptionDefault<CardItemModel>> = emptyList(),
    expandAllAtFirst: Boolean = false,
    layoutStyle: LayoutStyle = LayoutStyle(),
    onSwipeOption: (optionId: Int, CardHeaderModel?, CardItemModel?) -> Unit = { _, _, _ -> },
) {
    dataHeaders.map {
        DefaultGenericExpandableAdapterV2(
            header = it,
            customSwipeOptionsOnHeader = customSwipeOptionsOnHeader.map { defaultOption ->
                CustomSwipeOption(
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
            customSwipeOptionsOnItem = customSwipeOptionsOnItem.map { defaultOption ->
                CustomSwipeOption<CardItemModel>(
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
            onCustomSwipeOption = onSwipeOption,
            layoutStyle = layoutStyle
        )
    }.let {
        ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(true)
            .build().run {
                ConcatAdapter(this, it).also {
                    adapter = it
                    itemAnimator = CustomExpandableAdapterAnimation()
                }
            }
    }
}

//fun RecyclerView.updateDefaultExpandableAdapterHeaderAt(position: Int, header: CardHeaderModel) {
//    (adapter as ConcatAdapter).run {
//        (adapters[position] as DefaultGenericExpandableAdapter).updateData(header)
//        notifyItemChanged(position)
//    }
//}

//fun RecyclerView.addNewHeaderModel(
//    newHeader: CardHeaderModel,
//    expandAllAtFirst: Boolean = false,
//    onSwipeOptionListener: (optionId: Int, CardHeaderModel?, CardItemModel?) -> Unit = { _, _, _ -> }
//) {
//    (adapter as ConcatAdapter).run {
//        adapters.size.let {
//            addAdapter(
//                DefaultGenericExpandableAdapter(
//                    header = newHeader,
//                    expandAllAtFirst = expandAllAtFirst,
//                    onSwipeOptionListener =
//                    if (it == 0)
//                        onSwipeOptionListener
//                    else
//                        (adapters.first() as DefaultGenericExpandableAdapter).getOnSwipeOptionListener()
//                )
//            )
//            notifyItemInserted(
//                (it - 1).coerceAtLeast(0)
//            )
//        }
//    }
//}

fun <H, I> RecyclerView.setupCustomExpandableAdapter(
    dataHeaders: List<H>,
    getItemsCallback: (header: H) -> List<I>,
    itemBindingCallback: ItemBindingCallback<I, H>,
    headerBindingCallback: HeaderBindingCallback<H>,
    getExpandedIcImageView: (headerBinding: ViewDataBinding) -> ImageView?,
    getMainHeaderLayoutView: (headerBinding: ViewDataBinding) -> View,
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
            itemLayout = itemLayout,
            mainHeaderLayoutView = getMainHeaderLayoutView
        )
    }.let {
        ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(true)
            .build().run {
                ConcatAdapter(this, it).also {
                    adapter = it
                    itemAnimator = GenericExpandableAdapterAnimation()
                }
            }
    }
}

fun <H : BaseHeaderCustomModel<H, I>, I : BaseItemCustomModel> RecyclerView.setupCustomExpandableAdapterV2(
    onBindingHeader: OnBindingHeaderCustom<H>,
    onBindingItem: OnBindingItemCustom<I, H>,
    getExpandedIcImageView: (headerBinding: ViewDataBinding) -> ImageView?,
    dataHeaders: List<H>,
    headerLayout: Int,
    itemLayout: Int,
    onCustomSwipeOption: OnCustomSwipeOption<H, I> = { _, _, _ -> },
    customSwipeOptionsOnHeader: List<CustomSwipeOption<H>> = emptyList(),
    customSwipeOptionsOnItem: List<CustomSwipeOption<I>> = emptyList(),
    expandAllAtFirst: Boolean = false,
) {
    dataHeaders.map {
        CustomGenericExpandableAdapterV2(
            onBindingItem = onBindingItem,
            onBindingHeader = onBindingHeader,
            getExpandedIcImageView = getExpandedIcImageView,
            onCustomSwipeOption = onCustomSwipeOption,
            header = it,
            headerLayout = headerLayout,
            itemLayout = itemLayout,
            expandAllAtFirst = expandAllAtFirst,
            customSwipeOptionsOnHeader = customSwipeOptionsOnHeader,
            customSwipeOptionsOnItem = customSwipeOptionsOnItem
        )
    }.let {
        ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(true)
            .build().run {
                ConcatAdapter(this, it).also {
                    adapter = it
                    itemAnimator = CustomExpandableAdapterAnimation()
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