package com.github.romullodev.generic_expandable_adapter

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.ConcatAdapter
import com.github.romullodev.generic_expandable_adapter.base.BaseExpandableAdapter
import com.github.romullodev.generic_expandable_adapter.base.OnSwipeOption
import com.github.romullodev.generic_expandable_adapter.databinding.HeaderCardStyle1Binding
import com.github.romullodev.generic_expandable_adapter.entities.CardHeaderModel
import com.github.romullodev.generic_expandable_adapter.entities.CardItemModel
import com.github.romullodev.generic_expandable_adapter.entities.DefaultSwipeOption
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.HEADER_BACKGROUND_COLOR
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.HEADER_BACKGROUND_ITEMS
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.HEADER_EXPANDABLE_IC_COLOR
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.HEADER_IMG_BACKGROUND
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.HEADER_OPTION_ICON_COLOR
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.HEADER_OPTION_ICON
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.HEADER_OPTION_ID
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.HEADER_OPTION_WITH
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.HEADER_SUBTITLE
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.HEADER_SUBTITLE_COLOR
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.HEADER_THICKNESS_COLOR
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.HEADER_TITLE
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.HEADER_TITLE_COLOR
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.ITEM_BACKGROUND_COLOR
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.ITEM_OPTION_ICON
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.ITEM_OPTION_ICON_COLOR
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.ITEM_OPTION_ID
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.ITEM_OPTION_WITH
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.ITEM_THICKNESS_COLOR
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.ITEM_TITLE
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.ITEM_TITLE_COLOR
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.getHeaderModelFilled
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.getItemModelFilled
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.getOptionOnHeader
import com.github.romullodev.generic_expandable_adapter.util.Utils.Companion.getOptionOnItem
import com.github.romullodev.generic_expandable_adapter.util.addHeader
import com.github.romullodev.generic_expandable_adapter.util.performSwipeToLeft
import com.github.romullodev.generic_expandable_adapter.util.updateHeaderAt
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf

/**
 * SUMMARY
 * 1-TEXTS
 *   check info on header
 *   check info on item
 *
 * 2-STYLES
 *  check background color on header
 *  check background color on item
 *  check title and subtitle color on header
 *  check tile color on item
 *  check stroke with background color on header
 *  check stroke on item
 *  check stroke with background img on header
 *  check expandable icon color
 *  check option style on header
 *  check option style on item
 *  2.1-STYLES PRIORITY
 *   check priority with header and item background color
 *   check priority with img and background color on header
 *
 * 3-OPERATIONS
 *  check add new header model extension
 *  check update header extension
 *
 * 4-OPTIONS
 *  check options number on header
 *  check options number on item
 *  check click on header
 *  check click on item
 *
 * 5-OTHERS
 *  check expandable icon visibility
 *  check total items on adapter
 */

@RunWith(RobolectricTestRunner::class)
class DefaultGenericExpandableAdapterTest : BaseTest() {

    @Test
    fun `check info on header`() {
        assertEquals(HEADER_TITLE, headerBinding.textViewCardStyle1Title.text)
        assertEquals(HEADER_SUBTITLE, headerBinding.textViewCardStyle1Subtitle.text)
    }

    @Test
    fun `check info on item`() {
        assertEquals(ITEM_TITLE, itemBinding.textViewItemCardStyle1Title.text)
    }

    @Test
    fun `check background color on header`() {
        headerBinding = getHeaderBindingInflated(
            listOf(
                getHeaderModelFilled().copy(
                    cardHeaderStyle = getHeaderModelFilled().cardHeaderStyle.copy(
                        backgroundImgRes = null
                    )
                )
            )
        )
        val expectedDrawable = (ContextCompat.getDrawable(
            headerBinding.root.context, R.drawable.shape_with_background
        ) as GradientDrawable).run {
            setColor(ContextCompat.getColor(headerBinding.root.context, HEADER_BACKGROUND_COLOR))
            this
        }

        assertEquals(
            shadowOf(expectedDrawable).lastSetColor, shadowOf(
                headerBinding.constraintLayoutHeaderCardContainer.background as GradientDrawable
            ).lastSetColor
        )
    }

    @Test
    fun `check background color on item`() {
        val expectedDrawable = (ContextCompat.getDrawable(
            itemBinding.root.context, R.drawable.shape_with_background
        ) as GradientDrawable).run {
            setColor(
                ContextCompat.getColor(itemBinding.root.context, ITEM_BACKGROUND_COLOR)
            )
            this
        }

        assertEquals(
            shadowOf(expectedDrawable).lastSetColor,
            shadowOf(itemBinding.constraintLayoutItemCardContainer.background as GradientDrawable).lastSetColor
        )
    }

