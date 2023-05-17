package com.github.romullodev.generic_expandable_adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.widget.ImageButton
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.romullodev.generic_expandable_adapter.base.BaseExpandableAdapter
import com.github.romullodev.generic_expandable_adapter.base.OnSwipeOption
import com.github.romullodev.generic_expandable_adapter.databinding.HeaderCardStyle1Binding
import com.github.romullodev.generic_expandable_adapter.databinding.ItemCardStyle1Binding
import com.github.romullodev.generic_expandable_adapter.demo.DemoActivity
import com.github.romullodev.generic_expandable_adapter.entities.CardHeaderModel
import com.github.romullodev.generic_expandable_adapter.entities.DefaultSwipeOption
import com.github.romullodev.generic_expandable_adapter.entities.LayoutOptions
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.LAYOUT_THICKNESS_COLOR
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.getHeaderModelFilled
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
    internal lateinit var context: Context

    @Before
    fun setup() {
        headerBinding = getHeaderBindingInflated(
            data = listOf(getHeaderModelFilled()),
            layoutOptions = LayoutOptions.DEFAULT.copy(
                expandAllAtFirst = true,
                thicknessColorForAll = LAYOUT_THICKNESS_COLOR
            )
        )
        itemBinding = getItemBindingInflated(
            data = listOf(getHeaderModelFilled()), layoutOptions = LayoutOptions.DEFAULT.copy(
                expandAllAtFirst = true,
                thicknessColorForAll = LAYOUT_THICKNESS_COLOR
            )
        )
        context = headerBinding.root.context
    }

    internal fun getItemBindingInflated(
        data: List<CardHeaderModel>,
        viewHolderRowIndex: Int = 1,
        layoutOptions: LayoutOptions = LayoutOptions.DEFAULT.copy(expandAllAtFirst = true)
    ): ItemCardStyle1Binding =
        getSetupAdapterOnRecyclerViewWithAllExpanded(
            data = data,
            layoutOptions = layoutOptions
        ).run {
            getItemBindingFromViewHolder(
                findViewHolderForAdapterPosition(viewHolderRowIndex)
            )
        }

    internal fun getHeaderBindingInflated(
        data: List<CardHeaderModel>,
        viewHolderRowIndex: Int = 0,
        layoutOptions: LayoutOptions = LayoutOptions.DEFAULT.copy(expandAllAtFirst = true)
    ): HeaderCardStyle1Binding =
        getSetupAdapterOnRecyclerViewWithAllExpanded(
            data = data,
            layoutOptions = layoutOptions
        ).run {
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
        onSwipeOption: OnSwipeOption = { _, _ -> },
        layoutOptions: LayoutOptions = LayoutOptions.DEFAULT.copy(expandAllAtFirst = true),
    ): RecyclerView =
        Robolectric.buildActivity(DemoActivity::class.java).setup().get().run {
            binding.recyclerViewDemoActivity.apply {
                setupDefaultExpandableAdapter(
                    dataHeaders = data,
                    layoutOptions = layoutOptions,
                    optionsOnHeader = optionsOnHeader,
                    optionsOnItem = optionsOnItem,
                    onSwipeOption = onSwipeOption
                )
                updateMeasure()
            }
        }

    internal fun getGradientDrawable(
        @DrawableRes res: Int,
        @ColorRes thicknessColor: Int? = null
    ): GradientDrawable =
        (ContextCompat.getDrawable(
            context,
            res
        ) as GradientDrawable).run {
            thicknessColor?.let {
                setStroke(
                    context.resources.getDimension(R.dimen.thickness).toInt(),
                    ContextCompat.getColor(context, it)
                )
            }
            this
        }

    internal fun getGradientDrawableFromOptionHeader(viewHolder: ViewHolder?): GradientDrawable =
        ((viewHolder as BaseExpandableAdapter.BaseExpandableViewHolder.HeaderExpandableViewHolder<*, *>
                ).headerBindingContainer?.layoutSwipeOnHeader?.linearLayoutGenericSwipeContainer?.getChildAt(
                1
            ) as ImageButton).background as GradientDrawable

    internal fun getGradientDrawableFromOptionItem(viewHolder: ViewHolder?): GradientDrawable =
        ((viewHolder as BaseExpandableAdapter.BaseExpandableViewHolder.ItemExpandableViewHolder<*, *>
                ).itemBindingContainer?.layoutSwipeOnItem?.linearLayoutGenericSwipeContainer?.getChildAt(
                1
            ) as ImageButton).background as GradientDrawable
}