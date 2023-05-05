package com.github.romullodev.generic_expandable_adapter.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Space
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.apachat.swipereveallayout.core.ViewBinder
import com.github.romullodev.generic_expandable_adapter.databinding.CustomHeaderCardContainerBinding
import com.github.romullodev.generic_expandable_adapter.databinding.CustomItemCardContainerBinding
import com.github.romullodev.generic_expandable_adapter.entities.BaseHeaderCustomModel
import com.github.romullodev.generic_expandable_adapter.entities.BaseItemCustomModel
import com.github.romullodev.generic_expandable_adapter.entities.CustomSwipeOption
import com.github.romullodev.generic_expandable_adapter.utils.ExpandableAdapterConstants.IC_COLLAPSED_ROTATION_DEG
import com.github.romullodev.generic_expandable_adapter.utils.ExpandableAdapterConstants.IC_EXPANDED_ROTATION_DEG
import com.github.romullodev.generic_expandable_adapter.utils.setupShapeWithBackground
import com.github.romullodev.generic_expandable_adapter.utils.setupTintColor
import com.github.romullodev.generic_expandable_adapter.utils.visibleOrGone
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

typealias OnBindingItemCustom<I, H> = (item: I, header: H, itemBinding: ViewDataBinding) -> Unit
typealias OnBindingHeaderCustom<H> = (header: H, headerBinding: ViewDataBinding) -> Unit
typealias OnHeaderViewHolderInitMethodCustom = (headerBinding: ViewDataBinding) -> Unit
typealias OnItemViewHolderInitMethodCustom = (itemBinding: ViewDataBinding) -> Unit
typealias OnBindViewHolderHeaderCustom<H> = (headerBinding: ViewDataBinding, header: H) -> Unit
typealias OnBindViewHolderItemCustom<I, H> = (itemBinding: ViewDataBinding, item: I, header: H) -> Unit
typealias OnCustomSwipeOption<H, I> = (optionId: Int, header: H?, item: I?) -> Unit

private const val SPACE_BETWEEN_OPTIONS = 5

