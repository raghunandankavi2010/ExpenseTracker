package me.raghu.expensetracker.ui.expense

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import me.raghu.expensetracker.R


class DividerItemDecoration(context: Context) : ItemDecoration() {

    private val mDivider: Drawable?

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        a.recycle()
    }

    companion object {
        private val ATTRS = intArrayOf(
           android.R.attr.listDivider
        )
    }


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = 0
        outRect.right = 0
        outRect.bottom = 0
        //val childCount = parent.childCount
        val spacing = view.resources.getDimensionPixelOffset(R.dimen.rv_bottom)
        val isLastRow: Boolean = state.itemCount - 1  < 0
        parent.adapter?.let {
            if (isLastRow) {
                outRect.bottom = spacing
            }
        }

    }
    override fun onDraw(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }
}