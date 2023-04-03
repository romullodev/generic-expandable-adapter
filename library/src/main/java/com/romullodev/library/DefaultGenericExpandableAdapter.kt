package com.romullodev.library

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.romullodev.library.base.*
import com.romullodev.library.databinding.HeaderCardStyle1Binding
import com.romullodev.library.databinding.ItemCardStyle1Binding
import com.romullodev.library.entities.CardItemModel
import com.romullodev.library.entities.CardHeaderModel
import com.romullodev.library.utils.setupBackgroundColor
import com.romullodev.library.utils.setupImage
import com.romullodev.library.utils.setupShapeOnHeader
import com.romullodev.library.utils.setupShapeOnItem

class DefaultGenericExpandableAdapter(
    header: CardHeaderModel,
    private val hasThickness: Boolean,
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
                constraintLayoutItemCardContainer.setupBackgroundColor(
                    colorRes = item.backgroundColorRes ?: header.backgroundColorItems,
                    hasThickness = hasThickness
                )
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
                header.backgroundImgRes?.let {
                    imageViewBackgroundCardStyle1.setupImage(it)
                }
                header.backgroundColorRes?.let {
                    imageViewBackgroundCardStyle1.setupBackgroundColor(
                        colorRes = it,
                        hasThickness = hasThickness
                    )
                }
            }
        }

    override fun getLayoutParamsSetup() = Unit

    override fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView =
        (headerBinding as? HeaderCardStyle1Binding)?.run {
            imageViewArrowDown
        } ?: throw Exception("error on perform cast do header binding")

    override fun performOperationOnHeaderViewHolderInitMethod(): ViewHolderHeaderInitMethodCallback = {
        binding ->
        (binding as HeaderCardStyle1Binding).apply {
            cardViewHeaderCardContainer.setupShapeOnHeader(hasThickness = hasThickness)
        }
    }
    override fun performOperationOnItemViewHolderInitMethod(): ViewHolderItemInitMethodCallback = {
            binding ->
        (binding as ItemCardStyle1Binding).apply {
            cardViewItemCardContainer.setupShapeOnItem(hasThickness = hasThickness)
        }
    }
}