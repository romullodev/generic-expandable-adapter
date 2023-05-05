package com.github.romullodev.generic_expandable_adapter

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.github.romullodev.generic_expandable_adapter.base.*
import com.github.romullodev.generic_expandable_adapter.databinding.HeaderCardStyle1Binding
import com.github.romullodev.generic_expandable_adapter.databinding.ItemCardStyle1Binding
import com.github.romullodev.generic_expandable_adapter.entities.*
import com.github.romullodev.generic_expandable_adapter.utils.*

class DefaultGenericExpandableAdapterV2(
    header: CardHeaderModel,
    headerLayout: Int = R.layout.header_card_style_1,
    itemLayout: Int = R.layout.item_card_style_1,
    customSwipeOptionsOnHeader: List<CustomSwipeOption<CardHeaderModel>>,
    customSwipeOptionsOnItem: List<CustomSwipeOption<CardItemModel>>,
    private val onCustomSwipeOption: OnCustomSwipeOption<CardHeaderModel, CardItemModel>,
    expandAllAtFirst: Boolean,
    private val layoutStyle: LayoutStyle
) : BaseCustomExpandableAdapter<CardHeaderModel, CardItemModel>(
    data = header,
    headerLayoutRes = headerLayout,
    itemLayoutRes = itemLayout,
    customSwipeOptionsOnHeader = customSwipeOptionsOnHeader,
    customSwipeOptionsOnItem = customSwipeOptionsOnItem,
    expandAllAtFirst = expandAllAtFirst
) {

    override fun onBindingItem(): OnBindingItemCustom<CardItemModel, CardHeaderModel> =
        { item, header, binding ->
            (binding as? ItemCardStyle1Binding)?.run {
                textViewItemCardStyle1Title.text = item.itemTitle
                setupItemStyle(binding, item.cardItemStyle, header.cardHeaderStyle)
            }
        }

    override fun onBindingHeader(): OnBindingHeaderCustom<CardHeaderModel> =
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

    override fun onCustomSwipeOption(): OnCustomSwipeOption<CardHeaderModel, CardItemModel> =
        onCustomSwipeOption

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
                    radiusDimenRes = layoutStyle.radius
                )
            } ?: run {
                constraintLayoutHeaderCardContainer.setupShapeWithBackground(
                    backgroundColorRes = cardHeaderStyle.backgroundColorRes,
                    hasThickness = cardHeaderStyle.hasThickness,
                    thicknessColorRes = cardHeaderStyle.thicknessColor,
                    radiusDimenRes = layoutStyle.radius
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
            cardViewItemCardContainer.setupCardRadius(layoutStyle.radius)
            constraintLayoutItemCardContainer.setupShapeWithBackground(
                backgroundColorRes = cardItemStyle.backgroundColorRes
                    ?: cardHeaderStyle.backgroundColorItems
                    ?: R.color.black,
                hasThickness = cardItemStyle.hasThickness,
                thicknessColorRes = cardItemStyle.thicknessColor,
                radiusDimenRes = layoutStyle.radius
            )
        }
    }
}