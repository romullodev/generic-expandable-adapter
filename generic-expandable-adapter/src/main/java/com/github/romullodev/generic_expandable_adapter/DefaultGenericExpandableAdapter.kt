package com.github.romullodev.generic_expandable_adapter

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.github.romullodev.generic_expandable_adapter.base.*
import com.github.romullodev.generic_expandable_adapter.databinding.HeaderCardStyle1Binding
import com.github.romullodev.generic_expandable_adapter.databinding.ItemCardStyle1Binding
import com.github.romullodev.generic_expandable_adapter.entities.*
import com.github.romullodev.generic_expandable_adapter.utils.*

class DefaultGenericExpandableAdapter(
    header: CardHeaderModel,
    private val optionsOnHeader: List<GenericSwipeOption>,
    private val optionsOnItem: List<GenericSwipeOption>,
    private val onSwipeOption: OnSwipeOption,
    private val layoutOptions: LayoutOptions
) : BaseExpandableAdapter<CardHeaderModel, CardItemModel>(
    data = header,
    optionsOnHeader = optionsOnHeader,
    optionsOnItem = optionsOnItem,
    layoutOptions = layoutOptions
) {

    override val headerLayoutRes: Int get() = R.layout.header_card_style_1
    override val itemLayoutRes: Int get() = R.layout.item_card_style_1

    fun getOnCustomSwipeOption(): OnSwipeOption = onSwipeOption

    fun getOptionsOnHeader(): List<GenericSwipeOption> = optionsOnHeader

    fun getOptionsOnItem(): List<GenericSwipeOption> = optionsOnItem

    override fun onBindingItem(): OnBindingItem<CardItemModel, CardHeaderModel> =
        { item, header, binding ->
            (binding as? ItemCardStyle1Binding)?.run {
                textViewItemCardStyle1Title.text = item.itemTitle
                setupItemStyle(binding, item.cardItemStyle, header.cardHeaderStyle)
            }
        }

    override fun onBindingHeader(): OnBindingHeader<CardHeaderModel> =
        { header, binding ->
            (binding as? HeaderCardStyle1Binding)?.run {
                textViewCardStyle1Title.text = header.headerTitle
                textViewCardStyle1Subtitle.text = header.headerSubtitle
                setupHeaderStyle(binding, header.cardHeaderStyle)
            }
        }

    override fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView =
        (headerBinding as? HeaderCardStyle1Binding)?.run {
            imageViewArrowDown
        } ?: throw Exception("error on perform cast do header binding")

    override fun onSwipeOption(): OnSwipeOption = onSwipeOption

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
                    hasThickness = cardHeaderStyle.hasThicknessOnHeader ?: layoutOptions.hasThicknessForAll,
                    thicknessColorRes = layoutOptions.thicknessColorForAll,
                    radiusDimenRes = layoutOptions.radius
                )
            } ?: run {
                constraintLayoutHeaderCardContainer.setupShapeWithBackground(
                    backgroundColorRes = cardHeaderStyle.backgroundColorRes,
                    hasThickness = cardHeaderStyle.hasThicknessOnHeader ?: layoutOptions.hasThicknessForAll,
                    thicknessColorRes = layoutOptions.thicknessColorForAll,
                    radiusDimenRes = layoutOptions.radius
                )
            }
            imageViewArrowDown.setupTintColor(cardHeaderStyle.arrowDownIconColorRes)
        }
    }

    private fun setupItemStyle(
        binding: ItemCardStyle1Binding,
        cardItemStyle: CardItemStyle,
        cardHeaderStyle: CardHeaderStyle
    ) {
        binding.run {
            textViewItemCardStyle1Title.setupTextColor(cardItemStyle.titleColorRes)
            cardViewItemCardContainer.setupCardRadius(layoutOptions.radius)
            constraintLayoutItemCardContainer.setupShapeWithBackground(
                backgroundColorRes = cardItemStyle.backgroundColorRes
                    ?: cardHeaderStyle.backgroundColorItems
                    ?: R.color.black,
                hasThickness = cardItemStyle.hasThicknessOnItem ?: layoutOptions.hasThicknessForAll,
                thicknessColorRes = layoutOptions.thicknessColorForAll,
                radiusDimenRes = layoutOptions.radius
            )
        }
    }
}