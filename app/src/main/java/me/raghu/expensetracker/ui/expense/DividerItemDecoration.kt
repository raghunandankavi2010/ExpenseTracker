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
        val spacing = view.resources.getDimensionPixelOffset(R.dimen.rv_bottom)


        val childCount = parent.layoutManager!!.itemCount
        val childIndex = parent.getChildLayoutPosition(view)
        val spanCount: Int = 1
        val spanIndex = childIndex % spanCount
        if (isBottomEdge(childIndex, childCount, spanCount,spanIndex)) {
            outRect.bottom = spacing;
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


    private fun isBottomEdge(
        childIndex: Int,
        childCount: Int,
        spanCount: Int,
        spanIndex: Int
    ): Boolean {
        return childIndex >= childCount - spanCount + spanIndex
    }
}