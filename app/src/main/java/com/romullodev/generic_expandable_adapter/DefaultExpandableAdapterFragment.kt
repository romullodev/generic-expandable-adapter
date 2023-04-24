package com.romullodev.generic_expandable_adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.romullodev.generic_expandable_adapter.utils.setupDefaultExpandableAdapter
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
        setupAdapterByDefault()
    }

    private fun setupAdapterByDefault() {
        binding.recyclerViewExpandableAdapterDemo.setupDefaultExpandableAdapter(
            //dataHeaders = MockData.getMusics(requireContext(), hasBackgroundImg = false)
            dataHeaders = MockData.getMusics(requireContext(), hasBackgroundImg = true)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}