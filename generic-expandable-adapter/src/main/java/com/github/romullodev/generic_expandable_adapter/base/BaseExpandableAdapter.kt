package com.github.romullodev.generic_expandable_adapter.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.github.romullodev.generic_expandable_adapter.utils.ExpandableAdapterConstants.IC_COLLAPSED_ROTATION_DEG
import com.github.romullodev.generic_expandable_adapter.utils.ExpandableAdapterConstants.IC_EXPANDED_ROTATION_DEG
import com.github.romullodev.generic_expandable_adapter.utils.visibleOrGone
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

typealias ItemBindingCallback<I, H> = (item: I, header: H, itemBinding: ViewDataBinding) -> Unit
typealias HeaderBindingCallback<H> = (header: H, headerBinding: ViewDataBinding) -> Unit
typealias ViewHolderHeaderInitMethodCallback = (headerBinding: ViewDataBinding) -> Unit
typealias ViewHolderItemInitMethodCallback = (itemBinding: ViewDataBinding) -> Unit

abstract class BaseExpandableAdapter<H, I>(
    data: H,
    private val headerLayoutRes: Int,
    private val itemLayoutRes: Int,
    expandAllAtFirst: Boolean
) : RecyclerView.Adapter<BaseExpandableAdapter.BaseExpandableViewHolder<H, I>>() {

    private var headerObject = data

    internal fun updateData(data: H){
        headerObject = data
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int =
        if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM

    abstract fun getItems(headerObject: H): List<I>

    abstract fun onItemBinding(): ItemBindingCallback<I, H>

    abstract fun onHeaderBinding(): HeaderBindingCallback<H>

    abstract fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView?

    open fun onHeaderViewHolderInitMethod(): ViewHolderHeaderInitMethodCallback = {}

    open fun onItemViewHolderInitMethod(): ViewHolderItemInitMethodCallback = {}

    private var isExpanded: Boolean by Delegates.observable(expandAllAtFirst) { _: KProperty<*>, _: Boolean, newExpandedValue: Boolean ->
        if (newExpandedValue) {
            notifyItemRangeInserted(1, getItems(headerObject).size)
            //To update the header expand icon
            notifyItemChanged(0)
        } else {
            notifyItemRangeRemoved(1, getItems(headerObject).size)
            //To update the header expand icon
            notifyItemChanged(0)
        }
    }

    private val onHeaderClickListener = View.OnClickListener { isExpanded = !isExpanded }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseExpandableViewHolder<H, I> {
        DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            when (viewType) {
                VIEW_TYPE_HEADER -> headerLayoutRes
                else -> itemLayoutRes
            },
            FrameLayout(parent.context),
            false
        ).run {
            return when (viewType) {
                VIEW_TYPE_HEADER -> BaseExpandableViewHolder.HeaderExpandableViewHolder<H, I>(
                    headerBinding = this,
                    icExpanded = getExpandedIcImageView(this),
                    bindHeaderCallback = onHeaderBinding(),
                    performOperationOnHeaderViewHolderInitMethod = onHeaderViewHolderInitMethod()
                ).apply { headerBinding.root }
                else -> BaseExpandableViewHolder.ItemExpandableViewHolder(
                    itemBinding = this,
                    bindItemCallback = onItemBinding(),
                    performOperationOnItemViewHolderInitMethod = onItemViewHolderInitMethod()
                )
            }
        }
    }

    override fun getItemCount(): Int = if (isExpanded) getItems(headerObject).size + 1 else 1

    override fun onBindViewHolder(holder: BaseExpandableViewHolder<H, I>, position: Int) {
        when (holder) {
            is BaseExpandableViewHolder.HeaderExpandableViewHolder<H, I> -> {
                holder.bindHeader(
                    header = headerObject,
                    totalItems = getItems(headerObject).size,
                    onHeaderClickListener = onHeaderClickListener,
                    isExpanded = isExpanded
                )
            }
            is BaseExpandableViewHolder.ItemExpandableViewHolder<H, I> -> {
                holder.bindItem(
                    item = getItems(headerObject)[position - 1],
                    header = headerObject
                )
            }
        }
    }

    sealed class BaseExpandableViewHolder<H, I>(
        binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        class HeaderExpandableViewHolder<H, I>(
            val headerBinding: ViewDataBinding,
            icExpanded: ImageView?,
            performOperationOnHeaderViewHolderInitMethod: ViewHolderHeaderInitMethodCallback,
            private val bindHeaderCallback: HeaderBindingCallback<H>
        ) : BaseExpandableViewHolder<H, I>(headerBinding) {

            init {
                performOperationOnHeaderViewHolderInitMethod.invoke(headerBinding)
            }

            internal val icExpand: ImageView? = icExpanded
            fun bindHeader(
                header: H,
                totalItems: Int,
                isExpanded: Boolean,
                onHeaderClickListener: View.OnClickListener
            ) {
                icExpand?.run {
                    rotation = if (isExpanded) IC_EXPANDED_ROTATION_DEG else IC_COLLAPSED_ROTATION_DEG
                    //icExpand.setOnClickListener(onHeaderClickListener)
                    icExpand.visibleOrGone(totalItems > 0)
                }
                headerBinding.root.setOnClickListener(onHeaderClickListener)
                bindHeaderCallback.invoke(header, headerBinding)
            }
        }

        class ItemExpandableViewHolder<H, I>(
            val itemBinding: ViewDataBinding,
            performOperationOnItemViewHolderInitMethod: ViewHolderItemInitMethodCallback,
            private val bindItemCallback: ItemBindingCallback<I, H>
        ) : BaseExpandableViewHolder<H, I>(itemBinding) {

            init {
                performOperationOnItemViewHolderInitMethod.invoke(itemBinding)
            }

            fun bindItem(item: I, header: H) {
                bindItemCallback.invoke(item, header, itemBinding)
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_HEADER = 2
    }
}