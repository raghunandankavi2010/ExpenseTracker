package me.raghu.expensetracker.ui.expense

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import me.raghu.expensetracker.R


class DividerItemDecoration(private val context: Context) : ItemDecoration() {
    private var mDivider: Drawable? = null
    init {
        mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider)
    }
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        //We do not want to add any padding for the first child
        //Because we do not want to have any unwanted space above the
        //Recycler view
        if (parent.getChildAdapterPosition(view) == 0) {
            return
        }
        val position = parent.getChildAdapterPosition(view)
        val isLast = position == state.itemCount - 1
        if (isLast) {
            outRect.bottom = view.resources.getDimensionPixelOffset(R.dimen.rv_bottom)
        }
    }

    /**
     * Called only once.
     * This method is used for deciding the bounds of the divider. Meaning
     * We decide where the divider is to be drawn and how.
     *
     * @param c
     * @param parent
     * @param state
     */
    override fun onDraw(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.onDraw(c, parent, state)
        //Divider Left is the distance of the child view from the parent
        //And dividerRight is the distance from the parent's left to the
        //right and -32 because we want the divider to be drawn with a padding
        // of 32 on the right too.
        val dividerLeft = 0
        val dividerRight = parent.width
        //this is done for top and bottom divider for every view
        //This is because it is different for each view
        for (i in 0 until parent.childCount) { //This is done so that at the bottom of the last child
            //We don't want a divider there.
            if (i != parent.childCount - 1) {
                val child = parent.getChildAt(i)
                val params =
                    child.layoutParams as RecyclerView.LayoutParams
                //Calculating the distance of the divider to be drawn from top
                val dividerTop = child.bottom + params.bottomMargin
                val dividerBottom = dividerTop + (mDivider?.intrinsicHeight ?: 0)
                mDivider?.let {
                    it.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
                    it.draw(c)
                }

            }
        }
    }

}