    @Test
    fun `check title and subtitle color on header`() {
        assertEquals(
            ContextCompat.getColor(headerBinding.root.context, HEADER_TITLE_COLOR),
            headerBinding.textViewCardStyle1Title.currentTextColor,
        )
        assertEquals(
            ContextCompat.getColor(headerBinding.root.context, HEADER_SUBTITLE_COLOR),
            headerBinding.textViewCardStyle1Subtitle.currentTextColor,
        )
    }

    @Test
    fun `check tile color on item`() {
        assertEquals(
            ContextCompat.getColor(itemBinding.root.context, ITEM_TITLE_COLOR),
            itemBinding.textViewItemCardStyle1Title.currentTextColor,
        )
    }

    @Test
    fun `check stroke with background color on header`() {
        headerBinding = getHeaderBindingInflated(
            listOf(
                getHeaderModelFilled().copy(
                    cardHeaderStyle = getHeaderModelFilled().cardHeaderStyle.copy(
                        backgroundImgRes = null
                    )
                )
            )
        )

        val expectedDrawable = (ContextCompat.getDrawable(
            headerBinding.root.context, R.drawable.shape_with_background
        ) as GradientDrawable).run {
            setStroke(
                headerBinding.root.context.resources.getDimension(R.dimen.thickness).toInt(),
                ContextCompat.getColor(headerBinding.root.context, HEADER_THICKNESS_COLOR)
            )
            this
        }

        assertEquals(
            shadowOf(expectedDrawable).strokeColor,
            shadowOf(headerBinding.constraintLayoutHeaderCardContainer.background as GradientDrawable).strokeColor
        )
        assertEquals(
            shadowOf(expectedDrawable).strokeWidth,
            shadowOf(headerBinding.constraintLayoutHeaderCardContainer.background as GradientDrawable).strokeWidth
        )
    }

    @Test
    fun `check stroke on item`() {
        val context = itemBinding.root.context
        val expectedDrawable = (ContextCompat.getDrawable(
            context, R.drawable.shape_with_background
        ) as GradientDrawable).run {
            setStroke(
                context.resources.getDimension(R.dimen.thickness).toInt(),
                ContextCompat.getColor(context, ITEM_THICKNESS_COLOR)
            )
            this
        }

        assertEquals(
            shadowOf(expectedDrawable).strokeColor,
            shadowOf(itemBinding.constraintLayoutItemCardContainer.background as GradientDrawable).strokeColor
        )
        assertEquals(
            shadowOf(expectedDrawable).strokeWidth,
            shadowOf(itemBinding.constraintLayoutItemCardContainer.background as GradientDrawable).strokeWidth
        )

    }

    @Test
    fun `check stroke with background img on header`() {
        headerBinding = getHeaderBindingInflated(listOf(getHeaderModelFilled()))

        val context = headerBinding.root.context
        val expectedDrawable = (ContextCompat.getDrawable(
            context,
            R.drawable.shape_no_background
        ) as GradientDrawable).run {
            setStroke(
                context.resources.getDimension(R.dimen.thickness).toInt(),
                ContextCompat.getColor(context, HEADER_THICKNESS_COLOR)
            )
            this
        }

        assertEquals(
            shadowOf(expectedDrawable).strokeColor,
            shadowOf(headerBinding.cardViewHeaderCardContainer.foreground as GradientDrawable).strokeColor
        )
        assertEquals(
            shadowOf(expectedDrawable).strokeWidth,
            shadowOf(headerBinding.cardViewHeaderCardContainer.foreground as GradientDrawable).strokeWidth
        )
    }

    @Test
    fun `check expandable icon color`() {
        assertEquals(
            ColorStateList.valueOf(
                ContextCompat.getColor(headerBinding.root.context, HEADER_EXPANDABLE_IC_COLOR)
            ).defaultColor,
            headerBinding.imageViewArrowDown.imageTintList?.defaultColor
        )
    }

