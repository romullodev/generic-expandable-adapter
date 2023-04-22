package com.github.romullodev.generic_expandable_adapter

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ConcatAdapter
import com.github.romullodev.generic_expandable_adapter.Utils.Companion.HEADER_BACKGROUND_COLOR
import com.github.romullodev.generic_expandable_adapter.Utils.Companion.HEADER_IMG_BACKGROUND
import com.github.romullodev.generic_expandable_adapter.Utils.Companion.HEADER_SUBTITLE
import com.github.romullodev.generic_expandable_adapter.Utils.Companion.HEADER_SUBTITLE_COLOR
import com.github.romullodev.generic_expandable_adapter.Utils.Companion.HEADER_THICKNESS_COLOR
import com.github.romullodev.generic_expandable_adapter.Utils.Companion.HEADER_TITLE
import com.github.romullodev.generic_expandable_adapter.Utils.Companion.HEADER_TITLE_COLOR
import com.github.romullodev.generic_expandable_adapter.Utils.Companion.ITEM_BACKGROUND_COLOR
import com.github.romullodev.generic_expandable_adapter.Utils.Companion.ITEM_THICKNESS_COLOR
import com.github.romullodev.generic_expandable_adapter.Utils.Companion.ITEM_TITLE
import com.github.romullodev.generic_expandable_adapter.Utils.Companion.ITEM_TITLE_COLOR
import com.github.romullodev.generic_expandable_adapter.Utils.Companion.getHeaderModel
import com.github.romullodev.generic_expandable_adapter.Utils.Companion.getItemModel
import com.github.romullodev.generic_expandable_adapter.entities.CardHeaderModel
import com.github.romullodev.generic_expandable_adapter.entities.CardHeaderStyle
import com.github.romullodev.generic_expandable_adapter.entities.CardItemModel
import com.github.romullodev.generic_expandable_adapter.entities.CardItemStyle
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf


/**
 * TODO:
 * 1 - permitir atualizar os dados do recyclerView apos a instancia
 */

@RunWith(RobolectricTestRunner::class)
class DefaultGenericExpandableAdapterTest : BaseTest() {

    @Test
    fun `check info on header`() {
        val headerBinding = getHeaderBindingInflated(
            listOf(
                getHeaderModel().copy(
                    headerTitle = HEADER_TITLE, headerSubtitle = HEADER_SUBTITLE
                )
            ), 0
        )
        assertEquals(HEADER_TITLE, headerBinding.textViewCardStyle1Title.text)
        assertEquals(HEADER_SUBTITLE, headerBinding.textViewCardStyle1Subtitle.text)
    }

    @Test
    fun `check info on item`() {
        val itemBinding = getItemBindingInflated(
            listOf(
                getHeaderModel().copy(
                    items = listOf(
                        getItemModel().copy(
                            itemTitle = ITEM_TITLE
                        )
                    )
                )
            ), 1
        )

        assertEquals(ITEM_TITLE, itemBinding.textViewItemCardStyle1Name.text)
    }

    @Test
    fun `check background color on header`() {
        val headerBinding = getHeaderBindingInflated(
            listOf(
                getHeaderModel().copy(
                    cardHeaderStyle = CardHeaderStyle(
                        backgroundColorRes = HEADER_BACKGROUND_COLOR
                    )
                )
            ), 0
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
        val itemBinding = getItemBindingInflated(
            listOf(
                CardHeaderModel(
                    items = listOf(
                        CardItemModel(
                            cardItemStyle = CardItemStyle(
                                backgroundColorRes = ITEM_BACKGROUND_COLOR
                            )
                        )
                    )
                )
            ), 1
        )

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
    fun `check background img on header`() {
        val headerBinding = getHeaderBindingInflated(
            listOf(
                getHeaderModel().copy(
                    cardHeaderStyle = CardHeaderStyle(
                        backgroundImgRes = HEADER_IMG_BACKGROUND,
                        backgroundColorRes = HEADER_BACKGROUND_COLOR
                    )
                )
            ), 0
        )

        assertEquals(
            shadowOf(
                ContextCompat.getDrawable(
                    headerBinding.root.context,
                    HEADER_IMG_BACKGROUND
                )
            ).createdFromResId,
            shadowOf(headerBinding.imageViewBackgroundCardStyle1.background).createdFromResId
        )
    }

    @Test
    fun `check title and subtitle color on header`() {
        val headerBinding = getHeaderBindingInflated(
            listOf(
                getHeaderModel().copy(
                    cardHeaderStyle = CardHeaderStyle(
                        titleColorRes = HEADER_TITLE_COLOR, subtitleColorRes = HEADER_SUBTITLE_COLOR

                    )
                )
            ), 0
        )
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
        val itemBinding = getItemBindingInflated(
            listOf(
                getHeaderModel().copy(
                    items = listOf(
                        getItemModel().copy(
                            cardItemStyle = CardItemStyle(
                                titleColorRes = ITEM_TITLE_COLOR
                            )
                        )
                    )
                )
            ), 1
        )

        assertEquals(
            ContextCompat.getColor(itemBinding.root.context, ITEM_TITLE_COLOR),
            itemBinding.textViewItemCardStyle1Name.currentTextColor,
        )
    }


    @Test
    fun `check stroke color with background color on header`() {
        val headerBinding = getHeaderBindingInflated(
            listOf(
                getHeaderModel().copy(
                    cardHeaderStyle = CardHeaderStyle(
                        backgroundColorRes = HEADER_BACKGROUND_COLOR,
                        thicknessColor = HEADER_THICKNESS_COLOR
                    )
                )
            ), 0
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
        val itemBinding = getItemBindingInflated(
            listOf(
                getHeaderModel().copy(
                    items = listOf(
                        getItemModel().copy(
                            cardItemStyle = CardItemStyle(
                                thicknessColor = ITEM_THICKNESS_COLOR
                            )
                        )
                    )
                )
            ), 1
        )
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

    /*
    @Test
    fun `check stroke color with background img on header`() {
        TODO()
    }

    @Test
    fun `check stroke color on header`() {
        TODO()
    }

    @Test
    fun `check stroke color on item`() {
        TODO()
    }

    @Test
    fun `check priority on header background`() {
        TODO()
    }
    */
    @Test
    fun `check expandable icon visibility`() {
        val headerBindingIcVisible = getHeaderBindingInflated(
            listOf(getHeaderModel()), 0
        )
        assertEquals(View.VISIBLE, headerBindingIcVisible.imageViewArrowDown.visibility)

        val headerBindingIcGone = getHeaderBindingInflated(
            listOf(getHeaderModel().copy(items = listOf())), 0
        )
        assertEquals(View.GONE, headerBindingIcGone.imageViewArrowDown.visibility)
    }

    @Test
    fun `check total items on adapter`() {
        val recyclerView = getSetupAdapterOnRecyclerView(
            listOf(
                getHeaderModel().copy(
                    items = listOf(
                        getItemModel(), getItemModel()
                    )
                ),
                getHeaderModel().copy(
                    items = listOf(
                        getItemModel(), getItemModel(), getItemModel(), getItemModel()
                    )
                ),
                getHeaderModel().copy(
                    items = listOf(
                        getItemModel(), getItemModel(), getItemModel()
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
}