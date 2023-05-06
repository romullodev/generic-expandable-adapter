package com.romullodev.generic_expandable_adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import com.github.romullodev.generic_expandable_adapter.base.CustomExpandableAdapterAnimation
import com.github.romullodev.generic_expandable_adapter.entities.CustomSwipeOption
import com.github.romullodev.generic_expandable_adapter.utils.setupCustomExpandableAdapterV2
import com.romullodev.generic_expandable_adapter.adapter.MyCustomExpandableAdapterV2
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
        setupCustomAdapterByExtensionV2()
        //setupCustomAdapterByAdapterV2()
    }

    private fun setupCustomAdapterByAdapterV2() {
        MockData.getCustomHeader().map {
            MyCustomExpandableAdapterV2(
                onCustomSwipeOption = onCustomSwipeOption(),
                header = it,
            )
        }.let {
            ConcatAdapter.Config.Builder()
                .setIsolateViewTypes(true)
                .build().run {
                    ConcatAdapter(this, it).also {
                        binding.recyclerViewExpandableAdapterDemo.adapter = it
                        binding.recyclerViewExpandableAdapterDemo.itemAnimator =
                            CustomExpandableAdapterAnimation()
                    }
                }
        }

        MyCustomExpandableAdapterV2
    }

    private fun setupCustomAdapterByExtensionV2() {
        binding.recyclerViewExpandableAdapterDemo.setupCustomExpandableAdapterV2(
            onBindingHeader = onBindingHeader(),
            onBindingItem = onBindingItem(),
            getExpandedIcImageView = { getExpandedIcImageView(it) },
            onCustomSwipeOption = onCustomSwipeOption(),
            customSwipeOptionsOnHeader = getSwipeOptionsOnHeader(),
            customSwipeOptionsOnItem = getSwipeOptionsOnItem(),
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

    private fun getSwipeOptionsOnHeader(): List<CustomSwipeOption<MyCustomHeaderModel>> =
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

    private fun getSwipeOptionsOnItem(): List<CustomSwipeOption<MyCustomItemModel>> =
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}