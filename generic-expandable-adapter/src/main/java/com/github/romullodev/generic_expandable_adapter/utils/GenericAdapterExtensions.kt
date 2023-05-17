package com.github.romullodev.generic_expandable_adapter.utils

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.romullodev.generic_expandable_adapter.DefaultGenericExpandableAdapter
import com.github.romullodev.generic_expandable_adapter.GenericExpandableAdapter
import com.github.romullodev.generic_expandable_adapter.R
import com.github.romullodev.generic_expandable_adapter.base.BaseExpandableAdapterAnimation
import com.github.romullodev.generic_expandable_adapter.base.OnBindingHeader
import com.github.romullodev.generic_expandable_adapter.base.OnBindingItem
import com.github.romullodev.generic_expandable_adapter.base.OnSwipeOption
import com.github.romullodev.generic_expandable_adapter.entities.*

fun RecyclerView.setupDefaultExpandableAdapter(
    dataHeaders: List<CardHeaderModel>,
    optionsOnHeader: List<DefaultSwipeOption> = emptyList(),
    optionsOnItem: List<DefaultSwipeOption> = emptyList(),
    layoutOptions: LayoutOptions = LayoutOptions.DEFAULT,
    onSwipeOption: OnSwipeOption = { _, _ -> },
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
                    radius = layoutOptions.radius,
                    hasThickness = it.cardHeaderStyle.hasThicknessOnHeader ?: layoutOptions.hasThicknessForAll,
                    thicknessColor = layoutOptions.thicknessColorForAll,
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
                    radius = layoutOptions.radius,
                    thicknessColor = layoutOptions.thicknessColorForAll
                )
            },
            layoutOptions = layoutOptions,
            onSwipeOption = onSwipeOption
        )
    }.let {
        ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build().run {
                ConcatAdapter(this, it).also {
                    adapter = it
                    itemAnimator = BaseExpandableAdapterAnimation()
                }
            }
    }
}

fun RecyclerView.addHeaderDefaultExpandableAdapter(
    newHeader: CardHeaderModel,
    onSwipeOption: OnSwipeOption = { _, _, -> },
    optionsOnHeader: List<GenericSwipeOption>? = null,
    optionsOnItem: List<GenericSwipeOption>? = null,
    layoutOptions: LayoutOptions = LayoutOptions.DEFAULT
) {
    (adapter as ConcatAdapter).run {
        adapters.size.let {
            addAdapter(
                DefaultGenericExpandableAdapter(
                    header = newHeader,
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
                    layoutOptions = layoutOptions
                )
            )
            notifyItemInserted(
                (it - 1).coerceAtLeast(0)
            )
        }
    }
}

fun RecyclerView.updateHeaderDefaultExpandableAdapterHeader(position: Int, header: CardHeaderModel) {
    (adapter as ConcatAdapter).run {
        (adapters[position] as DefaultGenericExpandableAdapter).updateHeader(header)
        notifyItemChanged(position)
    }
}

fun RecyclerView.updateHeaderDefaultExpandableAdapterHeader(header: CardHeaderModel?): Boolean =
    header?.let {
        (adapter as ConcatAdapter).run {
            adapters.find { (it as DefaultGenericExpandableAdapter).isEqualTo(header) }?.let {
                (it as DefaultGenericExpandableAdapter).updateHeader(header)
                true
            } ?: false
        }
    } ?: false

fun RecyclerView.removeHeaderDefaultExpandableAdapter(header: CardHeaderModel?): Boolean =
    header?.let { headerToDelete ->
        (adapter as ConcatAdapter).run {
            adapters.find { (it as DefaultGenericExpandableAdapter).isEqualTo(headerToDelete) }
                ?.let { removeAdapter(it) } ?: false
        }
    } ?: false


fun RecyclerView.updateItemDefaultExpandableAdapter(item: CardItemModel?): Boolean =
    item?.let {
        (adapter as ConcatAdapter).run {
            adapters.find { (it as DefaultGenericExpandableAdapter).updateItem(item) }
                ?.let { true } ?: false
        }
    } ?: false

fun RecyclerView.removeItemDefaultExpandableAdapter(item: CardItemModel?): Boolean =
    item?.let {
        (adapter as ConcatAdapter).run {
            adapters.find { (it as DefaultGenericExpandableAdapter).removeItem(item) }
                ?.let { true } ?: false
        }
    } ?: false


fun <H : BaseHeaderModel<H, I>, I : BaseItemModel> RecyclerView.setupGenericExpandableAdapter(
    onBindingHeader: OnBindingHeader<H>,
    onBindingItem: OnBindingItem<I, H>,
    getExpandedIcImageView: (headerBinding: ViewDataBinding) -> ImageView?,
    dataHeaders: List<H>,
    headerLayout: Int,
    itemLayout: Int,
    onSwipeOption: OnSwipeOption = { _, _ -> },
    optionsOnHeader: List<GenericSwipeOption> = emptyList(),
    optionsOnItem: List<GenericSwipeOption> = emptyList(),
    layoutOptions: LayoutOptions = LayoutOptions.DEFAULT,
    isolateViewTypes: Boolean = false
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
            layoutOptions = layoutOptions,
            optionsOnHeader = optionsOnHeader,
            optionsOnItem = optionsOnItem
        )
    }.let {
        ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(isolateViewTypes)
            .build().run {
                ConcatAdapter(this, it).also {
                    adapter = it
                    itemAnimator = BaseExpandableAdapterAnimation()
                }
            }
    }
}