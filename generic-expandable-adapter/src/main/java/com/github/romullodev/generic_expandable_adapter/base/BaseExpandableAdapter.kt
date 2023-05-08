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
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.apachat.swipereveallayout.core.ViewBinder
import com.github.romullodev.generic_expandable_adapter.databinding.HeaderCardContainerBinding
import com.github.romullodev.generic_expandable_adapter.databinding.ItemCardContainerBinding
import com.github.romullodev.generic_expandable_adapter.entities.BaseHeaderModel
import com.github.romullodev.generic_expandable_adapter.entities.BaseItemModel
import com.github.romullodev.generic_expandable_adapter.entities.GenericSwipeOption
import com.github.romullodev.generic_expandable_adapter.utils.ExpandableAdapterConstants.IC_COLLAPSED_ROTATION_DEG
import com.github.romullodev.generic_expandable_adapter.utils.ExpandableAdapterConstants.IC_EXPANDED_ROTATION_DEG
import com.github.romullodev.generic_expandable_adapter.utils.setupShapeWithBackground
import com.github.romullodev.generic_expandable_adapter.utils.setupTintColor
import com.github.romullodev.generic_expandable_adapter.utils.visibleOrGone
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

typealias OnBindingItem<I, H> = (item: I, header: H, itemBinding: ViewDataBinding) -> Unit
typealias OnBindingHeader<H> = (header: H, headerBinding: ViewDataBinding) -> Unit
typealias OnHeaderViewHolderInitMethod = (headerBinding: ViewDataBinding) -> Unit
typealias OnItemViewHolderInitMethod = (itemBinding: ViewDataBinding) -> Unit
typealias OnBindViewHolderHeader<H> = (headerBinding: ViewDataBinding, header: H) -> Unit
typealias OnBindViewHolderItem<I, H> = (itemBinding: ViewDataBinding, item: I, header: H) -> Unit
typealias OnSwipeOption = (optionId: Int, model: Any) -> Unit

private const val SPACE_BETWEEN_OPTIONS = 5

