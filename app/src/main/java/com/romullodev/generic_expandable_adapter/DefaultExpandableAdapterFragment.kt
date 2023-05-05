package com.romullodev.generic_expandable_adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.romullodev.generic_expandable_adapter.entities.*
import com.github.romullodev.generic_expandable_adapter.utils.setupDefaultExpandableAdapterV2
import com.romullodev.generic_expandable_adapter.databinding.FragmentDefaultExpandableAdapterBinding
import com.romullodev.generic_expandable_adapter.utils.MockData
import com.romullodev.generic_expandable_adapter.utils.MockData.HEADER_SWIPE_DELETE_ID
import com.romullodev.generic_expandable_adapter.utils.MockData.HEADER_SWIPE_EDIT_ID
import com.romullodev.generic_expandable_adapter.utils.MockData.ITEM_SWIPE_DELETE_ID

class DefaultExpandableAdapterFragment : Fragment() {

    private var _binding: FragmentDefaultExpandableAdapterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDefaultExpandableAdapterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setupAdapterByDefault()
        setupAdapterByDefaultV2()
    }

//    private fun setupAdapterByDefault() {
//        binding.recyclerViewExpandableAdapterDemo.setupDefaultExpandableAdapter(
//            //dataHeaders = MockData.getMusics(requireContext(), hasBackgroundImg = false)
//            dataHeaders = MockData.getMusics(requireContext(), hasBackgroundImg = true)
//        ) { optionId, cardHeaderModel, cardItemModel ->
//            when (optionId) {
//                HEADER_SWIPE_DELETE_ID ->
//                    Toast.makeText(
//                        requireContext(),
//                        "${cardHeaderModel?.headerTitle} deleted", Toast.LENGTH_LONG
//                    ).show()
//                HEADER_SWIPE_EDIT_ID ->
//                    Toast.makeText(
//                        requireContext(),
//                        "${cardHeaderModel?.headerTitle} edited",
//                        Toast.LENGTH_LONG
//                    ).show()
//                ITEM_SWIPE_DELETE_ID ->
//                    Toast.makeText(
//                        requireContext(),
//                        "${cardItemModel?.itemTitle} deleted",
//                        Toast.LENGTH_LONG
//                    ).show()
//            }
//        }
//    }

    private fun setupAdapterByDefaultV2() {
        binding.recyclerViewExpandableAdapterDemo.setupDefaultExpandableAdapterV2(
            dataHeaders = MockData.getMusics(requireContext(), hasBackgroundImg = true),
            customSwipeOptionsOnHeader = getDefaultSwipeOptionsOnHeader(),
            customSwipeOptionsOnItem = getDefaultSwipeOptionsOnItem(),
            layoutStyle = LayoutStyle(radius = R.dimen.my_custom_radius)
        ) { optionId, cardHeaderModel, cardItemModel ->
            when (optionId) {
                HEADER_SWIPE_DELETE_ID ->
                    Toast.makeText(
                        requireContext(),
                        "${cardHeaderModel?.headerTitle} deleted", Toast.LENGTH_LONG
                    ).show()
                HEADER_SWIPE_EDIT_ID ->
                    Toast.makeText(
                        requireContext(),
                        "${cardHeaderModel?.headerTitle} edited",
                        Toast.LENGTH_LONG
                    ).show()
                ITEM_SWIPE_DELETE_ID ->
                    Toast.makeText(
                        requireContext(),
                        "${cardItemModel?.itemTitle} deleted",
                        Toast.LENGTH_LONG
                    ).show()
            }
        }
    }

    private fun getDefaultSwipeOptionsOnHeader(): List<SwipeOptionDefault<CardHeaderModel>> =
        listOf(
            SwipeOptionDefault(
                icon = R.drawable.ic_delete,
                iconColor = R.color.white,
                backgroundColor = R.color.holo_red_dark,
                optionId = HEADER_SWIPE_DELETE_ID,
                width = R.dimen.my_custom_swipe_options_width_on_header
            ),
            SwipeOptionDefault(
                icon = R.drawable.ic_edit,
                iconColor = R.color.white,
                backgroundColor = R.color.darker_gray,
                optionId = HEADER_SWIPE_EDIT_ID,
                width = R.dimen.my_custom_swipe_options_width_on_header
            )
        )

    private fun getDefaultSwipeOptionsOnItem(): List<SwipeOptionDefault<CardItemModel>> = listOf(
        SwipeOptionDefault(
            icon = R.drawable.ic_delete,
            iconColor = R.color.white,
            backgroundColor = R.color.holo_red_dark,
            optionId = ITEM_SWIPE_DELETE_ID,
            width = R.dimen.my_custom_swipe_options_width_on_item
        )
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}