abstract class BaseCustomExpandableAdapter<AdapterH : BaseHeaderCustomModel<AdapterH, AdapterI>, AdapterI : BaseItemCustomModel>(
    data: AdapterH,
    private val headerLayoutRes: Int,
    private val itemLayoutRes: Int,
    expandAllAtFirst: Boolean
) : RecyclerView.Adapter<BaseCustomExpandableAdapter.BaseCustomExpandableViewHolder<AdapterH, AdapterI>>() {

    private val viewBinderHelper: ViewBinder = ViewBinder()

    private var headerObject = data

    internal fun updateData(data: AdapterH) {
        headerObject = data
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int =
        if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM

    abstract fun onBindingItem(): OnBindingItemCustom<AdapterI, AdapterH>

    abstract fun onBindingHeader(): OnBindingHeaderCustom<AdapterH>

    abstract fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView?

    abstract fun onCustomSwipeOption(): OnCustomSwipeOption<AdapterH, AdapterI>

    open fun onHeaderViewHolderInitMethod(): OnHeaderViewHolderInitMethodCustom = { _ -> }

    open fun onItemViewHolderInitMethod(): OnItemViewHolderInitMethodCustom = { _ -> }

    open fun onBindViewHolderHeader(): OnBindViewHolderHeaderCustom<AdapterH> =
        { headerBinding, header ->
            header.getCustomHeaderSwipeOptions()?.let {
                viewBinderHelper.bind(
                    (headerBinding as CustomHeaderCardContainerBinding).swipeRevealLayoutContainer,
                    header.getHeaderId().toString()
                )
            }
        }

    open fun onBindViewHolderItem(): OnBindViewHolderItemCustom<AdapterI, AdapterH> =
        { itemBinding, item, header ->
            header.getCustomItemSwipeOptions()?.let {
                viewBinderHelper.bind(
                    (itemBinding as CustomItemCardContainerBinding).swipeRevealLayoutContainer,
                    item.getItemId().toString()
                )
            }
        }

    private fun getItems(headerObject: AdapterH): List<AdapterI> = headerObject.getCustomItems()

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
        parent: ViewGroup, viewType: Int
    ): BaseCustomExpandableViewHolder<AdapterH, AdapterI> =
        DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), if (viewType == VIEW_TYPE_HEADER) headerLayoutRes
            else itemLayoutRes, FrameLayout(parent.context), false
        ).run {
            if (viewType == VIEW_TYPE_HEADER) BaseCustomExpandableViewHolder.CustomHeaderExpandableViewHolder(
                headerBinding = this,
                icExpanded = getExpandedIcImageView(this),
                bindHeaderCallback = onBindingHeader(),
                performOperationOnHeaderViewHolderInitMethod = onHeaderViewHolderInitMethod(),
            )
            else BaseCustomExpandableViewHolder.CustomItemExpandableViewHolder(
                itemBinding = this,
                bindItemCallback = onBindingItem(),
                performOperationOnItemViewHolderInitMethod = onItemViewHolderInitMethod()
            )
        }

    override fun getItemCount(): Int = if (isExpanded) getItems(headerObject).size + 1 else 1

    override fun onBindViewHolder(
        holder: BaseCustomExpandableViewHolder<AdapterH, AdapterI>,
        position: Int
    ) {
        when (holder) {
            is BaseCustomExpandableViewHolder.CustomHeaderExpandableViewHolder<AdapterH, AdapterI> -> {
                holder.headerBindingContainer?.let {
                    onBindViewHolderHeader().invoke(
                        it, headerObject
                    )
                }
                holder.bindHeader(
                    header = headerObject,
                    totalItems = getItems(headerObject).size,
                    onHeaderClickListener = onHeaderClickListener,
                    isExpanded = isExpanded,
                    onHeaderSwipeOption = onCustomSwipeOption()
                )
            }
            is BaseCustomExpandableViewHolder.CustomItemExpandableViewHolder<AdapterH, AdapterI> -> {
                holder.itemBindingContainer?.let {
                    onBindViewHolderItem().invoke(
                        it,
                        getItems(headerObject)[position - 1],
                        headerObject,
                    )
                }
                holder.bindItem(
                    item = getItems(headerObject)[position - 1],
                    header = headerObject,
                    onItemSwipeOption = onCustomSwipeOption()
                )
            }
        }
    }

    sealed class BaseCustomExpandableViewHolder<H : BaseHeaderCustomModel<H, I>, I : BaseItemCustomModel>(
        binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        class CustomHeaderExpandableViewHolder<H : BaseHeaderCustomModel<H, I>, I : BaseItemCustomModel>(
            val headerBinding: ViewDataBinding,
            icExpanded: ImageView?,
            performOperationOnHeaderViewHolderInitMethod: OnHeaderViewHolderInitMethodCustom,
            private val bindHeaderCallback: OnBindingHeaderCustom<H>
        ) : BaseCustomExpandableViewHolder<H, I>(
            getHeaderViewContainerWithChild(headerBinding.root, headerBinding.root.context)
        ) {
            val headerBindingContainer: CustomHeaderCardContainerBinding? =
                DataBindingUtil.getBinding(itemView)

            init {
                performOperationOnHeaderViewHolderInitMethod.invoke(headerBinding)
            }

            internal val icExpand: ImageView? = icExpanded
            fun bindHeader(
                header: H,
                totalItems: Int,
                isExpanded: Boolean,
                onHeaderClickListener: View.OnClickListener,
                onHeaderSwipeOption: OnCustomSwipeOption<H, I>
            ) {
                icExpand?.run {
                    rotation =
                        if (isExpanded) IC_EXPANDED_ROTATION_DEG else IC_COLLAPSED_ROTATION_DEG
                    icExpand.visibleOrGone(totalItems > 0)
                }

                headerBindingContainer?.frameLayoutCustomHeaderContainer?.setOnClickListener(
                    onHeaderClickListener
                )
                bindHeaderCallback.invoke(header, headerBinding)
                setupSwipeLayoutOnHeader(header, onHeaderSwipeOption)
            }

            private fun setupSwipeLayoutOnHeader(
                header: H,
                onHeaderSwipeOption: OnCustomSwipeOption<H, I>
            ) {
                headerBindingContainer?.layoutSwipeOnHeader?.linearLayoutGenericSwipeHeaderContainer?.run {
                    if (childCount == 0) {
                        header.getCustomHeaderSwipeOptions()?.map {
                            addView(
                                getSwipeOptionViewOnHeader(
                                    header, it, context, onHeaderSwipeOption
                                )
                            )
                            addView(
                                Space(context).apply {
                                    minimumWidth = SPACE_BETWEEN_OPTIONS
                                }
                            )
                        }
                    }
                }
            }

            private fun getSwipeOptionViewOnHeader(
                header: H,
                option: CustomSwipeOption<H>,
                context: Context,
                onHeaderSwipeOption: OnCustomSwipeOption<H, I>
            ): View = getSetupImageView(context, option, header.getHeaderId()).apply {
                setOnClickListener {
                    onHeaderSwipeOption.invoke(option.optionId, header, null)
                }
                layoutParams = ViewGroup.LayoutParams(
                    context.resources.getDimension(option.width).toInt(),
                    context.resources.getDimension(option.height).toInt(),
                )

                setupShapeWithBackground(
                    backgroundColorRes = option.backgroundColor,
                    hasThickness = option.hasThickness,
                    thicknessColorRes = option.thicknessColor,
                    radiusDimenRes = option.radius
                )
            }
        }

        class CustomItemExpandableViewHolder<H : BaseHeaderCustomModel<H, I>, I : BaseItemCustomModel>(
            val itemBinding: ViewDataBinding,
            performOperationOnItemViewHolderInitMethod: OnItemViewHolderInitMethodCustom,
            private val bindItemCallback: OnBindingItemCustom<I, H>
        ) : BaseCustomExpandableViewHolder<H, I>(
            getItemViewContainerWithChild(itemBinding.root, itemBinding.root.context)
        ) {

            val itemBindingContainer: CustomItemCardContainerBinding? =
                DataBindingUtil.getBinding(itemView)

            init {
                performOperationOnItemViewHolderInitMethod.invoke(itemBinding)
            }

            fun bindItem(
                item: I,
                header: H,
                onItemSwipeOption: OnCustomSwipeOption<H, I>
            ) {
                bindItemCallback.invoke(item, header, itemBinding)
                setupSwipeLayoutOnItem(header, item, onItemSwipeOption)
            }

            private fun setupSwipeLayoutOnItem(
                header: H,
                item: I,
                onItemSwipeOption: OnCustomSwipeOption<H, I>
            ) {
                itemBindingContainer?.layoutSwipeOnItem?.linearLayoutGenericSwipeItemContainer?.run {
                    if (childCount == 0) {
                        header.getCustomItemSwipeOptions()?.map {
                            addView(getSwipeOptionViewOnItem(item, it, context, onItemSwipeOption))
                            addView(
                                Space(context).apply {
                                    minimumWidth = SPACE_BETWEEN_OPTIONS
                                }
                            )
                        }
                    }
                }
            }

            private fun getSwipeOptionViewOnItem(
                item: I,
                option: CustomSwipeOption<I>,
                context: Context,
                onItemSwipeOption: OnCustomSwipeOption<H, I>
            ): View = getSetupImageView(context, option, item.getItemId()).apply {
                setOnClickListener {
                    onItemSwipeOption.invoke(option.optionId, null, item)
                }
                layoutParams = ViewGroup.LayoutParams(
                    context.resources.getDimension(option.width).toInt(),
                    context.resources.getDimension(option.height).toInt()
                )
                setupShapeWithBackground(
                    backgroundColorRes = option.backgroundColor,
                    hasThickness = option.hasThickness,
                    thicknessColorRes = option.thicknessColor,
                    radiusDimenRes = option.radius
                )
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_HEADER = 2

        private fun getItemViewContainerWithChild(child: View?, context: Context): ViewDataBinding =
            CustomItemCardContainerBinding.inflate(
                LayoutInflater.from(context), null, false
            ).run {
                frameLayoutCustomItemContainer.addView(child)
                swipeRevealLayoutContainer.layoutParams =
                    ViewGroup.LayoutParams(
                        MATCH_PARENT,
                        WRAP_CONTENT,
                    )
                this
            }

        private fun getHeaderViewContainerWithChild(
            child: View?, context: Context
        ): ViewDataBinding = CustomHeaderCardContainerBinding.inflate(
            LayoutInflater.from(context), null, false
        ).run {
            frameLayoutCustomHeaderContainer.addView(child)
            swipeRevealLayoutContainer.layoutParams =
                ViewGroup.LayoutParams(
                    MATCH_PARENT,
                    WRAP_CONTENT,
                )
            this
        }

        private fun <T> getSetupImageView(
            context: Context, option: CustomSwipeOption<T>, id: Long
        ) = ImageButton(context).run {
            setImageResource(option.icon)
            setupTintColor(option.iconColor)
            tag = id
            setId(id.toInt())
            this.apply {
                    layoutParams = MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                    marginEnd = 10
                }
            }
        }
    }
}