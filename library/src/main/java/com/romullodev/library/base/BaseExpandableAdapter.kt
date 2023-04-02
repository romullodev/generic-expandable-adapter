package com.romullodev.library.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.romullodev.library.R
import com.romullodev.library.utils.ExpandableAdapterConstants.IC_COLLAPSED_ROTATION_DEG
import com.romullodev.library.utils.ExpandableAdapterConstants.IC_EXPANDED_ROTATION_DEG
import com.romullodev.library.utils.visibleOrGone
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

typealias ItemBindingCallback<I, H> = (item: I, header: H, itemBinding: ViewDataBinding) -> Unit
typealias HeaderBindingCallback<H, P> = (header: H, layoutParamsSetup: P, headerBinding: ViewDataBinding) -> Unit
typealias ViewHolderHeaderInitMethodCallback = (headerBinding: ViewDataBinding) -> Unit
typealias ViewHolderItemInitMethodCallback = (itemBinding: ViewDataBinding) -> Unit

abstract class BaseExpandableAdapter<H, I, P>(
    private val headerObject: H,
    private val headerLayoutRes: Int,
    private val itemLayoutRes: Int
) : RecyclerView.Adapter<BaseExpandableAdapter.BaseExpandableAdapterViewHolder<H, I, P>>() {

    override fun getItemViewType(position: Int): Int =
        if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM

    abstract fun getItems(headerObject: H): List<I>

    abstract fun getItemBindingCallback(): ItemBindingCallback<I, H>

    abstract fun getHeaderBindingCallback(): HeaderBindingCallback<H, P>

    abstract fun getLayoutParamsSetup(): P

    abstract fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView?

    open fun performOperationOnHeaderViewHolderInitMethod(): ViewHolderHeaderInitMethodCallback = {}

    open fun performOperationOnItemViewHolderInitMethod(): ViewHolderItemInitMethodCallback = {}

    private var isExpanded: Boolean by Delegates.observable(false) { _: KProperty<*>, _: Boolean, newExpandedValue: Boolean ->
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
    ): BaseExpandableAdapterViewHolder<H, I, P> {
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
                VIEW_TYPE_HEADER -> BaseExpandableAdapterViewHolder.HeaderExpandableAdapter(
                    headerBinding = this,
                    icExpanded = getExpandedIcImageView(this),
                    bindHeaderCallback = getHeaderBindingCallback(),
                    performOperationOnHeaderViewHolderInitMethod = performOperationOnHeaderViewHolderInitMethod()
                )
                else -> BaseExpandableAdapterViewHolder.ItemExpandableAdapter(
                    itemBinding = this,
                    bindItemCallback = getItemBindingCallback(),
                    performOperationOnItemViewHolderInitMethod = performOperationOnItemViewHolderInitMethod()
                )
            }
        }
    }

    override fun getItemCount(): Int = if (isExpanded) getItems(headerObject).size + 1 else 1

    override fun onBindViewHolder(holder: BaseExpandableAdapterViewHolder<H, I, P>, position: Int) {
        when (holder) {
            is BaseExpandableAdapterViewHolder.HeaderExpandableAdapter<H, I, P> -> {
                holder.bindHeader(
                    header = headerObject,
                    layoutParamsSetup = getLayoutParamsSetup(),
                    totalItems = getItems(headerObject).size,
                    onHeaderClickListener = onHeaderClickListener,
                    isExpanded = isExpanded
                )
            }
            is BaseExpandableAdapterViewHolder.ItemExpandableAdapter<H, I, P> -> {
                holder.bindItem(
                    item = getItems(headerObject)[position - 1],
                    header = headerObject
                )
            }
        }
    }

    sealed class BaseExpandableAdapterViewHolder<H, I, P>(
        binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        class HeaderExpandableAdapter<H, I, P>(
            private val headerBinding: ViewDataBinding,
            icExpanded: ImageView?,
            performOperationOnHeaderViewHolderInitMethod: ViewHolderHeaderInitMethodCallback,
            private val bindHeaderCallback: HeaderBindingCallback<H, P>
        ) : BaseExpandableAdapterViewHolder<H, I, P>(headerBinding) {

            init {
                performOperationOnHeaderViewHolderInitMethod.invoke(headerBinding)
            }

            internal val icExpand: ImageView? = icExpanded
            fun bindHeader(
                header: H,
                layoutParamsSetup: P,
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
                bindHeaderCallback.invoke(header, layoutParamsSetup, headerBinding)
            }
        }

        class ItemExpandableAdapter<H, I, P>(
            private val itemBinding: ViewDataBinding,
            performOperationOnItemViewHolderInitMethod: ViewHolderItemInitMethodCallback,
            private val bindItemCallback: ItemBindingCallback<I, H>
        ) : BaseExpandableAdapterViewHolder<H, I, P>(itemBinding) {

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