//package com.romullodev.generic_expandable_adapter.adapter
//
//import android.widget.ImageView
//import androidx.databinding.ViewDataBinding
//import com.github.romullodev.generic_expandable_adapter.base.*
//import com.github.romullodev.generic_expandable_adapter.entities.BaseHeaderCustomModel
//import com.github.romullodev.generic_expandable_adapter.entities.BaseItemCustomModel
//import com.romullodev.generic_expandable_adapter.R
//import com.romullodev.generic_expandable_adapter.databinding.MyCustomHeaderBinding
//import com.romullodev.generic_expandable_adapter.databinding.MyCustomItemBinding
//import com.romullodev.generic_expandable_adapter.utils.MyCustomHeaderModel
//import com.romullodev.generic_expandable_adapter.utils.MyCustomItemModel
//
//class MyCustomExpandableAdapterV2(
//    header: MyCustomHeaderModel
//) : BaseCustomExpandableAdapter<MyCustomHeaderModel, MyCustomItemModel>(
//    data = header,
//    headerLayoutRes = R.layout.my_custom_header,
//    itemLayoutRes = R.layout.my_custom_item,
//    expandAllAtFirst = false
//) {
//
//    override fun onBindingItem(): OnBindingItemCustom<BaseItemCustomModel, BaseHeaderCustomModel<MyCustomHeaderModel, MyCustomItemModel>> =
//        { item: BaseItemCustomModel, _, itemBinding ->
//            (itemBinding as? MyCustomItemBinding)?.run {
//                textViewCustomItemTitle.text = (item as MyCustomItemModel).myCustomItemName
//            }
//        }
//
//    override fun onBindingHeader(): OnBindingHeaderCustom<BaseHeaderCustomModel<MyCustomHeaderModel, MyCustomItemModel>> =
//        { header: BaseHeaderCustomModel<MyCustomHeaderModel, MyCustomItemModel>, headerBinding ->
//            (headerBinding as? MyCustomHeaderBinding)?.run {
//                textViewCustomTitle.text = (header as MyCustomHeaderModel).myCustomHeaderName
//            }
//        }
//
//    override fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView? =
//        (headerBinding as? MyCustomHeaderBinding)?.imageViewArrowDown
//
//    override fun onHeaderSwipeOption(): OnHeaderSwipeOption<BaseHeaderCustomModel<MyCustomHeaderModel, MyCustomItemModel>, BaseItemCustomModel> =
//        { optionId, header, item ->
//        }
//
//    override fun onItemSwipeOption(): OnHeaderSwipeOption<BaseHeaderCustomModel<MyCustomHeaderModel, MyCustomItemModel>, BaseItemCustomModel> =
//        { optionId, header, item ->
//        }
//}