    @Test
    fun `check option style on header`() {
        // Arrange
        val optionsOnHeader = listOf(getOptionOnHeader())

        // Act
        val recyclerView = getSetupAdapterOnRecyclerViewWithAllExpanded(
            data = listOf(getHeaderModelFilled()),
            optionsOnHeader = optionsOnHeader,
        )

        // Assert
        val viewHolder = recyclerView.findViewHolderForAdapterPosition(0)
        val imageButtonOption =
            (viewHolder as BaseExpandableAdapter.BaseExpandableViewHolder.HeaderExpandableViewHolder<*, *>
                    ).headerBindingContainer?.layoutSwipeOnHeader?.linearLayoutGenericSwipeContainer?.getChildAt(
                    1
                ) as ImageButton

        assertEquals(
            shadowOf(imageButtonOption.drawable).createdFromResId,
            shadowOf(
                ContextCompat.getDrawable(
                    headerBinding.root.context,
                    HEADER_OPTION_ICON
                )
            ).createdFromResId
        )
        assertEquals(
            ColorStateList.valueOf(
                ContextCompat.getColor(headerBinding.root.context, HEADER_OPTION_ICON_COLOR)
            ).defaultColor,
            imageButtonOption.imageTintList?.defaultColor
        )
        assertEquals(
            headerBinding.root.context.resources.getDimension(HEADER_OPTION_WITH).toInt(),
            imageButtonOption.width
        )
    }

    @Test
    fun `check option style on item`() {
        // Arrange
        val optionsOnItem = listOf(getOptionOnItem())

        // Act
        val recyclerView = getSetupAdapterOnRecyclerViewWithAllExpanded(
            data = listOf(getHeaderModelFilled()),
            optionsOnItem = optionsOnItem,
        )

        // Assert
        val viewHolder = recyclerView.findViewHolderForAdapterPosition(1)
        val imageButtonOption =
            (viewHolder as BaseExpandableAdapter.BaseExpandableViewHolder.ItemExpandableViewHolder<*, *>
                    ).itemBindingContainer?.layoutSwipeOnItem?.linearLayoutGenericSwipeContainer?.getChildAt(
                    1
                ) as ImageButton

        assertEquals(
            shadowOf(imageButtonOption.drawable).createdFromResId,
            shadowOf(
                ContextCompat.getDrawable(
                    headerBinding.root.context,
                    ITEM_OPTION_ICON
                )
            ).createdFromResId
        )
        assertEquals(
            ColorStateList.valueOf(
                ContextCompat.getColor(headerBinding.root.context, ITEM_OPTION_ICON_COLOR)
            ).defaultColor,
            imageButtonOption.imageTintList?.defaultColor
        )
        assertEquals(
            headerBinding.root.context.resources.getDimension(ITEM_OPTION_WITH).toInt(),
            imageButtonOption.width
        )
    }

    @Test
    fun `check priority with header and item background color`() {
        val data = getHeaderModelFilled().let { headerModel ->
            getItemModelFilled().let { itemModel ->
                listOf(
                    headerModel.copy(
                        cardHeaderStyle = headerModel.cardHeaderStyle.copy(
                            backgroundColorItems = HEADER_BACKGROUND_ITEMS
                        ),
                        items = listOf(
                            itemModel,
                            itemModel.copy(
                                cardItemStyle = itemModel.cardItemStyle.copy(
                                    backgroundColorRes = null
                                )
                            )
                        )
                    )
                )
            }
        }

        val expectedDrawableOnFirstItem = (ContextCompat.getDrawable(
            itemBinding.root.context, R.drawable.shape_with_background
        ) as GradientDrawable).run {
            setColor(
                ContextCompat.getColor(itemBinding.root.context, ITEM_BACKGROUND_COLOR)
            )
            this
        }

        val expectedDrawableOnSecondItem = (ContextCompat.getDrawable(
            itemBinding.root.context, R.drawable.shape_with_background
        ) as GradientDrawable).run {
            setColor(
                ContextCompat.getColor(itemBinding.root.context, HEADER_BACKGROUND_ITEMS)
            )
            this
        }

        itemBinding = getItemBindingInflated(data)
        assertEquals(
            shadowOf(expectedDrawableOnFirstItem).lastSetColor,
            shadowOf(itemBinding.constraintLayoutItemCardContainer.background as GradientDrawable).lastSetColor
        )
        itemBinding = getItemBindingInflated(data, 2)
        assertEquals(
            shadowOf(expectedDrawableOnSecondItem).lastSetColor,
            shadowOf(itemBinding.constraintLayoutItemCardContainer.background as GradientDrawable).lastSetColor
        )
    }

    @Test
    fun `check priority with img and background color on header`() {
        assertEquals(
            shadowOf(ContextCompat.getDrawable(headerBinding.root.context, HEADER_IMG_BACKGROUND))
                .createdFromResId,
            shadowOf(headerBinding.imageViewBackgroundCardStyle1.background)
                .createdFromResId
        )
    }

