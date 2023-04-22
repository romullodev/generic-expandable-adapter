package com.github.romullodev.generic_expandable_adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.romullodev.generic_expandable_adapter.base.BaseExpandableAdapter
import com.github.romullodev.generic_expandable_adapter.databinding.HeaderCardStyle1Binding
import com.github.romullodev.generic_expandable_adapter.databinding.ItemCardStyle1Binding
import com.github.romullodev.generic_expandable_adapter.demo.DemoActivity
import com.github.romullodev.generic_expandable_adapter.entities.CardHeaderModel
import com.github.romullodev.generic_expandable_adapter.utils.setupDefaultExpandableAdapter
import com.github.romullodev.generic_expandable_adapter.utils.updateDefaultExpandableAdapterHeaderAt
import org.robolectric.Robolectric

open class BaseTest {

    internal fun getItemBindingInflated(
        data: List<CardHeaderModel>,
        viewHolderRowIndex: Int
    ): ItemCardStyle1Binding =
        getSetupAdapterOnRecyclerView(data).run {
            findViewHolderForAdapterPosition(viewHolderRowIndex).let {
                ((it as BaseExpandableAdapter.BaseExpandableViewHolder.ItemExpandableViewHolder<*, *>
                        ).itemBinding as ItemCardStyle1Binding)
            }
        }

    internal fun getHeaderBindingInflated(
        data: List<CardHeaderModel>,
        viewHolderRowIndex: Int
    ): HeaderCardStyle1Binding =
        getSetupAdapterOnRecyclerView(data).run {
            findViewHolderForAdapterPosition(viewHolderRowIndex).let {
                ((it as BaseExpandableAdapter.BaseExpandableViewHolder.HeaderExpandableViewHolder<*, *>
                        ).headerBinding as HeaderCardStyle1Binding)
            }
        }

    internal fun getSetupAdapterOnRecyclerView(
        data: List<CardHeaderModel>
    ): RecyclerView =
        Robolectric.buildActivity(DemoActivity::class.java).setup().get().run {
            binding.recyclerViewDemoActivity.apply {
                setupDefaultExpandableAdapter(data, true)
                updateMeasure()
            }
        }
}

private fun RecyclerView.updateMeasure() {
    measure(
        View.MeasureSpec.UNSPECIFIED,
        View.MeasureSpec.UNSPECIFIED
    )
    layout(0, 0, 1000, 1000)
}