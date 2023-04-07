package com.romullodev.library

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.romullodev.library.base.*
import com.romullodev.library.databinding.HeaderCardStyle1Binding
import com.romullodev.library.databinding.ItemCardStyle1Binding
import com.romullodev.library.entities.CardItemModel
import com.romullodev.library.entities.CardHeaderModel
import com.romullodev.library.entities.CardHeaderStyle
import com.romullodev.library.entities.CardItemStyle
import com.romullodev.library.utils.*

class DefaultGenericExpandableAdapter(
    header: CardHeaderModel
) : BaseExpandableAdapter<CardHeaderModel, CardItemModel, Unit>(
    headerObject = header,
    headerLayoutRes = R.layout.header_card_style_1,
    itemLayoutRes = R.layout.item_card_style_1
) {
    override fun getItems(headerObject: CardHeaderModel): List<CardItemModel> = headerObject.items

    override fun getItemBindingCallback(): ItemBindingCallback<CardItemModel, CardHeaderModel> =
        { item, header, binding ->
            (binding as? ItemCardStyle1Binding)?.run {
                textViewItemCardStyle1Name.text = item.itemName
                setupItemStyle(binding, item.cardItemStyle, header.cardHeaderStyle)
            }
        }

    override fun getHeaderBindingCallback(): HeaderBindingCallback<CardHeaderModel, Unit> =
        { header, _, binding ->
            (binding as? HeaderCardStyle1Binding)?.run {
                textViewCardStyle1Title.text = header.cardName
                textViewCardStyle1Subtitle.text = root.context.getString(
                    R.string.expandable_adapter_total_bands,
                    header.items.size.toString()
                )
                setupHeaderStyle(binding, header.cardHeaderStyle)
            }
        }

    private fun setupItemStyle(
        binding: ItemCardStyle1Binding,
        cardItemStyle: CardItemStyle,
        cardHeaderStyle: CardHeaderStyle
    ) {
        binding.run {
            textViewItemCardStyle1Name.setupTextColor(cardItemStyle.titleColorRes)
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

    override fun getLayoutParamsSetup() = Unit

    override fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView =
        (headerBinding as? HeaderCardStyle1Binding)?.run {
            imageViewArrowDown
        } ?: throw Exception("error on perform cast do header binding")

//    override fun performOperationOnHeaderViewHolderInitMethod(): ViewHolderHeaderInitMethodCallback = {
//        binding ->
//        (binding as HeaderCardStyle1Binding).apply {
//            cardViewHeaderCardContainer.setupShapeOnHeader()
//        }
//    }
//    override fun performOperationOnItemViewHolderInitMethod(): ViewHolderItemInitMethodCallback = {
//            binding ->
//        (binding as ItemCardStyle1Binding).apply {
//            cardViewItemCardContainer.setupShapeOnItem()
//        }
//    }
}