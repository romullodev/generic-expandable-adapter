package com.romullodev.generic_expandable_adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import com.github.romullodev.generic_expandable_adapter.base.ExpandableAdapterAnimation
import com.github.romullodev.generic_expandable_adapter.utils.setupCustomExpandableAdapter
import com.romullodev.generic_expandable_adapter.databinding.CustomHeaderBinding
import com.romullodev.generic_expandable_adapter.databinding.CustomItemBinding
import com.romullodev.generic_expandable_adapter.databinding.FragmentCustomExpandableAdapterBinding
import com.romullodev.generic_expandable_adapter.utils.CustomHeaderModel
import com.romullodev.generic_expandable_adapter.utils.CustomItemModel
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
        setupCustomAdapterByExtension()
        //setupCustomAdapterByAdapter()
    }

    private fun setupCustomAdapterByAdapter() {
        binding.recyclerViewExpandableAdapterDemo.run {
            MockData.getCustomHeader().map {
                CustomExpandableAdapter(
                    header = it,
                )
            }.let {
                ConcatAdapter.Config.Builder()
                    .setIsolateViewTypes(false)
                    .build().run {
                        ConcatAdapter(this, it).also {
                            adapter = it
                            itemAnimator = ExpandableAdapterAnimation()
                        }
                    }
            }
        }
    }

    private fun setupCustomAdapterByExtension() {
        binding.recyclerViewExpandableAdapterDemo.setupCustomExpandableAdapter<CustomHeaderModel, CustomItemModel>(
            dataHeaders = MockData.getCustomHeader(),
            getItemsCallback = { getItemsCallback(it) },
            itemBindingCallback = getItemBindingCallback(),
            headerBindingCallback = getHeaderBindingCallback(),
            getExpandedIcImageView = { getExpandedIcImageView(it) },
            headerLayout = R.layout.custom_header,
            itemLayout = R.layout.custom_item,
            getMainHeaderLayoutView = { getMainHeaderLayoutView(it) }
        )
    }

    private fun getMainHeaderLayoutView(headerBinding: ViewDataBinding): View =
        (headerBinding as CustomHeaderBinding).cardViewHeaderContainer

    private fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView? =
        (headerBinding as? CustomHeaderBinding)?.imageViewArrowDown

    private fun getItemsCallback(header: CustomHeaderModel): List<CustomItemModel> = header.items

    private fun getItemBindingCallback(): (item: CustomItemModel, header: CustomHeaderModel, itemBinding: ViewDataBinding) -> Unit =
        { item, _, itemBinding ->
            (itemBinding as? CustomItemBinding)?.run {
                textViewCustomItemTitle.text = item.customItemName
            }
        }

    private fun getHeaderBindingCallback(): (header: CustomHeaderModel, headerBinding: ViewDataBinding) -> Unit =
        { header, headerBinding ->
            (headerBinding as? CustomHeaderBinding)?.run {
                textViewCustomTitle.text = header.customHeaderName
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}