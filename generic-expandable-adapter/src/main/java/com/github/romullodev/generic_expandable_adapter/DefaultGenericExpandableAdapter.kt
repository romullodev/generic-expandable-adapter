package com.github.romullodev.generic_expandable_adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.apachat.swipereveallayout.core.ViewBinder
import com.github.romullodev.generic_expandable_adapter.base.*
import com.github.romullodev.generic_expandable_adapter.databinding.HeaderCardStyle1Binding
import com.github.romullodev.generic_expandable_adapter.databinding.ItemCardStyle1Binding
import com.github.romullodev.generic_expandable_adapter.entities.*
import com.github.romullodev.generic_expandable_adapter.utils.*

class DefaultGenericExpandableAdapter(
    header: CardHeaderModel,
    expandAllAtFirst: Boolean = false,
    private val onSwipeOptionSelected: (optionId: Int, CardHeaderModel?, CardItemModel?) -> Unit
) : BaseExpandableAdapter<CardHeaderModel, CardItemModel>(
    data = header,
    headerLayoutRes = R.layout.header_card_style_1,
    itemLayoutRes = R.layout.item_card_style_1,
    expandAllAtFirst = expandAllAtFirst
) {

    private val viewBinderHelper: ViewBinder = ViewBinder()

    override fun getItems(headerObject: CardHeaderModel): List<CardItemModel> = headerObject.items

    override fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView =
        (headerBinding as? HeaderCardStyle1Binding)?.run {
            imageViewArrowDown
        } ?: throw Exception("error on perform cast do header binding")

    override fun getMainHeaderLayoutView(headerBinding: ViewDataBinding): View =
        (headerBinding as HeaderCardStyle1Binding).cardViewHeaderCardContainer

    override fun onHeaderBindViewHolder(): HeaderBindViewHolderCallback<CardHeaderModel> =
        { headerBinding, header -> bindSwipeLayoutOnHeader(headerBinding, header) }

    private fun bindSwipeLayoutOnHeader(headerBinding: ViewDataBinding, header: CardHeaderModel) {
        header.swipeOptionsOnHeader?.let {
            viewBinderHelper.bind(
                (headerBinding as HeaderCardStyle1Binding).swipeRevealLayoutContainer,
                header.id.toString()
            )
        }
    }

    override fun onItemBindViewHolder(): ItemBindViewHolderCallback<CardItemModel, CardHeaderModel> =
        { itemBinding, item, header -> bindSwipeLayoutOnItem(itemBinding, item, header) }

    private fun bindSwipeLayoutOnItem(
        itemBinding: ViewDataBinding,
        item: CardItemModel,
        header: CardHeaderModel
    ) {
        header.swipeOptionsOnItem?.let {
            viewBinderHelper.bind(
                (itemBinding as ItemCardStyle1Binding).swipeRevealLayoutContainer,
                item.id.toString()
            )
        }
    }

    override fun onHeaderBinding(): HeaderBindingCallback<CardHeaderModel> =
        { header, binding ->
            (binding as? HeaderCardStyle1Binding)?.run {
                textViewCardStyle1Title.text = header.headerTitle
                textViewCardStyle1Subtitle.text = header.headerSubtitle
                setupHeaderStyle(binding, header.cardHeaderStyle)
                setupSwipeLayout(header)
            }
        }

    private fun setupHeaderStyle(
        binding: HeaderCardStyle1Binding,
        cardHeaderStyle: CardHeaderStyle
    ) {
        binding.run {
            textViewCardStyle1Title.setupTextColor(cardHeaderStyle.titleColorRes)
            textViewCardStyle1Subtitle.setupTextColor(cardHeaderStyle.subtitleColorRes)
            cardHeaderStyle.backgroundImgRes?.let {
                imageViewBackgroundCardStyle1.setupImage(it)
                cardViewHeaderCardContainer.setupShapeWithNoBackground(
                    hasThickness = cardHeaderStyle.hasThickness,
                    thicknessColorRes = cardHeaderStyle.thicknessColor,
                    radiusDimenRes = cardHeaderStyle.radiusHeaderContainer
                )
            } ?: run {
                constraintLayoutHeaderCardContainer.setupShapeWithBackground(
                    backgroundColorRes = cardHeaderStyle.backgroundColorRes,
                    hasThickness = cardHeaderStyle.hasThickness,
                    thicknessColorRes = cardHeaderStyle.thicknessColor,
                    radiusDimenRes = cardHeaderStyle.radiusHeaderContainer
                )
            }
            imageViewArrowDown.setupTintColor(cardHeaderStyle.arrowDownIconColorRes)
        }
    }

    private fun HeaderCardStyle1Binding.setupSwipeLayout(header: CardHeaderModel) {
        layoutSwipeOnHeader.linearLayoutGenericSwipeHeaderContainer.run {
            if (childCount == 0) {
                header.swipeOptionsOnHeader?.map {
                    addView(
                        getSwipeOptionViewOnHeader(header, it, context)
                    )
                }
            }
        }
    }

    private fun getSwipeOptionViewOnHeader(
        header: CardHeaderModel,
        option: SwipeOption<CardHeaderModel>,
        context: Context
    ): View =
        getSetupImageView(context, option, header.id).apply {
            setOnClickListener { onSwipeOptionSelected.invoke(option.optionId, header, null) }
            layoutParams = ViewGroup.LayoutParams(
                context.resources.getDimension(R.dimen.header_height).toInt(),
                context.resources.getDimension(R.dimen.header_height).toInt(),
            )

            setupShapeWithBackground(
                backgroundColorRes = option.backgroundColor,
                hasThickness = header.cardHeaderStyle.hasThickness,
                thicknessColorRes = header.cardHeaderStyle.thicknessColor,
                radiusDimenRes = header.cardHeaderStyle.radiusHeaderContainer
            )
        }

    override fun onItemBinding(): ItemBindingCallback<CardItemModel, CardHeaderModel> =
        { item, header, binding ->
            (binding as? ItemCardStyle1Binding)?.run {
                textViewItemCardStyle1Title.text = item.itemTitle
                setupItemStyle(binding, item.cardItemStyle, header.cardHeaderStyle)
                setupSwipeLayout(header, item)
            }
        }

    private fun setupItemStyle(
        binding: ItemCardStyle1Binding,
        cardItemStyle: CardItemStyle,
        cardHeaderStyle: CardHeaderStyle
    ) {
        binding.run {
            textViewItemCardStyle1Title.setupTextColor(cardItemStyle.titleColorRes)
            constraintLayoutItemCardContainer.setupShapeWithBackground(
                backgroundColorRes = cardItemStyle.backgroundColorRes
                    ?: cardHeaderStyle.backgroundColorItems
                    ?: R.color.black,
                hasThickness = cardItemStyle.hasThickness,
                thicknessColorRes = cardItemStyle.thicknessColor,
                radiusDimenRes = cardHeaderStyle.radiusHeaderContainer
            )
        }
    }

    private fun ItemCardStyle1Binding.setupSwipeLayout(
        header: CardHeaderModel,
        item: CardItemModel
    ) {
        layoutSwipeOnItem.linearLayoutGenericSwipeItemContainer.run {
            if (childCount == 0) {
                header.swipeOptionsOnItem?.map {
                    addView(getSwipeOptionViewOnItem(item, it, context))
                }
            }
        }
    }

    private fun getSwipeOptionViewOnItem(
        item: CardItemModel,
        option: SwipeOption<CardItemModel>,
        context: Context
    ): View =
        getSetupImageView(context, option, item.id).apply {
            setOnClickListener { onSwipeOptionSelected.invoke(option.optionId, null, item) }
            layoutParams = ViewGroup.LayoutParams(
                context.resources.getDimension(R.dimen.item_height).toInt(),
                context.resources.getDimension(R.dimen.item_height).toInt()
            )
            setupShapeWithBackground(
                backgroundColorRes = option.backgroundColor,
                hasThickness = item.cardItemStyle.hasThickness,
                thicknessColorRes = item.cardItemStyle.thicknessColor,
                radiusDimenRes = item.cardItemStyle.radiusItemContainer
            )
        }

    private fun <T> getSetupImageView(context: Context, option: SwipeOption<T>, id: Long) =
        ImageButton(context).run {
            setImageResource(option.icon)
            setupTintColor(option.iconColor)
            tag = id
            setId(id.toInt())
            this
        }
}