    @Test
    fun `check add new header model extension`() {
        val newHeaderTitle = "new.header.title"
        val newItemTitle = "new.item.title"
        val updatedHeaderModel = getHeaderModelFilled().copy(
            headerTitle = newHeaderTitle,
            items = listOf(
                getItemModelFilled().copy(
                    itemTitle = newItemTitle
                )
            )
        )
        val newData = listOf(
            getHeaderModelFilled().copy(
                items = listOf(
                    getItemModelFilled(),
                    getItemModelFilled()
                )
            ),
            getHeaderModelFilled().copy(
                items = listOf(
                    getItemModelFilled(),
                    getItemModelFilled(),
                    getItemModelFilled()
                )
            ),
            getHeaderModelFilled().copy(
                items = listOf(
                    getItemModelFilled(),
                    getItemModelFilled(),
                    getItemModelFilled(),
                    getItemModelFilled()
                )
            )
        )

        val recyclerView = getSetupAdapterOnRecyclerViewWithAllExpanded(newData)
        recyclerView.addHeader(updatedHeaderModel)

        headerBinding = getHeaderBindingFromViewHolder(
            recyclerView.findViewHolderForAdapterPosition(12)
        )
        itemBinding = getItemBindingFromViewHolder(
            recyclerView.findViewHolderForAdapterPosition(13)
        )
        assertEquals(newHeaderTitle, headerBinding.textViewCardStyle1Title.text)
        assertEquals(newItemTitle, itemBinding.textViewItemCardStyle1Title.text)
    }

    @Test
    fun `check update header extension`() {
        val newHeaderTitle = "new.header.title"
        val newItemTitle = "new.item.title"
        val updatedHeaderModel = getHeaderModelFilled().copy(
            headerTitle = newHeaderTitle,
            items = listOf(
                getItemModelFilled().copy(
                    itemTitle = newItemTitle
                )
            )
        )
        val recyclerView =
            getSetupAdapterOnRecyclerViewWithAllExpanded(listOf(getHeaderModelFilled()))
        headerBinding = getHeaderBindingFromViewHolder(
            recyclerView.findViewHolderForAdapterPosition(0)
        )
        itemBinding = getItemBindingFromViewHolder(
            recyclerView.findViewHolderForAdapterPosition(1)
        )
        assertEquals(HEADER_TITLE, headerBinding.textViewCardStyle1Title.text)
        assertEquals(ITEM_TITLE, itemBinding.textViewItemCardStyle1Title.text)

        recyclerView.updateHeaderAt(0, updatedHeaderModel)

        headerBinding = getHeaderBindingFromViewHolder(
            recyclerView.findViewHolderForAdapterPosition(0)
        )
        itemBinding = getItemBindingFromViewHolder(
            recyclerView.findViewHolderForAdapterPosition(1)
        )

        assertEquals(newHeaderTitle, headerBinding.textViewCardStyle1Title.text)
        assertEquals(newItemTitle, itemBinding.textViewItemCardStyle1Title.text)
    }

    @Test
    fun `check expandable icon visibility`() {
        headerBinding = getHeaderBindingInflated(listOf(getHeaderModelFilled()))
        assertEquals(View.VISIBLE, headerBinding.imageViewArrowDown.visibility)

        headerBinding = getHeaderBindingInflated(
            listOf(getHeaderModelFilled().copy(items = listOf()))
        )
        assertEquals(View.GONE, headerBinding.imageViewArrowDown.visibility)
    }

    @Test
    fun `check total items on adapter`() {
        val recyclerView = getSetupAdapterOnRecyclerViewWithAllExpanded(
            listOf(
                getHeaderModelFilled().copy(
                    items = listOf(
                        getItemModelFilled(), getItemModelFilled()
                    )
                ),
                getHeaderModelFilled().copy(
                    items = listOf(
                        getItemModelFilled(),
                        getItemModelFilled(),
                        getItemModelFilled(),
                        getItemModelFilled()
                    )
                ),
                getHeaderModelFilled().copy(
                    items = listOf(
                        getItemModelFilled(), getItemModelFilled(), getItemModelFilled()
                    )
                ),
            ),
        )
        assertEquals(
            3,
            ((recyclerView.adapter as ConcatAdapter).adapters[0] as DefaultGenericExpandableAdapter).itemCount
        )
        assertEquals(
            5,
            ((recyclerView.adapter as ConcatAdapter).adapters[1] as DefaultGenericExpandableAdapter).itemCount
        )
        assertEquals(
            4,
            ((recyclerView.adapter as ConcatAdapter).adapters[2] as DefaultGenericExpandableAdapter).itemCount
        )
        assertEquals(
            3 + 5 + 4, (recyclerView.adapter as ConcatAdapter).itemCount
        )
    }

