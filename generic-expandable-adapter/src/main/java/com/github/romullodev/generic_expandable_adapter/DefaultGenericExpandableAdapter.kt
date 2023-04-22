package com.github.romullodev.generic_expandable_adapter

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.github.romullodev.generic_expandable_adapter.base.BaseExpandableAdapter
import com.github.romullodev.generic_expandable_adapter.base.HeaderBindingCallback
import com.github.romullodev.generic_expandable_adapter.base.ItemBindingCallback
import com.github.romullodev.generic_expandable_adapter.databinding.HeaderCardStyle1Binding
import com.github.romullodev.generic_expandable_adapter.databinding.ItemCardStyle1Binding
import com.github.romullodev.generic_expandable_adapter.entities.CardItemModel
import com.github.romullodev.generic_expandable_adapter.entities.CardHeaderModel
import com.github.romullodev.generic_expandable_adapter.entities.CardHeaderStyle
import com.github.romullodev.generic_expandable_adapter.entities.CardItemStyle
import com.github.romullodev.generic_expandable_adapter.utils.*

class DefaultGenericExpandableAdapter(
    header: CardHeaderModel,
    expandAllAtFirst: Boolean = false
) : BaseExpandableAdapter<CardHeaderModel, CardItemModel>(
    data = header,
    headerLayoutRes = R.layout.header_card_style_1,
    itemLayoutRes = R.layout.item_card_style_1,
    expandAllAtFirst = expandAllAtFirst
) {
    override fun getItems(headerObject: CardHeaderModel): List<CardItemModel> = headerObject.items

    override fun onItemBinding(): ItemBindingCallback<CardItemModel, CardHeaderModel> =
        { item, header, binding ->
            (binding as? ItemCardStyle1Binding)?.run {
                textViewItemCardStyle1Title.text = item.itemTitle
                setupItemStyle(binding, item.cardItemStyle, header.cardHeaderStyle)
            }
        }

    override fun onHeaderBinding(): HeaderBindingCallback<CardHeaderModel> =
        { header, binding ->
            (binding as? HeaderCardStyle1Binding)?.run {
                textViewCardStyle1Title.text = header.headerTitle
                textViewCardStyle1Subtitle.text = header.headerSubtitle
                setupHeaderStyle(binding, header.cardHeaderStyle)
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
                backgroundColorRes = cardItemStyle.backgroundColorRes ?: cardHeaderStyle.backgroundColorItems
                ?: R.color.black,
                hasThickness = cardItemStyle.hasThickness,
                thicknessColorRes = cardItemStyle.thicknessColor
            )
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
                    thicknessColorRes = cardHeaderStyle.thicknessColor
                )
            } ?: run {
                constraintLayoutHeaderCardContainer.setupShapeWithBackground(
                    backgroundColorRes = cardHeaderStyle.backgroundColorRes,
                    hasThickness = cardHeaderStyle.hasThickness,
                    thicknessColorRes = cardHeaderStyle.thicknessColor
                )
            }
            imageViewArrowDown.setupTintColor(cardHeaderStyle.arrowDownIconColorRes)
        }
    }

    override fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView =
        (headerBinding as? HeaderCardStyle1Binding)?.run {
            imageViewArrowDown
        } ?: throw Exception("error on perform cast do header binding")

}