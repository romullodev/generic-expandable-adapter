package com.romullodev.library.base

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class ExpandableAdapterAnimation : DefaultItemAnimator() {

    override fun recordPreLayoutInformation(
        state: RecyclerView.State,
        viewHolder: ViewHolder,
        changeFlags: Int,
        payloads: MutableList<Any>
    ): ItemHolderInfo {
        return if (viewHolder is BaseExpandableAdapter.BaseExpandableAdapterViewHolder.HeaderExpandableAdapter<*, *>) {
            HeaderItemInfo().also {
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
        return if (viewHolder is BaseExpandableAdapter.BaseExpandableAdapterViewHolder.HeaderExpandableAdapter<*, *>) {
            HeaderItemInfo().also {
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
        if (preInfo is HeaderItemInfo && postInfo is HeaderItemInfo && holder is BaseExpandableAdapter.BaseExpandableAdapterViewHolder.HeaderExpandableAdapter<*, *>) {
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

class HeaderItemInfo : RecyclerView.ItemAnimator.ItemHolderInfo() {

    internal var arrowRotation: Float = 0F

    override fun setFrom(holder: ViewHolder): RecyclerView.ItemAnimator.ItemHolderInfo {
        if (holder is BaseExpandableAdapter.BaseExpandableAdapterViewHolder.HeaderExpandableAdapter<*, *>) {
            holder.icExpand?.run {
                arrowRotation = rotation
            }
        }
        return super.setFrom(holder)
    }
}