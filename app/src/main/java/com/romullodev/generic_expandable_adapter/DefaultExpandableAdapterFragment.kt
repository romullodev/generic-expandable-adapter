package com.romullodev.generic_expandable_adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.romullodev.generic_expandable_adapter.entities.*
import com.github.romullodev.generic_expandable_adapter.utils.removeHeaderDefaultExpandableAdapter
import com.github.romullodev.generic_expandable_adapter.utils.removeItemDefaultExpandableAdapter
import com.github.romullodev.generic_expandable_adapter.utils.setupDefaultExpandableAdapter
import com.romullodev.generic_expandable_adapter.databinding.FragmentDefaultExpandableAdapterBinding
import com.romullodev.generic_expandable_adapter.utils.MockData
import com.romullodev.generic_expandable_adapter.utils.MockData.HEADER_SWIPE_DELETE_ID
import com.romullodev.generic_expandable_adapter.utils.MockData.HEADER_SWIPE_EDIT_ID
import com.romullodev.generic_expandable_adapter.utils.MockData.ITEM_SWIPE_DELETE_ID

class DefaultExpandableAdapterFragment : Fragment() {

    private var _binding: FragmentDefaultExpandableAdapterBinding? = null

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
        setupDefaultExpandableAdapter()
    }

    private fun setupDefaultExpandableAdapter() {
        binding.recyclerViewExpandableAdapterDemo.setupDefaultExpandableAdapter(
            dataHeaders = MockData.getMusics(requireContext(), hasBackgroundImg = true),
            optionsOnHeader = getDefaultOptionsOnHeader(),
            optionsOnItem = getDefaultOptionsOnItem(),
            layoutStyle = LayoutStyle.DEFAULT
        ) { optionId, model->
            when (optionId) {
                HEADER_SWIPE_DELETE_ID -> {
                    binding.recyclerViewExpandableAdapterDemo.removeHeaderDefaultExpandableAdapter(header = model as CardHeaderModel)
                        .takeIf { it }?.let {
                        Toast.makeText(
                            requireContext(),
                            "${model.headerTitle} deleted", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                HEADER_SWIPE_EDIT_ID ->
                    Toast.makeText(
                        requireContext(),
                        "${(model as CardHeaderModel).headerTitle} edited",
                        Toast.LENGTH_LONG
                    ).show()
                ITEM_SWIPE_DELETE_ID -> {
                    binding.recyclerViewExpandableAdapterDemo.removeItemDefaultExpandableAdapter(item = model as CardItemModel)
                        .takeIf { it }?.let {
                        Toast.makeText(
                            requireContext(),
                            "${model.itemTitle} deleted",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun getDefaultOptionsOnHeader(): List<DefaultSwipeOption> =
        listOf(
            DefaultSwipeOption(
                icon = R.drawable.ic_delete,
                iconColor = R.color.white,
                backgroundColor = R.color.holo_red_dark,
                optionId = HEADER_SWIPE_DELETE_ID,
                width = R.dimen.my_custom_swipe_options_width_on_header
            ),
            DefaultSwipeOption(
                icon = R.drawable.ic_edit,
                iconColor = R.color.white,
                backgroundColor = R.color.darker_gray,
                optionId = HEADER_SWIPE_EDIT_ID,
                width = R.dimen.my_custom_swipe_options_width_on_header
            )
        )

    private fun getDefaultOptionsOnItem(): List<DefaultSwipeOption> = listOf(
        DefaultSwipeOption(
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