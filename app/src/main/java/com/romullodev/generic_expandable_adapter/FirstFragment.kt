package com.romullodev.generic_expandable_adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.romullodev.generic_expandable_adapter.databinding.FragmentFirstBinding
import com.romullodev.library.entities.CardStyle1
import com.romullodev.library.entities.CardItemStyle1
import com.romullodev.generic_expandable_adapter.utils.MockData
import com.romullodev.generic_expandable_adapter.utils.setupBackgroundColor
import com.romullodev.generic_expandable_adapter.utils.setupImage
import com.romullodev.library.databinding.HeaderCardStyle1Binding
import com.romullodev.library.databinding.ItemCardStyle1Binding
import com.romullodev.library.utils.setupCustomExpandableAdapter

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.recyclerViewExpandableAdapterDemo.setupDefaultExpandableAdapter(
//            MockData.getDefaultDataHeader()
//        )
        binding.recyclerViewExpandableAdapterDemo.setupCustomExpandableAdapter(
            dataHeaders = MockData.getMusicStyles(),
            getItemsCallback = { getItemsCallback(it) },
            itemBindingCallback = getItemBindingCallback(),
            headerBindingCallback = getHeaderBindingCallback(),
            getLayoutParamsSetup = Unit,
            getExpandedIcImageView = { getExpandedIcImageView(it) },
            headerLayout = com.romullodev.library.R.layout.header_card_style_1,
            itemLayout = com.romullodev.library.R.layout.item_card_style_1
        )
    }

    private fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView? =
        (headerBinding as? HeaderCardStyle1Binding)?.imageViewArrowDown

    private fun getItemsCallback(header: CardStyle1): List<CardItemStyle1> = header.items

    private fun getItemBindingCallback(): (item: CardItemStyle1, header: CardStyle1, itemBinding: ViewDataBinding) -> Unit =
        { item, _, itemBinding ->
            (itemBinding as? ItemCardStyle1Binding)?.run {
                textViewItemCardStyle1Name.text = item.itemName
            }
        }

    private fun getHeaderBindingCallback(): (header: CardStyle1, layoutParamsSetup: Unit, headerBinding: ViewDataBinding) -> Unit =
        { header, _, headerBinding ->
            (headerBinding as? HeaderCardStyle1Binding)?.run {
                textViewCardStyle1Title.text = header.cardName
                textViewCardStyle1Subtitle.text = getString(
                    com.romullodev.library.R.string.expandable_adapter_total_bands,
                    header.items.size.toString()
                )
                header.backgroundImgRes?.let {
                    imageViewBackgroundCardStyle1.setupImage(it)
                }
                header.colorRes?.let {
                    imageViewBackgroundCardStyle1.setupBackgroundColor(it)
                }
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}