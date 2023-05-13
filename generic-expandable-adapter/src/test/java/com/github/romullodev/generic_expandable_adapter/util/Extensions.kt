package com.github.romullodev.generic_expandable_adapter.util

import android.os.SystemClock
import android.view.InputDevice
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.romullodev.generic_expandable_adapter.entities.CardHeaderModel
import com.github.romullodev.generic_expandable_adapter.utils.addHeaderDefaultExpandableAdapter
import com.github.romullodev.generic_expandable_adapter.utils.updateHeaderDefaultExpandableAdapterHeader

fun RecyclerView.updateMeasure() {
    val screenSize = 2000
    measure(
        View.MeasureSpec.UNSPECIFIED,
        View.MeasureSpec.UNSPECIFIED
    )
    layout(0, 0, screenSize, screenSize)
}

fun RecyclerView.updateHeaderAt(position: Int, data: CardHeaderModel) {
    updateHeaderDefaultExpandableAdapterHeader(position, data)
    updateMeasure()
}

fun RecyclerView.addHeader(data: CardHeaderModel) {
    addHeaderDefaultExpandableAdapter(data, true)
    updateMeasure()
}

fun ViewGroup.performSwipeToLeft(target: View) {
    this.performSwipe(target, distanceX = -this.width * .5f, distanceY = 0f)
}

fun ViewGroup.performSwipe(target: View, distanceX: Float, distanceY: Float) {
    val parentCoords = intArrayOf(0, 0)
    this.getLocationInWindow(parentCoords)

    val childCoords = intArrayOf(0, 0)
    target.getLocationInWindow(childCoords)

    val initGlobalX = childCoords[0].toFloat() + 1f
    val initGlobalY = childCoords[1].toFloat() + 1f

    val initLocalX = (childCoords[0] - parentCoords[0]).toFloat() + 1f
    val initLocalY = (childCoords[1] - parentCoords[1]).toFloat() + 1f

    val downTime = SystemClock.uptimeMillis()
    var eventTime = SystemClock.uptimeMillis()

    this.dispatchTouchEvent(
        MotionEvent.obtain(
            downTime,
            eventTime,
            MotionEvent.ACTION_DOWN,
            initGlobalX,
            initGlobalY,
            0
        ).apply {
            setLocation(initLocalX, initLocalY)
            source = InputDevice.SOURCE_TOUCHSCREEN
        }
    )

    val steps = 20
    var i = 0
    while (i in 0..steps) {
        val globalX = initGlobalX + i * distanceX / steps
        val globalY = initGlobalY + i * distanceY / steps
        val localX = initLocalX + i * distanceX / steps
        val localY = initLocalY + i * distanceY / steps
        if (globalX <= 10f || globalY <= 10f) {
            break
        }
        this.dispatchTouchEvent(
            MotionEvent.obtain(
                downTime,
                ++eventTime,
                MotionEvent.ACTION_MOVE,
                globalX,
                globalY,
                0
            ).apply {
                setLocation(localX, localY)
                source = InputDevice.SOURCE_TOUCHSCREEN
            }
        )
        i++
    }

    this.dispatchTouchEvent(
        MotionEvent.obtain(
            downTime,
            ++eventTime,
            MotionEvent.ACTION_UP,
            initGlobalX + i * distanceX,
            initGlobalY + i * distanceY,
            0
        ).apply {
            setLocation(initLocalX + i * distanceX, initLocalY + i * distanceY)
            source = InputDevice.SOURCE_TOUCHSCREEN
        }
    )
}