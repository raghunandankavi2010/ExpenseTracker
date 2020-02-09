package me.raghu.expensetracker.utils

import android.view.View
import android.view.ViewGroup
import androidx.core.view.*
import timber.log.Timber
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 *  Utils methods
 */

fun getLastDateOfMonth(): Date {
    val cal: Calendar = Calendar.getInstance()
    val lastDate: Int = cal.getActualMaximum(Calendar.DATE)
    cal.set(Calendar.DAY_OF_MONTH, lastDate)
    cal.set(Calendar.HOUR_OF_DAY, 0)
    Timber.i(cal.time.dateToString(), lastDate)
    return cal.time
}

fun getFirstDateOfMonth(): Date {
    val cal = Calendar.getInstance()
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal[Calendar.DAY_OF_MONTH] = cal.getActualMinimum(Calendar.DAY_OF_MONTH)
    Timber.i(cal.time.dateToString())
    return cal.time
}

fun Date.dateToString(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(this)
}

fun Date.getDayofMonth(): String {
    val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
    return dateFormat.format(this)
}

fun Float.toDefaultFormat(): String {
    return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this)
}

fun String.toDateFormat(): Date? {
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(this)
}

fun Date.getDayOfMonth(): Float {
    val c = Calendar.getInstance()
    c.time = this
    val dayOfWeek = c[Calendar.DAY_OF_MONTH]
    return dayOfWeek.toFloat()
}

fun View.addSystemWindowInsetToMargin(
    left: Boolean = false,
    top: Boolean = false,
    right: Boolean = false,
    bottom: Boolean = false
) {
    val (initialLeft, initialTop, initialRight, initialBottom) =
        listOf(marginLeft, marginTop, marginRight, marginBottom)

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        view.updateLayoutParams {
            (this as? ViewGroup.MarginLayoutParams)?.let {
                updateMargins(
                    left = initialLeft + (if (left) insets.systemWindowInsetLeft else 0),
                    top = initialTop + (if (top) insets.systemWindowInsetTop else 0),
                    right = initialRight + (if (right) insets.systemWindowInsetRight else 0),
                    bottom = initialBottom + (if (bottom) insets.systemWindowInsetBottom else 0)
                )
            }
        }

        insets
    }
}

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

fun View.doOnApplyWindowInsets(f: (View, WindowInsetsCompat, ViewPaddingState) -> Unit) {
    // Create a snapshot of the view's padding state
    val paddingState = createStateForView(this)
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        f(v, insets, paddingState)
        insets
    }
    requestApplyInsetsWhenAttached()
}

data class ViewPaddingState(
    val left: Int,
    val top: Int,
    val right: Int,
    val bottom: Int,
    val start: Int,
    val end: Int
)

private fun createStateForView(view: View) = ViewPaddingState(
    view.paddingLeft,
    view.paddingTop, view.paddingRight, view.paddingBottom, view.paddingStart, view.paddingEnd
)
