package com.romullodev.generic_expandable_adapter.adapter

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.github.romullodev.generic_expandable_adapter.base.*
import com.github.romullodev.generic_expandable_adapter.entities.BaseHeaderCustomModel
import com.github.romullodev.generic_expandable_adapter.entities.BaseItemCustomModel
import com.github.romullodev.generic_expandable_adapter.entities.CustomSwipeOption
import com.romullodev.generic_expandable_adapter.R
import com.romullodev.generic_expandable_adapter.databinding.MyCustomHeaderBinding
import com.romullodev.generic_expandable_adapter.databinding.MyCustomItemBinding
import com.romullodev.generic_expandable_adapter.utils.MockData
import com.romullodev.generic_expandable_adapter.utils.MyCustomHeaderModel
import com.romullodev.generic_expandable_adapter.utils.MyCustomItemModel

class MyCustomExpandableAdapterV2(
    header: MyCustomHeaderModel,
    private val onCustomSwipeOption: OnCustomSwipeOption<MyCustomHeaderModel, MyCustomItemModel>
) : BaseCustomExpandableAdapter<MyCustomHeaderModel, MyCustomItemModel>(
    data = header,
    headerLayoutRes = R.layout.my_custom_header,
    itemLayoutRes = R.layout.my_custom_item,
    expandAllAtFirst = false,
    customSwipeOptionsOnHeader = getCustomSwipeOptionsOnHeader(),
    customSwipeOptionsOnItem = getCustomSwipeOptionsOnItem()
) {
    override fun onBindingItem(): OnBindingItemCustom<BaseItemCustomModel, BaseHeaderCustomModel<MyCustomHeaderModel, MyCustomItemModel>> =
        { item: BaseItemCustomModel, _, itemBinding ->
            (itemBinding as? MyCustomItemBinding)?.run {
                textViewCustomItemTitle.text = (item as MyCustomItemModel).myCustomItemName
            }
        }

    override fun onBindingHeader(): OnBindingHeaderCustom<BaseHeaderCustomModel<MyCustomHeaderModel, MyCustomItemModel>> =
        { header: BaseHeaderCustomModel<MyCustomHeaderModel, MyCustomItemModel>, headerBinding ->
            (headerBinding as? MyCustomHeaderBinding)?.run {
                textViewCustomTitle.text = (header as MyCustomHeaderModel).myCustomHeaderName
            }
        }

    override fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView? =
        (headerBinding as? MyCustomHeaderBinding)?.imageViewArrowDown

    override fun onCustomSwipeOption(): OnCustomSwipeOption<MyCustomHeaderModel, MyCustomItemModel> =
        onCustomSwipeOption

    companion object {
        private fun getCustomSwipeOptionsOnHeader(): List<CustomSwipeOption<MyCustomHeaderModel>> =
            listOf(
                CustomSwipeOption(
                    icon = R.drawable.ic_delete,
                    iconColor = R.color.white,
                    backgroundColor = R.color.holo_red_dark,
                    optionId = MockData.HEADER_SWIPE_DELETE_ID,
                    width = R.dimen.my_custom_width_header_option,
                    height = R.dimen.my_custom_height_header,
                    radius = R.dimen.my_custom_radius_option,
                ),
                CustomSwipeOption(
                    icon = R.drawable.ic_edit,
                    iconColor = R.color.white,
                    backgroundColor = R.color.darker_gray,
                    optionId = MockData.HEADER_SWIPE_EDIT_ID,
                    width = R.dimen.my_custom_width_header_option,
                    height = R.dimen.my_custom_height_header,
                    radius = R.dimen.my_custom_radius_option,
                )
            )

        private fun getCustomSwipeOptionsOnItem(): List<CustomSwipeOption<MyCustomItemModel>> =
            listOf(
                CustomSwipeOption(
                    icon = R.drawable.ic_delete,
                    iconColor = R.color.white,
                    backgroundColor = R.color.holo_red_dark,
                    optionId = MockData.ITEM_SWIPE_DELETE_ID,
                    width = R.dimen.my_custom_width_header_option,
                    height = R.dimen.my_custom_height_item,
                    radius = R.dimen.my_custom_radius_option,
                ),
            )
    }
}
