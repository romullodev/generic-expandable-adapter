package com.romullodev.generic_expandable_adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.romullodev.generic_expandable_adapter.databinding.CustomHeaderBinding
import com.romullodev.generic_expandable_adapter.databinding.CustomItemBinding
import com.romullodev.generic_expandable_adapter.databinding.FragmentCustomExpandableAdapterBinding
import com.romullodev.generic_expandable_adapter.utils.MockData
import com.romullodev.library.entities.CardHeaderModel
import com.romullodev.library.entities.CardItemModel
import com.romullodev.library.utils.setupCustomExpandableAdapter

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
        setupAdapterByCustom()
    }

    private fun setupAdapterByCustom() {
        binding.recyclerViewExpandableAdapterDemo.setupCustomExpandableAdapter(
            dataHeaders = MockData.getMusics(),
            getItemsCallback = { getItemsCallback(it) },
            itemBindingCallback = getItemBindingCallback(),
            headerBindingCallback = getHeaderBindingCallback(),
            getLayoutParamsSetup = Unit,
            getExpandedIcImageView = { getExpandedIcImageView(it) },
            headerLayout = R.layout.custom_header,
            itemLayout = R.layout.custom_item
        )
    }

    private fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView? =
        (headerBinding as? CustomHeaderBinding)?.imageViewArrowDown

    private fun getItemsCallback(header: CardHeaderModel): List<CardItemModel> = header.items

    private fun getItemBindingCallback(): (item: CardItemModel, header: CardHeaderModel, itemBinding: ViewDataBinding) -> Unit =
        { item, _, itemBinding ->
            (itemBinding as? CustomItemBinding)?.run {
                textViewCustomItemTitle.text = item.itemName
            }
        }

    private fun getHeaderBindingCallback(): (header: CardHeaderModel, layoutParamsSetup: Unit, headerBinding: ViewDataBinding) -> Unit =
        { header, _, headerBinding ->
            (headerBinding as? CustomHeaderBinding)?.run {
                textViewCustomTitle.text = header.cardName
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}