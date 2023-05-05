package com.github.romullodev.generic_expandable_adapter.base

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class CustomExpandableAdapterAnimation : DefaultItemAnimator() {

    override fun recordPreLayoutInformation(
        state: RecyclerView.State,
        viewHolder: ViewHolder,
        changeFlags: Int,
        payloads: MutableList<Any>
    ): ItemHolderInfo {
        return if (viewHolder is BaseCustomExpandableAdapter.BaseCustomExpandableViewHolder.CustomHeaderExpandableViewHolder<*, *>) {
            CustomHeaderItemInfo().also {
                it.setFrom(viewHolder)
            }
        } else {
            super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads)
        }
    }

    override fun recordPostLayoutInformation(
        state: RecyclerView.State,
        viewHolder: ViewHolder
    ): ItemHolderInfo {
        return if (viewHolder is BaseCustomExpandableAdapter.BaseCustomExpandableViewHolder.CustomHeaderExpandableViewHolder<*, *>) {
            CustomHeaderItemInfo().also {
                it.setFrom(viewHolder)
            }
        } else {
            super.recordPostLayoutInformation(state, viewHolder)
        }
    }

    override fun animateChange(
        oldHolder: ViewHolder,
        holder: ViewHolder,
        preInfo: ItemHolderInfo,
        postInfo: ItemHolderInfo
    ): Boolean {
        if (preInfo is CustomHeaderItemInfo && postInfo is CustomHeaderItemInfo && holder is BaseCustomExpandableAdapter.BaseCustomExpandableViewHolder.CustomHeaderExpandableViewHolder<*, *>) {
            holder.icExpand?.run {
                ObjectAnimator
                    .ofFloat(
                        holder.icExpand,
                        View.ROTATION,
                        preInfo.arrowRotation,
                        postInfo.arrowRotation
                    )
                    .also {
                        it.addListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                holder.icExpand?.run {
                                    rotation = postInfo.arrowRotation
                                }
                                dispatchAnimationFinished(holder)
                            }
                        })
                        it.start()
                    }
            }
        }
        return super.animateChange(oldHolder, holder, preInfo, postInfo)
    }

    //It means that for animation we don’t need to have separated objects of ViewHolder (old and new holder)
    override fun canReuseUpdatedViewHolder(viewHolder: ViewHolder): Boolean {
        return true
    }
}

class CustomHeaderItemInfo : RecyclerView.ItemAnimator.ItemHolderInfo() {

    internal var arrowRotation: Float = 0F

    override fun setFrom(holder: ViewHolder): RecyclerView.ItemAnimator.ItemHolderInfo {
        if (holder is BaseCustomExpandableAdapter.BaseCustomExpandableViewHolder.CustomHeaderExpandableViewHolder<*, *>) {
            holder.icExpand?.run {
                arrowRotation = rotation
            }
        }
        return super.setFrom(holder)
    }
}