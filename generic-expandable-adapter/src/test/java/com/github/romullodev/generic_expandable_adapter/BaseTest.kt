package com.github.romullodev.generic_expandable_adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.romullodev.generic_expandable_adapter.base.BaseExpandableAdapter
import com.github.romullodev.generic_expandable_adapter.base.OnSwipeOption
import com.github.romullodev.generic_expandable_adapter.databinding.HeaderCardStyle1Binding
import com.github.romullodev.generic_expandable_adapter.databinding.ItemCardStyle1Binding
import com.github.romullodev.generic_expandable_adapter.demo.DemoActivity
import com.github.romullodev.generic_expandable_adapter.entities.CardHeaderModel
import com.github.romullodev.generic_expandable_adapter.entities.DefaultSwipeOption
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.getHeaderModelFilled
import com.github.romullodev.generic_expandable_adapter.util.updateMeasure
import com.github.romullodev.generic_expandable_adapter.utils.setupDefaultExpandableAdapter
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
open class BaseTest {
    internal lateinit var itemBinding: ItemCardStyle1Binding
    internal lateinit var headerBinding: HeaderCardStyle1Binding

    @Before
    fun setup() {
        headerBinding = getHeaderBindingInflated(listOf(getHeaderModelFilled()))
        itemBinding = getItemBindingInflated(listOf(getHeaderModelFilled()))
    }

    internal fun getItemBindingInflated(
        data: List<CardHeaderModel>,
        viewHolderRowIndex: Int = 1,
    ): ItemCardStyle1Binding =
        getSetupAdapterOnRecyclerViewWithAllExpanded(data).run {
            getItemBindingFromViewHolder(
                findViewHolderForAdapterPosition(viewHolderRowIndex)
            )
        }

    internal fun getHeaderBindingInflated(
        data: List<CardHeaderModel>,
        viewHolderRowIndex: Int = 0
    ): HeaderCardStyle1Binding =
        getSetupAdapterOnRecyclerViewWithAllExpanded(data).run {
            getHeaderBindingFromViewHolder(
                findViewHolderForAdapterPosition(viewHolderRowIndex)
            )
        }

    internal fun getHeaderBindingFromViewHolder(viewHolder: ViewHolder?): HeaderCardStyle1Binding =
        ((viewHolder as BaseExpandableAdapter.BaseExpandableViewHolder.HeaderExpandableViewHolder<*, *>
                ).headerBinding as HeaderCardStyle1Binding)

    internal fun getItemBindingFromViewHolder(viewHolder: ViewHolder?): ItemCardStyle1Binding =
        ((viewHolder as BaseExpandableAdapter.BaseExpandableViewHolder.ItemExpandableViewHolder<*, *>
                ).itemBinding as ItemCardStyle1Binding)

    internal fun getSetupAdapterOnRecyclerViewWithAllExpanded(
        data: List<CardHeaderModel>,
        optionsOnHeader: List<DefaultSwipeOption> = emptyList(),
        optionsOnItem: List<DefaultSwipeOption> = emptyList(),
        onSwipeOption: OnSwipeOption = { _, _ -> }
    ): RecyclerView =
        Robolectric.buildActivity(DemoActivity::class.java).setup().get().run {
            binding.recyclerViewDemoActivity.apply {
                setupDefaultExpandableAdapter(
                    dataHeaders = data,
                    expandAllAtFirst = true,
                    optionsOnHeader = optionsOnHeader,
                    optionsOnItem = optionsOnItem,
                    onSwipeOption = onSwipeOption
                )
                updateMeasure()
            }
        }
}