abstract class BaseExpandableAdapter<AdapterH : BaseHeaderModel<AdapterH, AdapterI>, AdapterI : BaseItemModel>(
    data: AdapterH,
    private val headerLayoutRes: Int, //TODO: colocar como metodo abstrato
    private val itemLayoutRes: Int, //TODO: colocar como metodo abstrato
    private val optionsOnHeader: List<GenericSwipeOption>?, //TODO: colocar como metodo abstrato
    private val optionsOnItem: List<GenericSwipeOption>?, //TODO: colocar como metodo abstrato
    expandAllAtFirst: Boolean
) : RecyclerView.Adapter<BaseExpandableAdapter.BaseExpandableViewHolder<AdapterH, AdapterI>>() {

    private val viewBinderHelper: ViewBinder = ViewBinder()

    private var headerData: AdapterH = data
    private val deletedItems: List<AdapterI> = data.getCustomItems()


    internal fun updateItem(itemToUpdate: AdapterI): Boolean =
        headerData.getCustomItems().let { items ->
            items.find {
                it.getModelId() == itemToUpdate.getModelId()
            }?.let {
                headerData = headerData.getHeaderWithUpdatedItems(items - it + itemToUpdate)
                notifyItemRangeChanged(
                    0,
                    headerData.getCustomItems().size
                )
                true
            } ?: false
        }

    internal fun updateHeader(data: AdapterH) {
        headerData = data
        notifyDataSetChanged()
    }

    internal fun isEqualTo(data: AdapterH): Boolean = headerData.isEqualTo(data)

    internal fun removeItem(item: AdapterI): Boolean =
        headerData.getCustomItems().run {
            find { it.isEqualTo(item) }?.let {
                headerData = headerData.getHeaderWithUpdatedItems(this - it)
                notifyItemRemoved(
                    indexOf(item) + 1
                )
                notifyItemRangeChanged(
                    0,
                    headerData.getCustomItems().size
                )
                true
            } ?: false
        }

    override fun getItemViewType(position: Int): Int =
        if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM

    abstract fun onBindingItem(): OnBindingItem<AdapterI, AdapterH>

    abstract fun onBindingHeader(): OnBindingHeader<AdapterH>

    abstract fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView?

    abstract fun onSwipeOption(): OnSwipeOption

    open fun onHeaderViewHolderInitMethod(): OnHeaderViewHolderInitMethod = { _ -> }

    open fun onItemViewHolderInitMethod(): OnItemViewHolderInitMethod = { _ -> }

    open fun onBindViewHolderHeader(): OnBindViewHolderHeader<AdapterH> =
        { headerBinding, header ->
            optionsOnHeader?.let {
                viewBinderHelper.bind(
                    (headerBinding as HeaderCardContainerBinding).swipeRevealLayoutContainer,
                    header.getModelId().toString()
                )
            }
        }

    open fun onBindViewHolderItem(): OnBindViewHolderItem<AdapterI, AdapterH> =
        { itemBinding, item, _ ->
            optionsOnItem?.let {
                viewBinderHelper.bind(
                    (itemBinding as ItemCardContainerBinding).swipeRevealLayoutContainer,
                    item.getModelId().toString()
                )
            }
        }

    private fun getItems(): List<AdapterI> = headerData.getCustomItems()

    private var isExpanded: Boolean by Delegates.observable(expandAllAtFirst) { _: KProperty<*>, _: Boolean, newExpandedValue: Boolean ->
        if (newExpandedValue) {
            notifyItemRangeInserted(1, getItems().size)
            //To update the header expand icon
            notifyItemChanged(0)
        } else {
            notifyItemRangeRemoved(1, getItems().size)
            //To update the header expand icon
            notifyItemChanged(0)
        }
    }

    private val onHeaderClickListener = View.OnClickListener { isExpanded = !isExpanded }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): BaseExpandableViewHolder<AdapterH, AdapterI> =
        DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            if (viewType == VIEW_TYPE_HEADER) headerLayoutRes
            else itemLayoutRes,
            FrameLayout(parent.context),
            false
        ).run {
            if (viewType == VIEW_TYPE_HEADER) BaseExpandableViewHolder.HeaderExpandableViewHolder(
                headerBinding = this,
                icExpanded = getExpandedIcImageView(this),
                bindHeaderCallback = onBindingHeader(),
                performOperationOnHeaderViewHolderInitMethod = onHeaderViewHolderInitMethod(),
            )
            else BaseExpandableViewHolder.ItemExpandableViewHolder(
                itemBinding = this,
                bindItemCallback = onBindingItem(),
                performOperationOnItemViewHolderInitMethod = onItemViewHolderInitMethod()
            )
        }

    override fun getItemCount(): Int = if (isExpanded) getItems().size + 1 else 1

    override fun onBindViewHolder(
        holder: BaseExpandableViewHolder<AdapterH, AdapterI>,
        position: Int
    ) {
        when (holder) {
            is BaseExpandableViewHolder.HeaderExpandableViewHolder<AdapterH, AdapterI> -> {
                holder.headerBindingContainer?.let {
                    onBindViewHolderHeader().invoke(
                        it, headerData
                    )
                }

                holder.bindHeader(
                    header = headerData,
                    totalItems = getItems().size,
                    onHeaderClickListener = onHeaderClickListener,
                    isExpanded = isExpanded,
                    onSwipeOption = onSwipeOption(),
                    optionsOnHeader = optionsOnHeader
                )
            }
            is BaseExpandableViewHolder.ItemExpandableViewHolder<AdapterH, AdapterI> -> {
                holder.itemBindingContainer?.let {
                    onBindViewHolderItem().invoke(
                        it,
                        getItems()[position - 1],
                        headerData,
                    )
                }
                holder.bindItem(
                    item = getItems()[position - 1],
                    header = headerData,
                    onSwipeOption = onSwipeOption(),
                    optionsOnItem = optionsOnItem,
                )
            }
        }
    }

    sealed class BaseExpandableViewHolder<H : BaseHeaderModel<H, I>, I : BaseItemModel>(
        binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        class HeaderExpandableViewHolder<H : BaseHeaderModel<H, I>, I : BaseItemModel>(
            val headerBinding: ViewDataBinding,
            icExpanded: ImageView?,
            performOperationOnHeaderViewHolderInitMethod: OnHeaderViewHolderInitMethod,
            private val bindHeaderCallback: OnBindingHeader<H>
        ) : BaseExpandableViewHolder<H, I>(
            getHeaderViewContainerWithChild(headerBinding.root, headerBinding.root.context)
        ) {
            val headerBindingContainer: HeaderCardContainerBinding? =
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
                onSwipeOption: OnSwipeOption,
                optionsOnHeader: List<GenericSwipeOption>?,
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
                setupSwipeLayoutOnHeader(header, onSwipeOption, optionsOnHeader)
            }

            private fun setupSwipeLayoutOnHeader(
                header: H,
                onSwipeOption: OnSwipeOption,
                optionsOnHeader: List<GenericSwipeOption>?
            ) {
                headerBindingContainer?.layoutSwipeOnHeader?.linearLayoutGenericSwipeContainer?.run {
                    removeAllViews()
                    optionsOnHeader?.map {
                        addView(
                            Space(context).apply {
                                minimumWidth = SPACE_BETWEEN_OPTIONS
                            }
                        )
                        addView(
                            getOptionView(
                                id = header.getModelId(),
                                model = header,
                                context = context,
                                option = it,
                                onSwipeOption = onSwipeOption
                            )
                        )
                    }
                    addView(
                        Space(context).apply {
                            minimumWidth = SPACE_BETWEEN_OPTIONS
                        }
                    )
                    layoutParams = MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                        (headerBindingContainer.frameLayoutCustomHeaderContainer.children.first().layoutParams as MarginLayoutParams).let {
                            setPadding(
                                0,
                                it.topMargin,
                                it.marginEnd,
                                it.bottomMargin
                            )
                        }
                    }

                }
            }
        }

        class ItemExpandableViewHolder<H : BaseHeaderModel<H, I>, I : BaseItemModel>(
            val itemBinding: ViewDataBinding,
            performOperationOnItemViewHolderInitMethod: OnItemViewHolderInitMethod,
            private val bindItemCallback: OnBindingItem<I, H>
        ) : BaseExpandableViewHolder<H, I>(
            getItemViewContainerWithChild(itemBinding.root, itemBinding.root.context)
        ) {

            val itemBindingContainer: ItemCardContainerBinding? =
                DataBindingUtil.getBinding(itemView)

            init {
                performOperationOnItemViewHolderInitMethod.invoke(itemBinding)
            }

            fun bindItem(
                item: I,
                header: H,
                onSwipeOption: OnSwipeOption,
                optionsOnItem: List<GenericSwipeOption>?
            ) {
                bindItemCallback.invoke(item, header, itemBinding)
                setupSwipeLayoutOnItem(item, onSwipeOption, optionsOnItem)
            }

            private fun setupSwipeLayoutOnItem(
                item: I,
                onItemSwipeOption: OnSwipeOption,
                genericSwipeOptionsOnItem: List<GenericSwipeOption>?
            ) {
                itemBindingContainer?.layoutSwipeOnItem?.linearLayoutGenericSwipeContainer?.run {
                    removeAllViews()
                    genericSwipeOptionsOnItem?.map {
                        addView(
                            Space(context).apply {
                                minimumWidth = SPACE_BETWEEN_OPTIONS
                            }
                        )
                        addView(
                            getOptionView(
                                id = item.getModelId(),
                                model = item,
                                context = context,
                                option = it,
                                onSwipeOption = onItemSwipeOption
                            )
                        )
                    }
                    addView(
                        Space(context).apply {
                            minimumWidth = SPACE_BETWEEN_OPTIONS
                        }
                    )
                    layoutParams = MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                        (itemBindingContainer.frameLayoutCustomItemContainer.children.first().layoutParams as MarginLayoutParams).let {
                            setPadding(
                                0,
                                it.topMargin,
                                it.marginEnd,
                                it.bottomMargin
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_HEADER = 2

        private fun getItemViewContainerWithChild(
            child: View?,
            context: Context
        ): ViewDataBinding =
            ItemCardContainerBinding.inflate(
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
        ): ViewDataBinding = HeaderCardContainerBinding.inflate(
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

        private fun getSetupImageView(
            context: Context, option: GenericSwipeOption, id: Long
        ) = ImageButton(context).run {
            setImageResource(option.icon)
            setupTintColor(option.iconColor)
            tag = id
            setId(id.toInt())
            this
        }

        private fun getOptionView(
            id: Long,
            model: Any,
            option: GenericSwipeOption,
            context: Context,
            onSwipeOption: OnSwipeOption
        ): View = getSetupImageView(context, option, id).apply {
            setOnClickListener {
                onSwipeOption.invoke(option.optionId, model)
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
}