    @Test
    fun `check options number on header`() {
        // Arrange
        val optionsOnHeader = listOf(
            getOptionOnHeader(),
            getOptionOnHeader(),
            getOptionOnHeader()
        )

        // Act
        val recyclerView = getSetupAdapterOnRecyclerViewWithAllExpanded(
            data = listOf(getHeaderModelFilled()),
            optionsOnHeader = optionsOnHeader
        )
        // Assert
        val viewHolder = recyclerView.findViewHolderForAdapterPosition(0)
        val headerOptionsNumber =
            (viewHolder as BaseExpandableAdapter.BaseExpandableViewHolder.HeaderExpandableViewHolder<*, *>
                    ).headerBindingContainer?.layoutSwipeOnHeader?.linearLayoutGenericSwipeContainer?.childCount
        assertEquals(
            2 * (optionsOnHeader.size) + 1, headerOptionsNumber
        )
    }

    @Test
    fun `check options number on item`() {
        // Arrange
        val optionsOnItem = listOf(
            getOptionOnItem(),
            getOptionOnItem(),
            getOptionOnItem(),
        )

        // Act
        val recyclerView = getSetupAdapterOnRecyclerViewWithAllExpanded(
            data = listOf(getHeaderModelFilled()),
            optionsOnItem = optionsOnItem
        )
        // Assert
        val viewHolder = recyclerView.findViewHolderForAdapterPosition(1)
        val itemOptionsNumber =
            (viewHolder as BaseExpandableAdapter.BaseExpandableViewHolder.ItemExpandableViewHolder<*, *>
                    ).itemBindingContainer?.layoutSwipeOnItem?.linearLayoutGenericSwipeContainer?.childCount
        assertEquals(
            2 * (optionsOnItem.size) + 1, itemOptionsNumber
        )
    }

    @Test
    fun `check click on header`() {
        // Arrange
        val optionsOnHeader = listOf(getOptionOnHeader())
        var testOptionId = -1
        var modelTest: Any? = null
        val onSwipeOption: OnSwipeOption = { optionId, model ->
            testOptionId = optionId
            modelTest = model
        }
        val recyclerView = getSetupAdapterOnRecyclerViewWithAllExpanded(
            data = listOf(getHeaderModelFilled()),
            optionsOnHeader = optionsOnHeader,
            onSwipeOption = onSwipeOption
        )
        // Act
        val viewHolder = recyclerView.findViewHolderForAdapterPosition(0)
        val imageButtonOption =
            (viewHolder as BaseExpandableAdapter.BaseExpandableViewHolder.HeaderExpandableViewHolder<*, *>
                    ).headerBindingContainer?.layoutSwipeOnHeader?.linearLayoutGenericSwipeContainer?.getChildAt(
                    1
                ) as ImageButton
        imageButtonOption.performClick()

        // Assert
        assertEquals(HEADER_OPTION_ID, testOptionId)
        assertTrue(modelTest is CardHeaderModel)
    }

    @Test
    fun `check click on item`() {
        // Arrange
        val optionsOnItem = listOf(getOptionOnItem())
        var testOptionId = -1
        var modelTest: Any? = null
        val onSwipeOption: OnSwipeOption = { optionId, model ->
            testOptionId = optionId
            modelTest = model
        }
        val recyclerView = getSetupAdapterOnRecyclerViewWithAllExpanded(
            data = listOf(getHeaderModelFilled()),
            optionsOnItem = optionsOnItem,
            onSwipeOption = onSwipeOption
        )
        // Act
        val viewHolder = recyclerView.findViewHolderForAdapterPosition(1)
        val imageButtonOption =
            (viewHolder as BaseExpandableAdapter.BaseExpandableViewHolder.ItemExpandableViewHolder<*, *>
                    ).itemBindingContainer?.layoutSwipeOnItem?.linearLayoutGenericSwipeContainer?.getChildAt(
                    1
                ) as ImageButton
        imageButtonOption.performClick()

        // Assert
        assertEquals(ITEM_OPTION_ID, testOptionId)
        assertTrue(modelTest is CardItemModel)
    }
}