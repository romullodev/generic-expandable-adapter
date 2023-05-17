package com.github.romullodev.generic_expandable_adapter

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ConcatAdapter
import com.github.romullodev.generic_expandable_adapter.base.BaseExpandableAdapter
import com.github.romullodev.generic_expandable_adapter.base.OnSwipeOption
import com.github.romullodev.generic_expandable_adapter.entities.CardHeaderModel
import com.github.romullodev.generic_expandable_adapter.entities.CardItemModel
import com.github.romullodev.generic_expandable_adapter.entities.LayoutOptions
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.HEADER_BACKGROUND_COLOR
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.HEADER_BACKGROUND_ITEMS
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.HEADER_EXPANDABLE_IC_COLOR
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.HEADER_IMG_BACKGROUND
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.HEADER_OPTION_ICON_COLOR
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.HEADER_OPTION_ICON
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.HEADER_OPTION_ID
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.HEADER_OPTION_WITH
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.HEADER_SUBTITLE
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.HEADER_SUBTITLE_COLOR
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.HEADER_TITLE
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.HEADER_TITLE_COLOR
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.ITEM_BACKGROUND_COLOR
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.ITEM_OPTION_ICON
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.ITEM_OPTION_ICON_COLOR
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.ITEM_OPTION_ID
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.ITEM_OPTION_WITH
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.ITEM_TITLE
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.ITEM_TITLE_COLOR
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.LAYOUT_THICKNESS_COLOR
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.getHeaderModelFilled
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.getItemModelFilled
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.getOptionOnHeader
import com.github.romullodev.generic_expandable_adapter.util.TestUtils.Companion.getOptionOnItem
import com.github.romullodev.generic_expandable_adapter.util.addHeader
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
 *   check thickness priority on header
 *   check thickness priority on item
 *   check thickness priority on headerOptions
 *   check thickness priority on itemOptions
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
            context, R.drawable.shape_with_background
        ) as GradientDrawable).run {
            setColor(ContextCompat.getColor(context, HEADER_BACKGROUND_COLOR))
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
            context, R.drawable.shape_with_background
        ) as GradientDrawable).run {
            setColor(
                ContextCompat.getColor(context, ITEM_BACKGROUND_COLOR)
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
            ContextCompat.getColor(context, HEADER_TITLE_COLOR),
            headerBinding.textViewCardStyle1Title.currentTextColor,
        )
        assertEquals(
            ContextCompat.getColor(context, HEADER_SUBTITLE_COLOR),
            headerBinding.textViewCardStyle1Subtitle.currentTextColor,
        )
    }

    @Test
    fun `check tile color on item`() {
        assertEquals(
            ContextCompat.getColor(context, ITEM_TITLE_COLOR),
            itemBinding.textViewItemCardStyle1Title.currentTextColor,
        )
    }

    @Test
    fun `check stroke with background color on header`() {
        headerBinding = getHeaderBindingInflated(
            data = listOf(
                getHeaderModelFilled().copy(
                    cardHeaderStyle = getHeaderModelFilled().cardHeaderStyle.copy(
                        backgroundImgRes = null
                    )
                )
            ),
            layoutOptions = LayoutOptions.DEFAULT.copy(
                expandAllAtFirst = true,
                thicknessColorForAll = LAYOUT_THICKNESS_COLOR
            )
        )

        val expectedDrawable = getGradientDrawable(
            R.drawable.shape_with_background,
            LAYOUT_THICKNESS_COLOR
        )

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
        val expectedDrawable = getGradientDrawable(
            R.drawable.shape_with_background,
            LAYOUT_THICKNESS_COLOR
        )

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
        headerBinding = getHeaderBindingInflated(
            data = listOf(getHeaderModelFilled()),
            layoutOptions = LayoutOptions.DEFAULT.copy(
                expandAllAtFirst = true,
                thicknessColorForAll = LAYOUT_THICKNESS_COLOR
            )
        )

        val expectedDrawable = getGradientDrawable(
            R.drawable.shape_no_background,
            LAYOUT_THICKNESS_COLOR
        )

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
                ContextCompat.getColor(context, HEADER_EXPANDABLE_IC_COLOR)
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
                    context,
                    HEADER_OPTION_ICON
                )
            ).createdFromResId
        )
        assertEquals(
            ColorStateList.valueOf(
                ContextCompat.getColor(context, HEADER_OPTION_ICON_COLOR)
            ).defaultColor,
            imageButtonOption.imageTintList?.defaultColor
        )
        assertEquals(
            context.resources.getDimension(HEADER_OPTION_WITH).toInt(),
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
                    context,
                    ITEM_OPTION_ICON
                )
            ).createdFromResId
        )
        assertEquals(
            ColorStateList.valueOf(
                ContextCompat.getColor(context, ITEM_OPTION_ICON_COLOR)
            ).defaultColor,
            imageButtonOption.imageTintList?.defaultColor
        )
        assertEquals(
            context.resources.getDimension(ITEM_OPTION_WITH).toInt(),
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
            context, R.drawable.shape_with_background
        ) as GradientDrawable).run {
            setColor(
                ContextCompat.getColor(context, ITEM_BACKGROUND_COLOR)
            )
            this
        }

        val expectedDrawableOnSecondItem = (ContextCompat.getDrawable(
            context, R.drawable.shape_with_background
        ) as GradientDrawable).run {
            setColor(
                ContextCompat.getColor(context, HEADER_BACKGROUND_ITEMS)
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
            shadowOf(ContextCompat.getDrawable(context, HEADER_IMG_BACKGROUND))
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

    @Test
    fun `check thickness priority on header`() {
        // Arrange
        val headerModel = getHeaderModelFilled()
        val data = listOf(
            headerModel.copy(cardHeaderStyle = headerModel.cardHeaderStyle.copy(hasThicknessOnHeader = null)),
            headerModel.copy(cardHeaderStyle = headerModel.cardHeaderStyle.copy(hasThicknessOnHeader = false)),
            headerModel.copy(cardHeaderStyle = headerModel.cardHeaderStyle.copy(hasThicknessOnHeader = true))
        )
        val recyclerView = getSetupAdapterOnRecyclerViewWithAllExpanded(data)

        // Act
        val headerBinding1 = getHeaderBindingFromViewHolder(
            recyclerView.findViewHolderForAdapterPosition(0)
        )
        val headerBinding2 = getHeaderBindingFromViewHolder(
            recyclerView.findViewHolderForAdapterPosition(2)
        )
        val headerBinding3 = getHeaderBindingFromViewHolder(
            recyclerView.findViewHolderForAdapterPosition(4)
        )

        // Assert
        val drawableWithStrokeExpected = getGradientDrawable(
            R.drawable.shape_with_background,
            LAYOUT_THICKNESS_COLOR
        )

        val drawableNoStrokeExpected = getGradientDrawable(R.drawable.shape_with_background)

        assertEquals(
            shadowOf(drawableWithStrokeExpected).strokeWidth,
            shadowOf(
                headerBinding1.cardViewHeaderCardContainer.foreground as GradientDrawable
            ).strokeWidth
        )

        assertEquals(
            shadowOf(drawableNoStrokeExpected).strokeWidth,
            shadowOf(
                headerBinding2.cardViewHeaderCardContainer.foreground as GradientDrawable
            ).strokeWidth
        )

        assertEquals(
            shadowOf(drawableWithStrokeExpected).strokeWidth,
            shadowOf(
                headerBinding3.cardViewHeaderCardContainer.foreground as GradientDrawable
            ).strokeWidth
        )
    }

    @Test
    fun `check thickness priority on item`() {
        // Arrange
        val headerModel = getHeaderModelFilled()
        val itemModel = getItemModelFilled()
        val data = listOf(
            headerModel.copy(
                items = listOf(
                    itemModel,
                    itemModel.copy(
                        cardItemStyle = itemModel.cardItemStyle.copy(hasThicknessOnItem = true)
                    ),
                    itemModel.copy(
                        cardItemStyle = itemModel.cardItemStyle.copy(hasThicknessOnItem = false)
                    )
                )
            )
        )
        val recyclerView = getSetupAdapterOnRecyclerViewWithAllExpanded(data)

        // Act
        val itemBinding1 = getItemBindingFromViewHolder(
            recyclerView.findViewHolderForAdapterPosition(1)
        )
        val itemBinding2 = getItemBindingFromViewHolder(
            recyclerView.findViewHolderForAdapterPosition(2)
        )
        val itemBinding3 = getItemBindingFromViewHolder(
            recyclerView.findViewHolderForAdapterPosition(3)
        )

        // Assert
        val drawableWithStrokeExpected = getGradientDrawable(
            R.drawable.shape_with_background,
            LAYOUT_THICKNESS_COLOR
        )
        val drawableNoStrokeExpected = getGradientDrawable(R.drawable.shape_with_background)

        assertEquals(
            shadowOf(drawableWithStrokeExpected).strokeWidth,
            shadowOf(
                itemBinding1.constraintLayoutItemCardContainer.background as GradientDrawable
            ).strokeWidth
        )

        assertEquals(
            shadowOf(drawableWithStrokeExpected).strokeWidth,
            shadowOf(
                itemBinding2.constraintLayoutItemCardContainer.background as GradientDrawable
            ).strokeWidth
        )

        assertEquals(
            shadowOf(drawableNoStrokeExpected).strokeWidth,
            shadowOf(
                itemBinding3.constraintLayoutItemCardContainer.background as GradientDrawable
            ).strokeWidth
        )

    }

    @Test
    fun `check thickness priority on headerOptions`() {
        val headerModel = getHeaderModelFilled()
        val data = listOf(
            headerModel.copy(cardHeaderStyle = headerModel.cardHeaderStyle.copy(hasThicknessOnHeader = null)),
            headerModel.copy(cardHeaderStyle = headerModel.cardHeaderStyle.copy(hasThicknessOnHeader = false)),
            headerModel.copy(cardHeaderStyle = headerModel.cardHeaderStyle.copy(hasThicknessOnHeader = true))
        )
        val recyclerView = getSetupAdapterOnRecyclerViewWithAllExpanded(
            data = data,
            optionsOnHeader = listOf(getOptionOnHeader())
        )

        // Act
        val viewHolder1 = recyclerView.findViewHolderForAdapterPosition(0)
        val viewHolder2 = recyclerView.findViewHolderForAdapterPosition(2)
        val viewHolder3 = recyclerView.findViewHolderForAdapterPosition(4)

        // Assert
        val optionDrawable1 = getGradientDrawableFromOptionHeader(viewHolder1)
        val optionDrawable2 = getGradientDrawableFromOptionHeader(viewHolder2)
        val optionDrawable3 = getGradientDrawableFromOptionHeader(viewHolder3)

        val drawableWithStrokeExpected = getGradientDrawable(
            R.drawable.shape_with_background,
            LAYOUT_THICKNESS_COLOR
        )

        val drawableNoStrokeExpected = getGradientDrawable(R.drawable.shape_with_background)

        assertEquals(
            shadowOf(drawableWithStrokeExpected).strokeWidth,
            shadowOf(optionDrawable1).strokeWidth
        )

        assertEquals(
            shadowOf(drawableNoStrokeExpected).strokeWidth,
            shadowOf(optionDrawable2).strokeWidth
        )

        assertEquals(
            shadowOf(drawableWithStrokeExpected).strokeWidth,
            shadowOf(optionDrawable3).strokeWidth
        )
    }

    @Test
    fun `check thickness priority on itemOptions`() {
        val headerModel = getHeaderModelFilled()
        val itemModel = getItemModelFilled()
        val data = listOf(
            headerModel.copy(
                items = listOf(
                    itemModel,
                    itemModel.copy(
                        cardItemStyle = itemModel.cardItemStyle.copy(hasThicknessOnItem = true)
                    ),
                    itemModel.copy(
                        cardItemStyle = itemModel.cardItemStyle.copy(hasThicknessOnItem = false)
                    )
                )
            )
        )
        val recyclerView = getSetupAdapterOnRecyclerViewWithAllExpanded(
            data = data,
            optionsOnItem = listOf(getOptionOnItem())
        )

        // Act
        val viewHolder1 = recyclerView.findViewHolderForAdapterPosition(1)
        val viewHolder2 = recyclerView.findViewHolderForAdapterPosition(2)
        val viewHolder3 = recyclerView.findViewHolderForAdapterPosition(3)

        // Assert
        val optionDrawable1 = getGradientDrawableFromOptionItem(viewHolder1)
        val optionDrawable2 = getGradientDrawableFromOptionItem(viewHolder2)
        val optionDrawable3 = getGradientDrawableFromOptionItem(viewHolder3)

        val drawableWithStrokeExpected = getGradientDrawable(
            R.drawable.shape_with_background,
            LAYOUT_THICKNESS_COLOR
        )

        val drawableNoStrokeExpected = getGradientDrawable(R.drawable.shape_with_background)

        assertEquals(
            shadowOf(drawableWithStrokeExpected).strokeWidth,
            shadowOf(optionDrawable1).strokeWidth
        )

        assertEquals(
            shadowOf(drawableWithStrokeExpected).strokeWidth,
            shadowOf(optionDrawable2).strokeWidth
        )

        assertEquals(
            shadowOf(drawableNoStrokeExpected).strokeWidth,
            shadowOf(optionDrawable3).strokeWidth
        )
    }

}