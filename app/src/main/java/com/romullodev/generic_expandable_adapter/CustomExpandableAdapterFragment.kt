package com.romullodev.generic_expandable_adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.github.romullodev.generic_expandable_adapter.utils.setupCustomExpandableAdapterV2
import com.romullodev.generic_expandable_adapter.databinding.*
import com.romullodev.generic_expandable_adapter.utils.MyCustomHeaderModel
import com.romullodev.generic_expandable_adapter.utils.MyCustomItemModel
import com.romullodev.generic_expandable_adapter.utils.MockData

class CustomExpandableAdapterFragment : Fragment() {

    private var _binding: FragmentCustomExpandableAdapterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomExpandableAdapterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setupCustomAdapterByExtension()
        setupCustomAdapterByExtensionV2()
        //setupCustomAdapterByAdapter()
    }

//    private fun setupCustomAdapterByAdapter() {
//        binding.recyclerViewExpandableAdapterDemo.run {
//            MockData.getCustomHeader().map {
//                MyCustomExpandableAdapterV2(
//                    header = it,
//                )
//            }.let {
//                ConcatAdapter.Config.Builder()
//                    .setIsolateViewTypes(false)
//                    .build().run {
//                        ConcatAdapter(this, it).also {
//                            adapter = it
//                            itemAnimator = GenericExpandableAdapterAnimation()
//                        }
//                    }
//            }
//        }
//    }

//    private fun setupCustomAdapterByExtension() {
//        binding.recyclerViewExpandableAdapterDemo.setupCustomExpandableAdapter<MyCustomHeaderModel, MyCustomItemModel>(
//            dataHeaders = MockData.getCustomHeader(),
//            getItemsCallback = { getItemsCallback(it) },
//            itemBindingCallback = getItemBindingCallback(),
//            headerBindingCallback = getHeaderBindingCallback(),
//            getExpandedIcImageView = { getExpandedIcImageView(it) },
//            headerLayout = R.layout.my_custom_header,
//            itemLayout = R.layout.my_custom_item,
//            getMainHeaderLayoutView = { getMainHeaderLayoutView(it) }
//        )
//    }

    private fun setupCustomAdapterByExtensionV2() {
        binding.recyclerViewExpandableAdapterDemo.setupCustomExpandableAdapterV2(
            onBindingHeader = onBindingHeader(),
            onBindingItem = onBindingItem(),
            getExpandedIcImageView = { getExpandedIcImageView(it) },
            onCustomSwipeOption = onCustomSwipeOption(),
            dataHeaders = MockData.getCustomHeader(),
            headerLayout = R.layout.my_custom_header,
            itemLayout = R.layout.my_custom_item,
        )
    }

    private fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView? =
        (headerBinding as? MyCustomHeaderBinding)?.imageViewArrowDown

    private fun onBindingItem(): (item: MyCustomItemModel, header: MyCustomHeaderModel, itemBinding: ViewDataBinding) -> Unit =
        { item, _, itemBinding ->
            (itemBinding as? MyCustomItemBinding)?.run {
                textViewCustomItemTitle.text = item.myCustomItemName
            }
        }

    private fun onBindingHeader(): (header: MyCustomHeaderModel, headerBinding: ViewDataBinding) -> Unit =
        { header, headerBinding ->
            (headerBinding as? MyCustomHeaderBinding)?.run {
                textViewCustomTitle.text = header.myCustomHeaderName
            }
        }

    private fun onCustomSwipeOption(): (optionId: Int, header: MyCustomHeaderModel?, item: MyCustomItemModel?) -> Unit =
        { optionId, header, item ->
            when (optionId) {
                MockData.HEADER_SWIPE_DELETE_ID ->
                    Toast.makeText(
                        requireContext(),
                        "${header?.myCustomHeaderName} deleted", Toast.LENGTH_LONG
                    ).show()
                MockData.HEADER_SWIPE_EDIT_ID ->
                    Toast.makeText(
                        requireContext(),
                        "${header?.myCustomHeaderName} edited",
                        Toast.LENGTH_LONG
                    ).show()
                MockData.ITEM_SWIPE_DELETE_ID ->
                    Toast.makeText(
                        requireContext(),
                        "${item?.myCustomItemName} deleted",
                        Toast.LENGTH_LONG
                    ).show()
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}